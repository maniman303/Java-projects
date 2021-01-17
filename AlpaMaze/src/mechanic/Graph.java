package mechanic;

import java.util.*;

public class Graph
{
    private ArrayList<ArrayList<Integer> > graph;
    private int size;
    private int level;
    public Graph(int n)
    {
        graph = new ArrayList<ArrayList<Integer> >();
        size = 0;
        level = n;
        for (int i = 1; i <= n*n; i++) addVert();
    }
    public void addVert()
    {
        ArrayList<Integer> t = new ArrayList<>();
        graph.add(t);
        size++;
    }
    public void addConnection(int n, int m)
    {
        if (graph.get(n-1) != null && graph.get(m-1) != null) if (!(graph.get(n-1).contains(m-1) || graph.get(m-1).contains(n-1)))
        {
            graph.get(n-1).add(m-1);
            graph.get(m-1).add(n-1);
        }
    }
    public void removeConnection(int n, int m)
    {
        if (graph.get(n-1) != null && graph.get(m-1) != null)
        {
            graph.get(n-1).remove((Object) (m-1));
            graph.get(m-1).remove((Object) (n-1));
        }
    }
    public boolean checkConnection(int n, int m)
    {
        if (graph.get(n-1) == null || graph.get(m-1) == null) return false;
        return (graph.get(n-1).contains(m-1) && graph.get(m-1).contains(n-1));
    }
    public ArrayList<Integer> getConnections(int n) { return graph.get(n-1); }
    public void print()
    {
        for (int i = 0; i < graph.size(); i++)
        {
            System.out.println(i + ": " + graph.get(i));
        }
    }
    public int getSize() { return size; }
    public int getLevel() { return level; }
}