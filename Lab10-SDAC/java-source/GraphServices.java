import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GraphServices<V> {

    private static int time = 0;

    private static <V> void dfsVisit(Graph.Node<V> node){
        node.state = Graph.Node.Status.EXPLORING;
        node.timestamp = ++time;

        for(Graph.Node<V> n : node.outEdges){
            if(n.state == Graph.Node.Status.UNEXPLORED){
                System.out.printf("%s -> %s : %s\n", node.toString(), n.toString(), "TREE");
                dfsVisit(n);
            } else if(n.state == Graph.Node.Status.EXPLORING){
                System.out.printf("%s -> %s : %s\n", node.toString(), n.toString(), "BACK");
            } else { // EXPLORED
                if(node.timestamp < n.timestamp){
                    System.out.printf("%s -> %s : %s\n", node.toString(), n.toString(), "FORWARD");
                } else {
                    System.out.printf("%s -> %s : %s\n", node.toString(), n.toString(), "CROSS");
                }
            }
        }
        node.state = Graph.Node.Status.EXPLORED;
    }

    public static <V> void sweep(Graph<V> g) {
        time = 0;

        for(Graph.Node<V> n : g.getNodes()){
            n.state = Graph.Node.Status.UNEXPLORED;
            n.timestamp = 0;
        }

        for(Graph.Node<V> n : g.getNodes()){
            if(n.state == Graph.Node.Status.UNEXPLORED){
                dfsVisit(n);
            }
        }        
    }

    private static <V> void dfsFinish(Graph.Node<V> node, Stack<Graph.Node<V>> stack){
        node.state = Graph.Node.Status.EXPLORING;
        node.timestamp = ++time;

        for(Graph.Node<V> n : node.outEdges){
            if(n.state == Graph.Node.Status.UNEXPLORED){
                dfsFinish(n, stack);
            }
        }

        stack.push(node);
        node.state = Graph.Node.Status.EXPLORED;
    }

    private static <V> void dfsComponent(Graph.Node<V> node, List<Graph.Node<V>> component){
        node.state = Graph.Node.Status.EXPLORING;
        component.add(node);

        for(Graph.Node<V> n : node.outEdges){
            if(n.state == Graph.Node.Status.UNEXPLORED){
                dfsComponent(n, component);
            }
        }

        node.state = Graph.Node.Status.EXPLORED;
    }


    private static <V> void dfsTopo(Graph.Node<V> node, Stack<V> stack){
        node.state = Graph.Node.Status.EXPLORING;

        for(Graph.Node<V> n : node.outEdges){
            if(n.state == Graph.Node.Status.UNEXPLORED){
                dfsTopo(n, stack);
            } 
            else if(n.state == Graph.Node.Status.EXPLORING)
                throw new IllegalArgumentException("Il grafo non è aciclico");
        }

        stack.push(node.value);
        node.state = Graph.Node.Status.EXPLORED;
    }

    
    public static <V> void topologicalSort(Graph<V> g) {
        Stack<V> stack = new Stack<>();

        for(Graph.Node<V> n : g.getNodes()) n.state = Graph.Node.Status.UNEXPLORED;
        
        for(Graph.Node<V> n : g.getNodes()){
            if(n.state == Graph.Node.Status.UNEXPLORED){
                try{
                    dfsTopo(n, stack);
                } catch(IllegalArgumentException e){
                    System.out.println("Errore: " + e.getMessage());
                    return;
                }
            }
        }

        List<V> result = new ArrayList<>();
        while(!stack.isEmpty()) result.add(stack.pop());
        System.out.println("Ordinamento topologico: " + String.join(", ", result.stream().map(Object::toString).toList()));
    }

    
    public static <V> void strongConnectedComponents(Graph<V> g) {
        Stack<Graph.Node<V>> stack = new Stack<>();
        time = 0;

        for(Graph.Node<V> n : g.getNodes()){
            n.state = Graph.Node.Status.UNEXPLORED;
            n.timestamp = 0;
        }

        for(Graph.Node<V> n : g.getNodes()){
            if(n.state == Graph.Node.Status.UNEXPLORED)
                dfsFinish(n, stack);
        }

        Graph<V> gt = g.transpose();

        for(Graph.Node<V> n : gt.getNodes()){
            n.state = Graph.Node.Status.UNEXPLORED;
        }

        while(!stack.isEmpty()){
            Graph.Node<V> n = gt.addNode(stack.pop().value);
            if(n.state == Graph.Node.Status.UNEXPLORED){
                List<Graph.Node<V>> component = new ArrayList<>();
                dfsComponent(n, component);
                System.out.print("Sottografo fortemente connesso:\n");
                for(int i = 0; i < component.size(); i++){
                    System.out.print(component.get(i));
                    if(i < component.size() - 1) System.out.print(", ");
                }
                System.out.println();
            }
        }
    }
}
