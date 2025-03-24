public class Labirinto {

    private static enum Cella { VUOTA, PIENA };

    private int n;
    private Cella m[][];
    private boolean marcata[][];

    public Labirinto(int n) {
        this.n = n;
        this.m = new Cella[this.n][this.n];
        this.marcata = new boolean[this.n][this.n];

        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                this.m[i][j] = Cella.VUOTA;
            }
        }
    }

    public void setPiena(int r, int c) {
       if (r < 0 || c < 0 || r >= n || c >= n) return;
       this.m[r][c] = Cella.PIENA;
    }

    private boolean uscita(int r, int c) {
        return r == this.n - 1 && c == this.n - 1;
    }

    public boolean percorribile(int r, int c) {
        if (r < 0 || c < 0 || r >= n || c >= n) return false;
        return this.m[r][c] == Cella.VUOTA;
    }

    private boolean uscitaRaggiungibileDa(int r, int c) {
        if(r < 0 || c < 0 || r >= n || c >= n) {
            System.out.println("Indici");
            return false;
        }
        if(!percorribile(r,c)){
            System.out.println("Non percorribile");
            return false;
        } 
        if(uscita(r,c)) return true;
        // è percorribile e non è l'uscita
        // se marcata non va controllata e assumiamo sia non raggiungibileda
        System.out.println("Inizio ricerca...");
        setMarcata(r,c);
        boolean sx = (isMarcata(r,c-1)) ? false : uscitaRaggiungibileDa(r,c-1);
        setMarcata(r, c-1);
        boolean dx = (isMarcata(r,c+1)) ? false : uscitaRaggiungibileDa(r,c+1);
        setMarcata(r, c+1);
        boolean up = (isMarcata(r-1,c)) ? false : uscitaRaggiungibileDa(r-1,c);
        setMarcata(r-1, c);
        boolean down = (isMarcata(r+1,c)) ? false : uscitaRaggiungibileDa(r+1,c);
        setMarcata(r+1, c);
        boolean ragg = sx || dx || up || down;
        return ragg;
    }

    private boolean isMarcata(int r, int c){
        if (r < 0 || c < 0 || r >= n || c >= n) return true;
        return this.marcata[r][c];
    }
    private void setMarcata(int r, int c){
        if (r < 0 || c < 0 || r >= n || c >= n) return;
        this.marcata[r][c] = true;
    }

    private void clearMarcata(){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                this.marcata[i][j] = false;
            }
        }
    }

    public boolean risolvibile() {
        clearMarcata();
        return uscitaRaggiungibileDa(0,0);
    }

    public String toString() {
        String str = " ";
        for (int k = 0; k < n; k++){
            str += " " + k;
        }
        str += '\n';
        for (int i = 0; i < n; i++){
            str += i + " ";
            for(int j = 0; j < n; j++){
                String c = (this.m[i][j] == Cella.PIENA) ? "# " : ". ";
                str += c;
            }
            str += '\n';
        }
        return str;
    }
}
