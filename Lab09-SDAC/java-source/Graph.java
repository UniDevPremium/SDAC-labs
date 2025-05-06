import java.util.LinkedList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.StringBuilder;



public class Graph<V> {

    private HashSet<Node<V>> nodeSet; 

    public Graph () {
        this.nodeSet = new HashSet<>();
    }

    public List<Node<V>> getNodes() {
        if(this.nodeSet == null) return null;
        return new LinkedList<>(this.nodeSet);
    }

    public List<Node<V>> getNeighbors(Node<V> n) {
        if(this.nodeSet == null || n == null) return null;
        if(this.nodeSet.contains(n)){
            if(n.outEdges == null) return null;
            return n.outEdges;
        } 
        return null;
    }

    public Node addNode(V value) {
        if(this.nodeSet == null) return null;
        Node<V> n = new Node<>(value);
        this.nodeSet.add(n);
        return n;
    }

    public void addEdge(Node<V> s, Node<V> t) {
        if( s == null ||
            t == null ||
            !this.nodeSet.contains(s) ||
            !this.nodeSet.contains(s)) return;

        if(!s.outEdges.contains(t)) s.outEdges.add(t);
        if(!t.outEdges.contains(s)) t.outEdges.add(s);
        return;
    }

    public V getNodeValue(Node<V> n) {
        if(n == null) return null;
        return n.value;
    }

    public void removeEdge(Node<V> v1, Node<V> v2) {
        if( v1 == null ||
            v2 == null ||
            !this.nodeSet.contains(v1) ||
            !this.nodeSet.contains(v2)) return;

        if(v1.outEdges.contains(v2)) v1.outEdges.remove(v2);
        if(v2.outEdges.contains(v1)) v2.outEdges.remove(v1);
    }

    public void removeNode(Node<V> v) {
        if(v == null) return;
        v.outEdges.clear();
        if(this.nodeSet == null) return;
        this.nodeSet.remove(v);
    }

    private Node<V> getNodeByValue(V value) {
    for (Node<V> node : nodeSet) {
        if (node.value.equals(value)) {
            return node;
        }
    }
    return null;
}

    public static <V> Graph<V> readFF(File input) {
        if(input == null || !input.exists() || !input.isFile()) return null;
        try{
            Scanner sc = new Scanner(input);
            Graph<V> g = new Graph<>();
            int scan = 0;
            while(sc.hasNextLine()){
                String row = sc.nextLine();
                String[] parts = row.split(" ");
                if(scan == 0){
                    scan = 1;
                    for(int i = 0; i < Integer.parseInt(parts[0]); i++) g.addNode((V)String.valueOf(i+1));
                    continue;
                }
                
                Node<V> node1 = g.getNodeByValue((V) parts[0]);
                Node<V> node2 = g.getNodeByValue((V) parts[1]);
                g.addEdge(node1, node2);
            }
            return g;
        } catch(FileNotFoundException e){
            System.err.println("File non trovato.");
            return null;
        }
    }

    public String printAdj() {
        StringBuilder sb = new StringBuilder();
        for (Node<V> node : nodeSet) {
            sb.append(node.value).append(": ");
            for (Node<V> neighbor : node.outEdges) {
                sb.append(neighbor.value).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node<V> node : nodeSet) {
            sb.append(node.toString()).append("\n");
        }
        return sb.toString();
    }

    private void exploreComp(Node<V> start){
        start.state = Node.Status.EXPLORING;

        for(Node<V> neigh : start.outEdges){
            if(neigh.state == Node.Status.UNEXPLORED){
                exploreComp(neigh);
            }
        }

        start.state = Node.Status.EXPLORED;
    }

    public int nConComp() {
        int count = 0;

        for(Node<V> node : this.nodeSet){
            node.state = Node.Status.UNEXPLORED;
        }

        for(Node<V> node : this.nodeSet){
            if(node.state == Node.Status.UNEXPLORED){
                exploreComp(node);
                count++;
            }
        }

        return count;
    }

    private void exploreComp(Node<V> start, Graph<V> g){
        LinkedList<Node<V>> stack = new LinkedList<>();
        stack.push(start);
        start.state = Node.Status.EXPLORING;

        Map<Node<V>, Node<V>> nodicp = new HashMap<>();
        nodicp.put(start, g.addNode(start.value));

        while(!stack.isEmpty()){
            Node<V> curr = stack.pop();
            curr.state = Node.Status.EXPLORED;

            for(Node<V> neigh : curr.outEdges){
                if(!nodicp.containsKey(neigh)){
                    nodicp.put(neigh, g.addNode(neigh.value));
                }

                g.addEdge(nodicp.get(curr), nodicp.get(neigh));

                if(neigh.state == Node.Status.UNEXPLORED){
                    neigh.state = Node.Status.EXPLORING;
                    stack.push(neigh);
                }
            }
        }
    }

    public List<Graph<V>> getConComp() {
        List<Graph<V>> components = new LinkedList<>();

        for(Node<V> node : this.nodeSet){
            node.state = Node.Status.UNEXPLORED;
        }

        for(Node<V> node : this.nodeSet){
            if(node.state == Node.Status.UNEXPLORED){
                Graph<V> comp = new Graph<>();
                exploreComp(node, comp);
                components.add(comp);
            }
        }

        return components;
    }

    /* Classe interna che descrive il generico nodo del grafo */
    public static class Node<V> implements Cloneable {
        public enum Status {UNEXPLORED, EXPLORED, EXPLORING}

        protected V value;
        protected LinkedList<Node<V>> outEdges;

        protected Status state; // tiene traccia dello stato di esplorazione

        protected Node(V value){
            if(value == null) return;
            this.value = value;
            this.outEdges = new LinkedList<>();
            this.state = Node.Status.UNEXPLORED;
        }

        @Override
        public String toString() {
            return "Node [value=" + value + ", state=" + state + "]";
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
