public class Kth {

    public Kth(int k) {
        this.k = k;
        this.low = new Heap(Heap.HEAP_TYPE.MAX_HEAP, this.k - 1);
        this.high = new Heap(Heap.HEAP_TYPE.MIN_HEAP, 1);
    }

    private final int k;
    // manteniamo i k - 1 piu piccoli in un max heap
    // restanti n + 1 - k sono in un min heap
    private Heap low;
    private Heap high;

    public void insert(int key) {
        System.out.println("Insert " + key);
        if (low.isFull()){
            if(key < low.peek()){
                // da inserire in low
                ensureHigh();
                high.add(low.poll());
                low.add(key);
            } else {
                // da inserire in high
                ensureHigh();
                high.add(key);
            }
        } else {
            low.add(key);
        }
    }

    public int get() {
        if (high.isEmpty()) return -1;
            // throw new RuntimeException("Non ci sono k elementi");

        return high.peek();
    }

    public void remove() {
        System.out.println("Remove");
        high.poll();
    }

    private void ensureHigh(){
        if(high.isFull()){
            Heap temp = new Heap(Heap.HEAP_TYPE.MIN_HEAP, high.size() + 1);
            while(!high.isEmpty()) temp.add(high.poll());
            high = temp;
        }
    }

}
