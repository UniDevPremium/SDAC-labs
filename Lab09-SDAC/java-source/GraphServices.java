import java.util.*;

public class GraphServices {

    public static <V> void bfs(Graph<V> g) {
        for (Node<V> node : g.getNodes()) {
            node.state = Node.Status.UNEXPLORED;
        }

        for(Node<V> n : g.getNodes()){
            if(n.state == Node.Status.UNEXPLORED){
                n.state = Node.Status.EXPLORING;
                n.dist = 0;
                Queue<Node<V>> queue = new LinkedList<>();
                queue.offer(n);

                while(!queue.isEmpty()){
                    Node<V> curr = queue.poll();
                    System.out.println(curr.getValue());

                    for(Edge<V> e : g.getOutEdges(curr)){
                        Node<V> t = e.getTarget();
                        if(t.state == Node.Status.UNEXPLORED){
                            t.state = Node.Status.EXPLORING;
                            t.dist = curr.dist + 1;
                            queue.offer(t);
                        }
                    }
                    curr.state = Node.Status.EXPLORED;
                }
            }
        }
    }

    public static <V> void sssp(Graph<V> g, Node<V> source) {
        for(Node<V> n : g.getNodes()){
            n.state = Node.Status.UNEXPLORED;
            n.dist = Integer.MAX_VALUE;
        }

        source.dist = 0;
        PriorityQueue<Node<V>> pq = new PriorityQueue<>((n1,n2) -> Integer.compare(n1.dist, n2.dist));
        pq.offer(source);

        while(!pq.isEmpty()){
            Node<V> curr = pq.poll();
            if(curr.state == Node.Status.EXPLORED) continue;
            curr.state = Node.Status.EXPLORED;

            for(Edge<V> e : g.getOutEdges(curr)){
                Node<V> target = e.getTarget();
                int weight = e.getWeight();

                if(curr.dist != Integer.MAX_VALUE && curr.dist + weight < target.dist){
                    target.dist = curr.dist + weight;

                    target.state = Node.Status.EXPLORING;
                    pq.offer(target);
                }
            }
        }

        for(Node<V> n : g.getNodes()){
            System.out.print(n.getValue());

            if(n.dist == Integer.MAX_VALUE){
                System.out.println("INF");
            } else{
                System.out.println(n.dist);
            }
        }

    }
    
    public static <V> void mst(Graph<V> G) {
        
        // Comparator<Edge<V>> edgecmp = new Comparator<Edge<V>>(){
        //     @Override
        //     public int compare(Edge<V> e1, Edge<V> e2){
        //         return e1.getWeight().compareTo(e2.getWeight());
        //     }
        // }((e1, e2) -> Integer.compare(e1.getWeight(), e2.getWeight()));

        PriorityQueue<Edge<V>> pq = new PriorityQueue<>((e1, e2) -> Integer.compare(e1.getWeight(), e2.getWeight()));
        Set<Edge<V>> edges = G.getEdges();
        pq.addAll(edges);

        int i = 0;
        Map<Node<V>, Integer> nodeToId = new HashMap<>();
        for(Node<V> n : G.getNodes()){
            n.map = i;
            nodeToId.put(n, i);
            i++;
        }

        Partition<V> partition = new Partition<>(G.getNodes());
        List<Edge<V>> mstEdges = new ArrayList<>();

        while(!pq.isEmpty() && mstEdges.size() < G.getNodes().size() - 1){
            Edge<V> e = pq.poll();
            Node<V> s = e.getSource();
            Node<V> t = e.getTarget();

            int sourceSetId = s.map;
            int targetSetId = t.map;

            List<Node<V>> sourceSet = partition.find(sourceSetId);
            List<Node<V>> targetSet = partition.find(targetSetId);

            if(sourceSet != targetSet){
                mstEdges.add(e);
                partition.union(sourceSetId, targetSetId);
            }
        }

        for(Edge<V> e : mstEdges){
            System.out.print(e.getSource().getValue().toString() + e.getTarget().getValue().toString()+ " ");
        }
        System.out.println();
    }
}
