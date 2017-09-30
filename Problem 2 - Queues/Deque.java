import java.util.Iterator;

/**
 * Created by GUNGUI on 9/4/2017.
 */
public class Deque<Item> implements Iterable<Item> {
    private Item[] queue;
    private int N=0;

    public Deque() {
        queue = null;
    }                           // construct an empty deque

    public boolean isEmpty() {
        return N == 0;
    }                 // is the deque empty?

    public int size() {
        return N;
    }                       // return the number of items on the deque

    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (!isEmpty()) {
            if(N == queue.length) resize(2*queue.length);
           for(int i=N;i >= 1;i--) {
               queue[i]=queue[i-1];
           }
            queue[0]=item;
            N++;
        } else {
            queue = (Item[]) new Object[1];
            queue[N++] = item;
        }
    }          // add the item to the front

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (!isEmpty()) {
            if(N == queue.length) resize(2*N);
            queue[N++] =item;
        } else {
            queue = (Item[]) new Object[1];
            queue[0] = item;
            N++;
        }
    }           // add the item to the end

    public Item removeFirst() {
        if (!isEmpty()) {
            Item item = queue[0];
            if(N!=1){
                for(int i=0;i<N-1;i++)
                {
                    queue[i] = queue[i+1];
                }
            }
            else{
                queue[0] = null;
            }
            N--;
            queue[N]=null;
            if(N == queue.length/4) resize(queue.length/2);
            return item;
        } else {
            throw new java.util.NoSuchElementException();
        }
    }                // remove and return the item from the front

    public Item removeLast() {
        if (!isEmpty()) {
            Item item = queue[N-1];
            queue[N-1] = null;
            N--;
            if(N == queue.length/4) resize(queue.length/2);
            return item;
        } else {
            throw new java.util.NoSuchElementException();
        }
    }                 // remove and return the item from the end

    public Iterator<Item> iterator() {
        return new ListIterator();
    }// return an iterator over items in order from front to end

    private class ListIterator implements Iterator<Item> {
        int i=0;
        public boolean hasNext() {
            return i < N;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = queue[i++];
            return item;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
    private void resize(int length){
        Item[] temp = (Item[]) new Object[length];
        for(int i=0;i<N;i++)
            temp[i]=queue[i];
        queue = temp;
    }
    public static void main(String[] args) {
        Deque<Integer> rq = new Deque<>();
        rq.addFirst(1);
        rq.addLast(2);
        for(int i:rq){
            StdOut.println(i);
        }
        rq.removeFirst();
        rq.removeLast();
    }   // unit testing (optional)
}
