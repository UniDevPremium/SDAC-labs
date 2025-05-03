/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
/**
 *
 * @author silvia
 */
public class Algoritmi {
    
     
    public static void permuta_negativi_positivi(float [] a){
        int j = 0, i = -1;
        while(j < a.length){
            // scorriamo la lista
            float key = a[j];
            if (key < 0){
                // avanza i e scambia a[i] con a[j]
                i++;
                if(i < j){
                    a[j] = a[i];
                    a[i] = key;
                }
            }
            j++;
        }
        // System.out.println("Metodo non ancora implementato... Risultato non disponibile");
        return;
}
    
    public static void bandiera(char[] a){

        int i = 0, j = 0, k = a.length - 1;

        while (i < k){
            char key = a[j];
            if (key == 'v'){
                a[j] = a[i];
                a[i] = key;
                i++;
                j++;
            } else if (key == 'b'){
                j++;
            } else {
                a[j] = a[k];
                a[k] = key;
                k--;
            }
        }
        // System.out.println("Metodo non ancora implementato... Risultato non disponibile");
        return;
    }
    
       
    public static boolean isQuadratoLatino(int[][] m){
        int n = m.length;

        ArrayList<HashSet<Integer>> row_sets = new ArrayList<>();
        ArrayList<HashSet<Integer>> col_sets = new ArrayList<>();

        for (int k = 0; k < n; k++){
            if(m[k].length != n) return false; 
            row_sets.add(new HashSet<>());
            col_sets.add(new HashSet<>());
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                int key = m[i][j];
                if(key < 1 || key > n) return false;
                if(!row_sets.get(i).add(key) || !col_sets.get(j).add(key)) return false;
            }
        }

        // System.out.println("Metodo non ancora implementato... Risultato non disponibile");
        return true;
    }
    
        
    public static float[] primiKMin(float[] a, int k){

        if (k < 1 || k > a.length) return new float[0];

        PriorityQueue<Float> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int i = 0; i < k; i++){
            maxHeap.add(a[i]);
        }

        for (int i = k; i < a.length; i++){
            float key = a[i];
            if (key < maxHeap.peek()){
                maxHeap.poll();
                maxHeap.add(key);
            }
        }

        float[] res = new float[k];
        int i = 0;
        while(!maxHeap.isEmpty()){
            res[i] = maxHeap.poll();
            i++;
        }
        // System.out.println("Metodo non ancora implementato... Risultato non disponibile");
        return res;
    }
}
