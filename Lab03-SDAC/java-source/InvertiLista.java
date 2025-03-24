import java.util.*;

public class InvertiLista {

    public static void invertiLista(LinkedList<Integer> list) {
        /**
         * complessità O(n)
         * per ogni elemento della lista n facciamo operazioni a costo costante 1
         */
        
        if (list.isEmpty() || list.size() == 1) return;

        int x = list.removeFirst();

        invertiLista(list);
        
        list.addLast(x);
    }
}
