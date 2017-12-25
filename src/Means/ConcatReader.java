package Means;

/**
 * @author aolish333@gmail.com
 * @date 2017/12/25 16:21
 * User:Lee
 */

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;


public class ConcatReader  extends Reader {

    private int readerQueueIndex = 0;
    private ArrayList<Reader> readerQueue = new ArrayList<Reader>();
    private Reader currentReader = null;
    private boolean doneAddingReaders = false;


    public void lastReaderAdded(){
        doneAddingReaders = true;
    }


    public void addReader(Reader in){
        synchronized(readerQueue){
            if (in == null) throw new NullPointerException();
            if (closed) throw new IllegalStateException("ConcatReader has been closed");
            if (doneAddingReaders) throw new IllegalStateException("Cannot add more readers - the last reader has already been added.");
            readerQueue.add(in);
        }
    }


    public void addReaders(Reader[] in){
        for (Reader element: in) {
            addReader(element);
        }
    }


    private Reader getCurrentReader(){
        if (currentReader == null && readerQueueIndex < readerQueue.size()){
            synchronized(readerQueue){
                // reader queue index is advanced only by the nextReader()
                // method.  Don't do it here.
                currentReader = readerQueue.get(readerQueueIndex);
            }
        }
        return currentReader;
    }


    private void advanceToNextReader(){
        currentReader = null;
        readerQueueIndex++;
    }


    private boolean closed = false;


    public ConcatReader(Reader in){
        addReader(in);
        lastReaderAdded();
    }


    public ConcatReader(Reader in1, Reader in2){
        addReader(in1);
        addReader(in2);
        lastReaderAdded();
    }


    public ConcatReader(Reader[] in){
        addReaders(in);
        lastReaderAdded();
    }


    @Override public int read() throws IOException {
        if (closed) throw new IOException("Reader closed");
        int r = -1;
        while (r == -1){
            Reader in = getCurrentReader();
            if (in == null){
                if (doneAddingReaders) return -1;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException iox){
                    throw new IOException("Interrupted");
                }
            } else {
                r = in.read();
                if (r == -1) advanceToNextReader();
            }
        }
        return r;
    }


    @Override public int read(char[] cbuf) throws IOException {
        return read(cbuf, 0, cbuf.length);
    }


    @Override public int read(char[] cbuf, int off, int len) throws IOException {
        if (off < 0 || len < 0 || off + len > cbuf.length) throw new IndexOutOfBoundsException();
        if (closed) throw new IOException("Reader closed");
        int r = -1;
        while (r == -1){
            Reader in = getCurrentReader();
            if (in == null){
                if (doneAddingReaders) return -1;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException iox){
                    throw new IOException("Interrupted");
                }
            } else {
                r = in.read(cbuf, off, len);
                if (r == -1) advanceToNextReader();
            }
        }
        return r;
    }


    @Override public long skip(long n) throws IOException {
        if (closed) throw new IOException("Reader closed");
        if (n <= 0) return 0;
        long s = -1;
        while (s <= 0){
            Reader in = getCurrentReader();
            if (in == null){
                if (doneAddingReaders) return 0;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException iox){
                    throw new IOException("Interrupted");
                }
            } else {
                s = in.skip(n);
                // When nothing was skipped it is a bit of a puzzle.
                // The most common cause is that the end of the underlying
                // stream was reached.  In which case calling skip on it
                // will always return zero.  If somebody were calling skip
                // until it skipped everything they needed, there would
                // be an infinite loop if we were to return zero here.
                // If we get zero, let us try to read one character so
                // we can see if we are at the end of the stream.  If so,
                // we will move to the next.
                if (s <= 0) {
                    // read() will advance to the next stream for us, so don't do it again
                    s = ((read()==-1)?-1:1);
                }
            }

        }
        return s;
    }


    @Override public boolean ready() throws IOException {
        if (closed) throw new IOException("Reader closed");
        Reader in = getCurrentReader();
        if (in == null) return false;
        return in.ready();
    }

    @Override public void close() throws IOException {
        if (closed) return;
        for (Reader reader: readerQueue) {
            reader.close();
        }
        closed = true;
    }


    @Override public void mark(int readlimit) throws IOException {
        throw new IOException("Mark not supported");
    }


    @Override public void reset() throws IOException {
        throw new IOException("Reset not supported");
    }


    @Override public boolean markSupported(){
        return false;
    }
}
