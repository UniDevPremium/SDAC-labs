public class MaxGap {

    public static int maxGap(int[] array, int start, int end) {
        // array ordinato quindi a[end] >= a[start]

        /////////////////NON FUNZIONA//////////////
        // if (end <= start) return 0;
        
        // //if (end == start + 1) return array[end] - array[start];
        // if (end == start + 1) {
        //     System.out.printf("s = %d, e = %d\n", start, end);
        //     System.out.printf("a[s] = %d, a[e] = %d\n", array[start], array[end]);
        //     int g = array[end] - array[start];
        //     System.out.printf("gap = %d\n", g);
        //     return
        // }

        // // len >= 3
        // int len = 1 + end - start;
        // int i = start + (len / 2);

        // // chiamata sx
        // int gapsx = maxGap(array, start, i);
        // int gapdx = maxGap(array, i+1, end);
        // return (gapsx > gapdx) ? gapsx : gapdx;


        ////////////////// FUNZIONA ///////////////////////
        // if (end <= start + 1) return 0;
        // if (end == start + 2) return array[end - 1] - array[start];
        
        // int len = end - start;
        // int i = start + (len/2) + 1;
        // int g = array[i] - array[i-1];

        // int gs = maxGap(array, start, i);
        // int gd = maxGap(array, i, end);

        // return Math.max(Math.max(gs, gd), g);
        
        ////////////// FUNZIONA e mi piace di più //////////////
        if(end == start + 1) return 0;
        if(end == start + 2) return array[end - 1] - array[start];

        int len = end - start;
        int i = start + len / 2;

        int gs = maxGap(array, start, i+1);
        int gd = maxGap(array, i, end);
        return (gs > gd) ? gs : gd;
    }
}
