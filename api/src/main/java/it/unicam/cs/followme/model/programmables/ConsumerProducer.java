package it.unicam.cs.followme.model.programmables;

import java.util.LinkedList;

public class ConsumerProducer<T> {
    private final LinkedList<T> buffer;
    private final int size;

    public ConsumerProducer(LinkedList<T> buffer, int size) {
        this.buffer = new LinkedList<>();
        this.size = size;
    }

    public synchronized boolean isEmpty(){
        return buffer.size() == 0;
    }

    public synchronized boolean isFull(){
        return buffer.size() == size;
    }

    public synchronized void add(T item)throws InterruptedException{
        while (this.isFull())
            wait();
        this.notifyAll();
        buffer.add(item);
    }

    public T get() throws InterruptedException{
        while(this.isEmpty())
            wait();
        this.notifyAll();
        return buffer.poll();
    }
}
