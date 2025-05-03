import java.util.Arrays;

public class Azzardo {

    public static class GiocataVincente {
        int start; // Indice di partenza della giocata vincente
        int end; // Indice di fine della giocata vincente
        int maxWin; // Vincita massima della giocata vincente
        int[] subPlay; // Sequenza della giocata vincente

        public GiocataVincente(int start, int end, int maxWin, int[] subPlay) {
            this.start = start;
            this.end = end;
            this.maxWin = maxWin;
            this.subPlay = subPlay;
        }
    }

    public static GiocataVincente bruteForce(int[] listaPartite){
        int sum, head = 0, last = listaPartite.length - 1, bestSum = Integer.MIN_VALUE;
        for(int i = 0; i < listaPartite.length; i++){
            for(int j = i; j < listaPartite.length; j++){
                sum = 0;
                for(int k = i; k <= j; k++){
                    sum += listaPartite[k];
                }
                if(sum > bestSum){
                    head = i;
                    last = j;
                    bestSum = sum;
                }
            }
        }
        int[] sub = new int[last - head + 1];
        for(int i = head; i <= last; i++){
            sub[i - head] = listaPartite[i];
        }
        return new GiocataVincente(head, last, bestSum, sub);
    }
    


    public static GiocataVincente faster(int[] listaPartite){
        int sum, head = 0, last = 0, bestSum = Integer.MIN_VALUE;
        for(int i = 0; i < listaPartite.length; i++){
            sum = 0;
            for(int j = i; j < listaPartite.length; j++){
                sum += listaPartite[j];
                if(sum > bestSum){
                    head = i;
                    last = j;
                    bestSum = sum;
                }
            }
        }
        int[] sub = new int[last - head + 1];
        for(int i = head; i <= last; i++){
            sub[i - head] = listaPartite[i];
        }
        return new GiocataVincente(head, last, bestSum, sub);
    }



    public static GiocataVincente bolt(int[] listaPartite){
        return maxSub(listaPartite, 0, listaPartite.length - 1);
    }

    public static GiocataVincente maxSub(int[] arr, int left, int right){
        if(left == right)
            return new GiocataVincente(left, right, arr[left], new int[] {arr[left]});

        int mid = (left + right) / 2;

        GiocataVincente maxLeft = Azzardo.maxSub(arr, left, mid);
        GiocataVincente maxRight = Azzardo.maxSub(arr, mid + 1, right);
        GiocataVincente maxCross = Azzardo.maxCrossSub(arr, left, mid, right);

        // Restituisce il massimo tra i tre
        if (maxLeft.maxWin >= maxRight.maxWin && maxLeft.maxWin >= maxCross.maxWin) {
            return maxLeft;
        } else if (maxRight.maxWin >= maxLeft.maxWin && maxRight.maxWin >= maxCross.maxWin) {
            return maxRight;
        } else {
            return maxCross;
        }
    }

    public static GiocataVincente maxCrossSub(int[] arr, int left, int mid, int right){
        int sumSx = Integer.MIN_VALUE;
        int sum = 0;
        int maxLeft = mid;
        for(int i = mid; i >= left; i--){
            sum += arr[i];
            if(sum > sumSx){
                sumSx = sum;
                maxLeft = i;
            }
        }

        int sumDx = Integer.MIN_VALUE;
        sum = 0;
        int maxRight = mid + 1;
        for(int i = mid + 1; i <= right; i++){
            sum += arr[i];
            if(sum > sumDx){
                sumDx = sum;
                maxRight = i;
            }
        }

        int[] sub = new int[maxRight - maxLeft + 1];
        System.arraycopy(arr, maxLeft, sub, 0, sub.length);

        return new GiocataVincente(maxLeft, maxRight, sumSx + sumDx, sub);
    }
    
    public static GiocataVincente goldenSolution(int[] listaPartite) {
        int max = Integer.MIN_VALUE;
        int maxHere = 0, start = 0, end = 0, tempStart = 0;

        for(int i = 0; i < listaPartite.length; i++){
            maxHere += listaPartite[i];
            if(maxHere > max){
                max = maxHere;
                start = tempStart;
                end = i;
            }
            if(maxHere < 0){
                maxHere = 0;
                tempStart = i + 1;
            }
        }

        int[] sub = new int[end - start + 1];
        System.arraycopy(listaPartite, start, sub, 0, sub.length);

        return new GiocataVincente(start, end, max, sub);
    }
}
