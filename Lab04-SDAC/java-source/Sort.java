import java.util.*;

public class Sort {

    /* Disponibile in libreria java */
    public void quickSortDefault(int[] array) {
        Arrays.sort(array);
    }

    public void mergeSort(int[] array) {
        // System.out.println("mergeSort non è ancora implementato");
        _mergeSort(array, 0, array.length - 1);
        return;
    }

    private void _mergeSort(int[] arr, int p, int r){
        if (p >= r) return;
        // System.out.println("Chiamata _mergeSort");
        // System.out.println("p = " + p + " r = " + r);
        int q = (p + r) / 2;
        _mergeSort(arr, p, q);
        _mergeSort(arr, q + 1, r);

        _merge(arr, p, q, r);
    }

    private void _merge(int[] arr, int p, int q, int r){
        
        int nL = q - p + 1;
        int nR = r - q;
        // System.out.println("Copio sottoliste ...");
        int[] arrL = new int[nL];
        int[] arrR = new int[nR];

        for(int i = 0; i < nL; i++) arrL[i] = arr[p + i];
        for(int j = 0; j < nR; j++) arrR[j] = arr[q + j + 1];

        // System.out.println("Copio liste");
        int i = 0;
        int j = 0;
        int k = p;
        while (i < nL && j < nR){
            if(arrL[i] < arrR[j]){
                // System.out.println("Copio " + arrL[i] + " da L");
                arr[k] = arrL[i];
                i++;
            } else {
                // System.out.println("Copio " + arrR[j] + " da R");
                arr[k] = arrR[j];
                j++;
            }
            k++;
        }

        // System.out.println("Copio lista più lunga");
        //System.out.printf("i = %d, j = %d, nL = %d, nR = %d\n", i, j, nL, nR);
        while (i < nL){
            //System.out.printf("i = %d nL = %d,\n", i, nL);
            arr[k] = arrL[i];
            i++;
            k++;
        }

        while (j < nR){
            arr[k] = arrR[j];
            j++;
            k++;
        }
    }

    public void heapSort(int[] array) {
        int n = _parent(10);
        System.out.println(n);
        return;
    }

    private int _parent(int i){
        if (i <= 0) return 0;
        return i / 2;
    }

    private int _left(int i){
        if (i <= 0) return 0;
        return i * 2;
    }

    private int _right(int i){
        if (i <= 0) return 0;
        return (i * 2) + 1;
    }

    private void _maxHeapify (int[] array, int i){

        int l = _left(i);
        int r = _right(i);

        
    }

    public void insertionSort(int[] array) {
        // System.out.println("insertionSort non è ancora implementato");
        int n = array.length;
        for(int i = 0; i < n; i++){
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key){
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
    }

    public void selectionSort(int[] array) {
        // System.out.println("selectionSort non è ancora implementato");
        int n = array.length;
        int min, temp;
        int i = 0;

        while (i < n - 1){
            // i è l'indice di partenza dell'iterazione
            min = i;
            temp = array[i];
            int j = i + 1;

            while (j < n){
                // otteniamo il minimo dell'iterazione
                min = (array[j] < array[min]) ? j : min;
                j++;
            }

            // scambiamo il minimo con l'indice di partenza
            array[i] = array[min];
            array[min] = temp;

            i++;
        }
        return;
    }

    public void quickSort(int[] array) {
        // System.out.println("quickSort non è ancora implementato");
        _quickSort(array, 0, array.length - 1);
        return;
    }

    private void _quickSort(int[] array, int p, int r){
        if (p < r){
            int q = _partiziona(array, p, r);
            _quickSort(array, p , q-1);
            _quickSort(array, q+1 , r);
        }
        return;
    }

    private int _partiziona(int[] array, int p, int r){
        int x = array[r];
        int i = p - 1, j = p;
        int temp;

        while (j < r){
            if (array[j] <= x){
                i++;
                temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
            j++;
        }
        temp = array[i+1];
        array[i+1] = x;
        array[r] = temp;
        return i+1;
    }

    public void radixSort(int[] array) {
        System.out.println("radixSort non è ancora implementato");
        return;
    }

    public void bucketSort(int[] array) {
        System.out.println("bucketSort non è ancora implementato");
        return;
    }
}
