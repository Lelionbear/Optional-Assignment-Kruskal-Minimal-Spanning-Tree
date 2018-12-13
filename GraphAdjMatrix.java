import java.util.*;

public class GraphAdjMatrix implements Graph{
    private int vertices;
    private int edges;
    private ArrayList<Edge> kruskalMST;

    class Edge implements Comparable<Edge>
    {
        int src, dest, weight;
        public Edge(int s, int d, int w)
        {
            src = s;
            dest = d;
            weight = w;
        }

        @Override
        public int compareTo(Edge o) {
            if (this.weight < o.weight)
                return -1;
            else if (this.weight > o.weight)
                return  1;
            return 0;
        }
    }



    public GraphAdjMatrix(int inputVertices)
    {
        this.vertices = inputVertices;
        this.edges = 0;
        this.kruskalMST = new ArrayList<>();
    }

    @Override
    public int getEdge(int v1, int v2)
    {
        try
        {
            for (Edge e: kruskalMST)
            {
                if (e.src == v1 && e.dest == v2)
                {
                    return e.weight;
                }
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            //do nothing
        }
        return 0;
    }

    @Override
    public int createSpanningTree()
    {
        ArrayList<Edge> copyWeightList = new ArrayList<>(kruskalMST);
        Collections.sort(copyWeightList);
        kruskalMST.clear();

        int disjointSet[] = new int[vertices];
        createSet(disjointSet);
        int oldNumOfEdges = edges;
        edges = 0;
        for(int i = 0; i < oldNumOfEdges; i++)
        {
            Edge edge = copyWeightList.remove(0);
            int setA = find(disjointSet,edge.src);
            int setB = find(disjointSet,edge.dest);

            if (setA != setB){
                kruskalMST.add(edge);
                edges++;
                union(disjointSet,setA,setB);
            }
        }

        int totalWeight = 0;
        for (Edge e: kruskalMST) {
            totalWeight+=e.weight;
        }

        return totalWeight;
    }

    public void createSet(int [] parent){
        // makes a disjoint set by setting each vertex element as a parent to itself
        for (int i = 0; i < vertices ; i++) {
            parent[i] = i;
        }
    }

    public int find(int [] parent, int vertex){
        if(parent[vertex] != vertex)
            return find(parent, parent[vertex]); // returns the vertex parent from the set
        return vertex;
    }

    public void union(int [] parent, int v1, int v2){
        int a_set_parent = find(parent, v1);
        int b_set_parent = find(parent, v2);
        parent[b_set_parent] = a_set_parent; // make a the parent of b
    }

    @Override
    public void addEdge(int v1, int v2) {
        addEdge(v1,v2,0);
    }

    @Override
    public void topologicalSort() {
        return;
    }

    @Override
    public void addEdge(int v1, int v2, int weight) {
        try
        {
            Edge nEdge = new Edge(v1,v2,weight);
            kruskalMST.add(nEdge);
            edges++;
        }
        catch (IndexOutOfBoundsException e)
        {
            // do nothing
        }
    }
}