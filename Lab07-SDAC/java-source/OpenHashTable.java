import java.util.LinkedList;

public class OpenHashTable extends AbstractHashTable {
    // Un array di Entry per la tabella
    private Entry[] table;
    // Marcatore di cella vuota ma da esaminare durante probing
    private final Entry DEFUNCT = new Entry(null, 0); 
    
    /* Costruttori */
    public OpenHashTable(int cap, int p, double lambda) {
        super(cap, p, lambda);
    }

    public OpenHashTable(int cap, int p) {
        super(cap, p); // massimo fattore di carico predefinito
    }
    
    public OpenHashTable(int cap) {
        super(cap); // primo predefinito
    }
    
    public OpenHashTable() {
        super(); // capacità predefinita
    }
        
    /* Metodi non implementati in AbstractHashTable */

    // Inizializza una tabella hash vuota secondo i parametri passati al costruttore
    protected void createTable() {
        this.table = new Entry[this.getCapacity()];
    }

    // Restituisce il valore (intero) associato alla chiave k
    // Restituisce -1 se la chiave è assente
    public int get(String k) {
        int i = this.findIndex(k);
        return (i != -1) ? this.table[i].getValue() : -1;
    }

    private int findIndex(String k) {
        int hash = this.hashFunction(k);
        for (int i = 0; i < this.getCapacity(); i++) {
            int idx = (hash + i * i) % this.getCapacity();
            Entry e = table[idx];
            if (e == null) return -1;
            if (e != DEFUNCT && e.getKey().equals(k)) return idx;
        }
        return -1;
    }
    
    // Aggiorna il valore associato alla chiave k
    // Restituisce il vecchio valore o -1 se la chiave non è presente
    public int put(String k, int value) {

        if ((float) this.size() / this.getCapacity() > this.getMaxLambda()) resize(this.nextPrime(this.getCapacity() * 2 + 1));

        int i = this.findIndex(k);
        if(i != -1){
            int old = this.table[i].getValue();
            this.table[i].setValue(value);
            return old;
        }
        
        int slot = this.findAvailableSlot(k);
        if(slot != -1){
            this.table[slot] = new Entry(k, value);
            this.incSize();
        }
        return -1;
    }

    private int findAvailableSlot(String k) {
        int hash = this.hashFunction(k);
        for (int i = 0; i < this.getCapacity(); i++) {
            int idx = (hash + i * i) % this.getCapacity();
            Entry e = table[idx];
            if (e == null || e == DEFUNCT) return idx;
        }
        return -1;
    }
    
    // Rimuove la coppia con chiave k
    // Restituisce il vecchio valore o -1 se la chiave non è presente
    public int remove(String k) {
        int i = this.findIndex(k);
        if(i != -1) {
            int old = this.table[i].getValue();
            this.table[i] = DEFUNCT;
            this.decSize();
            return old;
        }
        return -1;
    }
    
    // Restituisce un oggetto Iterable contenente tutte le coppie presenti nella tabella hash
    public Iterable<Entry> entrySet() {
        LinkedList<Entry> entries = new LinkedList<>();
        for(Entry e : this.table){
            if(e != null && e != DEFUNCT) entries.add(e);
        }
        return entries;
    }

    private boolean isPrime(int n){
        if(n <= 3) return n > 1;
        if(n % 2 == 0 || n % 3 == 0) return false;
        for(int i = 5; i * i <= n; i += 6)
            if(n % i == 0 || n % (i + 2) == 0) return false;
        return true;
    }

    private int nextPrime(int x){
        if(x <= 2) return 2;
        if(x % 2 == 0) x++;
        while(!isPrime(x)) x += 2;
        return x;
    }
}


