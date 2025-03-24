
public class MatriceSparsa {

    private class Elem{
        int i;
        int j;
        int x;
        Elem next;

        public Elem(int i, int j, int x){
            this.i = i;
            this.j = j;
            this.x = x;
            this.next = null;
        }

        public Elem(int i, int j, int x, Elem next){
            this.i = i;
            this.j = j;
            this.x = x;
            this.next = next;
        }

        public void setNext(Elem e){
            this.next = e;
        }
    }

    private int rows;
    private int cols;
    Elem head;


    public MatriceSparsa(int m, int n) {
        rows = m;
        cols = n;
        head = null;
    }

    public int getNumRow() {
        return rows;
    }

    public int getNumCol() {
        return cols;
    }

    public void set(int i, int j, int x) {
        // System.out.println("Set: "+ i +" " +j + " " + x);
        if(i >= rows || j >= cols) {
            System.out.printf("Indice non corretto, (%d, %d)", rows, cols);
            return;
        }

        Elem it = head;

        if(it == null){
            // System.out.println("Head nullo");
            head = new Elem(i, j, x);
            return;
        }

        if(it.i > i || (it.i == i && it.j >= j)){
            if(it.i == i && it.j == j){
                it.x = x;
                return;
            }
            head = new Elem(i, j, x, it);
            return;
        }
        // va dopo head
        while(it.next != null){
            Elem next = it.next;
            if(next.i > i || (next.i == i && next.j >= j)){
                // va prima o al posto di next
                if(next.i == i && next.j == j){
                    next.x = x;
                    return;
                }
                it.setNext(new Elem(i, j, x, next));
                return;
            }
            it = next;
        }
        // inserisci in coda
        it.setNext(new Elem(i, j, x));
        return;

        /////////////
        // if(it == null){
        //     head = new Elem(i, j, x);
        //     done = true;
        // }
        
        // if(done == false && ((it.i >= i && it.j >= j) || it.i > i)){
        //     if(it.i == i && it.j == j){
        //         // sostuisci
        //         it.x = x;
        //     } else {
        //         // cambia head
        //         head = new Elem(i, j, x, it);
        //     }
        //     done = true;
        // }
        // // sicuro va dopo it
        // while(done == false && it.next != null){
        //     // vediamo se va dopo it.next
        //     Elem next = it.next;
        //     if((next.i >= i && next.j >= j) || next.i > i){
        //         if(next.i == i && next.j == j){
        //             // sostituisci a next
        //             next.x = x;
        //         } else {
        //             // va prima di next
        //             it.next = new Elem(i, j, x, next);
        //         }
        //         done = true;
        //     } else {
        //         // va dopo next
        //         it = next;
        //     }
        // }

        // if(done == false){
        //     it.next = new Elem(i, j, x);
        // }
    }

    public int get(int i, int j) {
        if(i >= rows || j >= cols) {
            System.out.printf("Indice non corretto, (%d, %d)", rows, cols);
            return -1;
        }
        Elem it = head;
        while(it != null){
            if(it.i == i && it.j == j){
                return it.x;
            }
            it = it.next;
        }
        return 0;
    }

    public String toString() {
        String str = "\n";
        Elem it = head;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(it != null && it.i == i && it.j == j){
                    str += it.x + " ";
                    it = it.next;
                } else str += "0 ";
            }
            str += "\n";
        }
        return str;
    }

    public static MatriceSparsa add(MatriceSparsa mat1, MatriceSparsa mat2) {
        if(mat1.rows != mat2.rows || mat1.cols != mat2.cols){
            return null;
        }
        int r = mat1.rows;
        int c = mat2.cols;
        MatriceSparsa res = new MatriceSparsa(r,c);
        Elem it1 = mat1.head;
        Elem it2 = mat2.head;
        while(it1 != null){
            res.set(it1.i, it1.j, it1.x);
            it1 = it1.next;
        }
        while(it2 != null){
            int f = res.get(it2.i, it2.j);
            res.set(it2.i, it2.j, f + it2.x);
            it2 = it2.next;
        }
        return res;
    }

    public MatriceSparsa tra() {
        if(rows != cols){
            System.out.println("Matrice non quadrata");
            return null;
        }
        MatriceSparsa res = new MatriceSparsa(rows, cols);
        Elem it = head;
        while(it != null){
            res.set(it.j, it.i, it.x);
            it = it.next;
        }
        return res;
    }

    public MatriceSparsa mul(MatriceSparsa mat) {
        if(cols != mat.rows){
            System.out.println("Prodotti riga per colonna non possibile");
            return null;
        }

        MatriceSparsa res = new MatriceSparsa(rows, mat.cols);
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < mat.cols; j++){
                System.out.println("Cella "+ i + " " +j);
                int v = 0;
                for(int k = 0; k < cols; k++){
                    int v1 = get(i, k);
                    int v2 = mat.get(k, j);
                    System.out.println(v1 + " * " + v2);
                    v += v1 * v2;
                }
                if(v != 0) res.set(i, j, v);
            }
        }
        return res;
    }
}
