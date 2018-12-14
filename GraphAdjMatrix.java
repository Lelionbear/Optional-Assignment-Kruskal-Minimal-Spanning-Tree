import java.util.*;

public class GraphAdjMatrix implements Graph{
    private int vertices;
    private int edges;
    private ArrayList<Edge> kruskalMST; // list of all edges in the graph

    class Edge implements Comparable<Edge> // objects used to store weights
    {
        int src, dest, weight;
        public Edge(int s, int d, int w) // source destination and weight
        {
            src = s;
            dest = d;
            weight = w;
        }

        @Override
        public int compareTo(Edge o) { // comparator that compares edge weights
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
            for (Edge e: kruskalMST) // finds the edge for v1 and v2
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
        return 0; // returns 0 if edge does not exist or has been deleted
    }

    @Override
    public int createSpanningTree()
    {
        ArrayList<Edge> copyWeightList = new ArrayList<>(kruskalMST); //copy the list into a temp list
        kruskalMST.clear();
        Collections.sort(copyWeightList); // sort the temp list by their weights

        int disjointSet[] = new int[vertices]; // create a disjoint set array
        createSet(disjointSet);

        int oldNumOfEdges = edges; // copy num of edges and set edges to 0
        edges = 0;
        for(int i = 0; i < oldNumOfEdges; i++)
        {
            Edge edge = copyWeightList.remove(0); // remove from temp list to check if it should be added to the original list
            int setA = find(disjointSet,edge.src);
            int setB = find(disjointSet,edge.dest);

            if (setA != setB){ // checks if both vertices are in the same set
                kruskalMST.add(edge);
                edges++;
                union(disjointSet,setA,setB); // unions the vertices to become a graph representation with a parent vertex
            }
        }

        int totalWeight = 0;
        for (Edge e: kruskalMST) {
            totalWeight+=e.weight; // sums all the edges weight from the kept edges
        }

        return totalWeight;
    }

    public void createSet(int [] parent){
        // makes a disjoint set by setting each vertex element as a parent to itself
        for (int i = 0; i < vertices ; i++) {
            parent[i] = i;
        }
    }

    public int find(int [] parent, int vertex){ // returns the vertex parent from the set
        if(parent[vertex] != vertex)
            return find(parent, parent[vertex]);
        return vertex;
    }

    public void union(int [] parent, int v1, int v2){ // unions vertices together on the disjoint set
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
    public void addEdge(int v1, int v2, int weight) { // creates an edge object to add on the list and increments the edge count
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