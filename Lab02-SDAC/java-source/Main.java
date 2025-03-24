import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        MatriceSparsa mat1 = readMatFF("mat.dat");
        mat1.set(0, 1, 5);
        System.out.println("Original Matrix1:");
        System.out.println(mat1);
        MatriceSparsa mat2 = readMatFF("mat2.dat");
        mat2.set(1,0, 3);
        System.out.println("Original Matrix2:");
        System.out.println(mat2);

        System.out.println("Prodotto riga per colonna");
        MatriceSparsa prod = mat1.mul(mat2);
        System.out.println(prod);
        // MatriceSparsa sumTest = MatriceSparsa.add(mat1, mat2);
        // System.out.println("Matrice Somma:");
        // System.out.println(sumTest);

        // MatriceSparsa mt1 = mat1.tra();
        // System.out.println("Matrice 1 trasposta:");
        // System.out.println(mt1);
        // MatriceSparsa mt2 = mat2.tra();
        // System.out.println("Matrice 2 trasposta:");
        // System.out.println(mt2);
        // MatriceSparsa mt3 = sumTest.tra();
        // System.out.println("Matrice Sum trasposta:");
        // System.out.println(mt3);


        // System.out.println("##################################");
        // System.out.println("##################################");
        // System.out.println("");
        // System.out.println("");
        // System.out.println("Added -> 0,0,5");
        // mat1.set(0, 0, 5);
        // // System.out.println(mat1);
        // System.out.println("Cella (0,0) = " + mat1.get(0,0));
        // System.out.println("");
        // System.out.println("Added -> 10,10,45");
        // mat1.set(10, 10, 45);
        // // System.out.println(mat1);
        // System.out.println("Cella (10,10) = " + mat1.get(10,10));
        // System.out.println("");
        // System.out.println("Added -> 10,10,7");
        // mat1.set(10, 10, 7);
        // // System.out.println(mat1);
        // System.out.println("Cella (10,10) = " + mat1.get(10,10));
        // System.out.println("");
        // System.out.println("Added -> 24,29,1");
        // mat1.set(24, 29, 1);
        // // System.out.println(mat1);
        // System.out.println("Cella (24,29) = " + mat1.get(24,29));
        // System.out.println("");
        // System.out.println("Added -> 24,29,5");
        // mat1.set(24, 29, 5);
        // // System.out.println(mat1);
        // System.out.println("Cella (24,29) = " + mat1.get(24,29));
        // System.out.println("");
        // System.out.println("Added -> 18,17,12");
        // mat1.set(18, 17, 12);
        // // System.out.println(mat1);
        // System.out.println("Cella (18,17) = " + mat1.get(18,17));
        // System.out.println("");
    }

    private static MatriceSparsa readMatFF(String file) {
        FileReader fr;
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            int m,n;
            String line = br.readLine();
            StringTokenizer st = new StringTokenizer(line);
            m = Integer.parseInt(st.nextToken());
            n = Integer.parseInt(st.nextToken());
            MatriceSparsa mat = new MatriceSparsa(m,n);
            for(int i = 0; i < m; i++) {
                line = br.readLine();
                st = new StringTokenizer(line);
                for(int j = 0; j < n; j++) {
                    int x = Integer.parseInt(st.nextToken());
                    mat.set(i, j, x);
                }
            }
            br.close();
            System.out.println("Matrice letta");
            return mat;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
