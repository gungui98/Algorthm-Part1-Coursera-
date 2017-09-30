import java.util.Iterator;
/**
 * Created by GUNGUI on 9/4/2017.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int N = 0;
    public RandomizedQueue(){
        queue = null;
    }                 // construct an empty randomized queue
    public boolean isEmpty(){
        return N == 0;
    }                 // is the queue empty?
    public int size(){
        return N;
    }                      // return the number of items on the queue
    public void enqueue(Item item){
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
    }           // add the item
    public Item dequeue(){
        if(N==0){
            throw new java.util.NoSuchElementException();
        }
        int location = StdRandom.uniform(0,N);
        Item item = queue[location];
        for(int i=location;i<N-1;i++)
        {
            queue[i]=queue[i+1];
        }
        N--;
        queue[N]=null;
        if(N == queue.length/4) resize(queue.length/2);
        return item;
    }                    // remove and return a random item
    public Item sample(){
        if(!isEmpty()){
            int location = StdRandom.uniform(0,N);
            return queue[location];
        }
        else{
            throw new java.util.NoSuchElementException();
        }
    }// return (but do not remove) a random item
    private void resize(int length){
        Item[] temp = (Item[]) new Object[length];
        for(int i=0;i<N;i++)
            temp[i]=queue[i];
        queue = temp;
    }
public Iterator<Item> iterator() {
    return new ListIterator();
}// return an iterator over items in order from front to end

    private class ListIterator implements Iterator<Item> {
        Item[] backup;
        int loc = 0;
        ListIterator(){
            if(queue == null){
                throw new java.util.NoSuchElementException();
            }
            backup = queue.clone();
            StdRandom.shuffle(backup,0,N);


        }
        public boolean hasNext() {
            return loc < N;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            return backup[loc++];
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
    public static void main(String[] args){
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        rq.enqueue("YLFFIWVISJ");
        rq.enqueue("VZCLFKEAKG");
        rq.sample();
       for(String i:rq){
           StdOut.println(i);
       }
    }// unit testing (optional)
}