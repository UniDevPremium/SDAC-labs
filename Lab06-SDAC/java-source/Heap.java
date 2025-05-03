public class Heap {

    public enum HEAP_TYPE {MAX_HEAP, MIN_HEAP};

    static class HeapEntry {
        private int key;
        private int position; // Indice della posizione dell'array in cui si trova l'elemento

        private HeapEntry(int v, int i){
            this.key = v;
            this.position = i;
        }
    }

    private HeapEntry[] heap;
    private int heapSize;
    private HEAP_TYPE heapType;

    public Heap(HEAP_TYPE type, int capacity) {
        if(capacity < 0 || type == null) throw new RuntimeException("Tipo nullo o capacita negativa");

        this.heap = new HeapEntry[capacity];
        this.heapType = type;
        this.heapSize = 0;
    }

    public HEAP_TYPE getType() {
        return this.heapType;
    }

    public int peek() {
        HeapEntry root = this.heap[0];
        if(root == null) throw new RuntimeException("Radice non inizializzata");
        return this.getEntryKey(root);
    }

    public HeapEntry add(int key) {
        if(this.heapSize == this.heap.length)
            throw new RuntimeException("Heap pieno");
        
        return heapInsert(key);
    }



    public int getEntryKey(HeapEntry e) {
        if (e == null)
            throw new RuntimeException("HeapEntry nullo");
        return e.key;
    }

    public int size() {
        return this.heapSize;
    }

    public int poll() {
        if (this.heapSize <= 0)
            throw new RuntimeException("Heap underflow");

        HeapEntry root = heapExtract();

        return this.getEntryKey(root);
    }

    public void print() {
        for(int i = 0; i < this.heapSize; i++){
            System.out.print(" " + this.getEntryKey(this.heap[i]));
        }
        System.out.print("\n");
    }

    public static void heapSort(int[] array) {
        Heap heap = array2heap(array, HEAP_TYPE.MIN_HEAP);
        for (int i = 0; i < array.length; i++){
            array[i] = heap.poll();
        }
    }

    public void updateEntryKey(HeapEntry e, int key) {
        if (e == null)
            throw new RuntimeException("HeapEntry nullo");

        if (e.key < key){
            if (this.heapType == HEAP_TYPE.MAX_HEAP) {
                int i = this.heapRiseKey(e.position, key);
            }
            else {
                e.key = key;
                this.heapifyDown(e.position);
            }
        }
        if (e.key > key){
            if (this.heapType == HEAP_TYPE.MAX_HEAP) {
                e.key = key;
                this.heapifyDown(e.position);
            } else {
                int i = this.heapRiseKey(e.position, key);
            }
        }
    }

    public static Heap array2heap ( int [] array , HEAP_TYPE type ){
        
        if (array == null)
            throw new RuntimeException("Array nullo");

        Heap res = new Heap(type, array.length);
        res.heapSize = array.length;

        for (int i = 0; i < array.length; i++){
            res.heap[i] = new HeapEntry(array[i], i);
        }
        res.print();
        res.heapifyUp();
        return res;
    }

    // AUSILIARIE

    public boolean isFull(){
        return this.size() == this.heap.length;
    }

    public boolean isEmpty(){
        return this.size() == 0;
    }

    private int parent(int i){
        return (i - 1)/2;
    }

    private int left(int i){
        return 2*i + 1;
    }

    private int right(int i){
        return 2 * i + 2;
    }

    private void swap(int i, int j){
        HeapEntry temp = this.heap[i];
        this.heap[i] = this.heap[j];
        this.heap[j] = temp;

        this.heap[i].position = i;
        this.heap[j].position = j;
    }

    private HeapEntry heapInsert(int key){
        int v = (this.heapType == heapType.MAX_HEAP) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        this.heap[this.heapSize] = new HeapEntry(v, this.heapSize);
        this.heapSize++;

        int pos = heapRiseKey(this.heapSize - 1, key);
        
        return this.heap[pos];
    }

    private int heapRiseKey(int i, int key){
        return (this.heapType == heapType.MAX_HEAP) ?  maxHeapRiseKey(i, key) : minHeapRiseKey(i, key);
    }

    private int maxHeapRiseKey(int i, int key){
        if (key < this.getEntryKey(heap[i]))
            throw new RuntimeException("New key is smaller than current key");
        
        this.heap[i].key = key;

        while (i > 0 && this.getEntryKey(heap[parent(i)]) < this.getEntryKey(heap[i])){
            swap(i, parent(i));
            i = parent(i);
        }
        return i;
    }

    private int minHeapRiseKey(int i, int key){
        if (key > this.getEntryKey(heap[i]))
            throw new RuntimeException("New key is smaller than current key");
        
        this.heap[i].key = key;

        while (i > 0 && this.getEntryKey(heap[parent(i)]) > this.getEntryKey(heap[i])){
            swap(i, parent(i));
            i = parent(i);
        }
        return i;
    }

    private HeapEntry heapExtract(){
        HeapEntry root = this.heap[0];

        this.heap[0] = this.heap[this.heapSize - 1];
        this.heap[0].position = 0;

        this.heapSize--;

        heapifyDown(0);

        return root;
    }


    private void heapifyDown(int i){
        if (this.heapType == heapType.MAX_HEAP) maxHeapifyDown(i);
        else minHeapifyDown(i);
    }

    private void maxHeapifyDown(int i){
        
        int l = left(i);
        int r = right(i);
        int max = i;

        //System.out.println("Size " + this.heapSize);
        //System.out.println("MaxHeapifyDown - i: " + i + ", l: " + l + ", r: " + r);


        if (l < this.heapSize && this.getEntryKey(this.heap[l]) > this.getEntryKey(this.heap[max]))
            max = l;
        if (r < this.heapSize && this.getEntryKey(this.heap[r]) > this.getEntryKey(this.heap[max]))
            max = r;

        //System.out.println("MaxHeapifyDown - after comparisons: max = " + max);

        if (max != i){
            swap(i, max);
            maxHeapifyDown(max);
        }
    }

    private void minHeapifyDown(int i){

        int l = left(i);
        int r = right(i);
        int min = i;

        if (l < heapSize && this.getEntryKey(this.heap[l]) < this.getEntryKey(this.heap[min]))
            min = l;
        if (r < heapSize && this.getEntryKey(this.heap[r]) < this.getEntryKey(this.heap[min]))
            min = r;

        if (min != i){
            swap(i, min);
            minHeapifyDown(min);
        }
    }

    private void heapifyUp(){
        for (int i = this.heapSize / 2 - 1; i >= 0; i--){
            heapifyDown(i);
        }
    }

}
