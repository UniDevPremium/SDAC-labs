import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;

public class ChainHashTable extends AbstractHashTable {
    // Un array di LinkedList per le liste di trabocco 
    private LinkedList<Entry> [] table;
    
    /* Costruttori */
    public ChainHashTable(int cap, int p, double lambda) {
        super(cap, p, lambda);
    }
    
    public ChainHashTable(int cap, int p) {
        super(cap, p); // massimo fattore di carico predefinito
    }
    
    public ChainHashTable(int cap) {
        super(cap); // primo predefinito
    }
    
    public ChainHashTable() {
        super(); // capacità predefinita
    }
    
    /* Metodi non implementati in AbstractHashTable */

    // Inizializza una tabella hash vuota secondo i parametri passati al costruttore
    @SuppressWarnings("unchecked")
    protected void createTable() {
        this.table = new LinkedList[this.getCapacity()];
        for(int i = 0; i < this.getCapacity(); i++){
            this.table[i] = new LinkedList<>();
        }
    }

    // Restituisce il valore (intero) associato alla chiave k
    // Restituisce -1 se la chiave è assente
    public int get(String k) {
        for(LinkedList<Entry> bucket : this.table){
            Iterator<Entry> it = bucket.iterator();
            while(it.hasNext()){
                Entry e = it.next();
                if(e.getKey().equals(k)){
                    return e.getValue();
                }
            }
        }
        return -1;
    }
    
    // Aggiorna il valore associato alla chiave k (o inserisce una nuova coppia se la chiave non è presente)
    // Restituisce il vecchio valore o -1 se la chiave non è presente
    public int put(String k, int value) {
        if((float) this.size() / this.getCapacity() >= this.getMaxLambda()) this.resize(this.getCapacity() * 2);
        int index = this.hashFunction(k);
        for(Entry e : this.table[index]){
            if(e.getKey().equals(k)){
                int old = e.getValue();
                e.setValue(value);
                return old;
            }
        }
        this.table[index].add(new Entry(k, value));
        this.incSize();
        return -1;
    }
    
    // Rimuove la coppia con chiave k
    // Restituisce il vecchio valore o -1 se la chiave non è presente
    public int remove(String k) {
        for(LinkedList<Entry> bucket : this.table){
            Iterator<Entry> it = bucket.iterator();
            while(it.hasNext()){
                Entry e = it.next();
                if(e.getKey().equals(k)){
                    it.remove();
                    return e.getValue();
                }
            }
        }
        return -1;
    }
    
    // Restituisce un oggetto Iterable contenente tutte le coppie presenti nella tabella hash
    public Iterable<Entry> entrySet() {
        LinkedList<Entry> entries = new LinkedList<>();
        for(LinkedList<Entry> bucket : this.table) entries.addAll(bucket);
        return entries;
    }
}
