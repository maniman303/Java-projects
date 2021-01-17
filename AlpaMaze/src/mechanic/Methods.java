package mechanic;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Methods
{
    public static Graph generateBoard(int n)
    {
        Graph graph = new Graph(n);
        for (int i = 1; i <= n*n; i++)
        {
            if (i%n != 0) graph.addConnection(i, i+1);
            if (i+n <= n*n) graph.addConnection(i, i+n);
        }
        return graph;
    }
    public static Pair randomConnection(List<Integer> li, Graph g)
    {
        List<Pair> lp = new ArrayList<>();
        List<Integer> con;
        int t, l;
        for (int i = 0; i < li.size(); i++)
        {
            t = li.get(i);
            con = g.getConnections(t);
            for (int k = 0; k < con.size(); k++)
            {
                l = con.get(k) + 1;
                if (!li.contains(l)) lp.add(new Pair(t, con.get(k) + 1));
            }
        }
        int randomNum = ThreadLocalRandom.current().nextInt(0, lp.size());
        return lp.get(randomNum);
    }
    public static Graph generateMaze(int n)
    {
        Graph g = generateBoard(n);
        Graph v = new Graph(n);
        Pair p = new Pair(0,0);
        List<Integer> vList = new ArrayList<>();
        vList.add(1);
        int randomNum = ThreadLocalRandom.current().nextInt(0, 2);
        if (randomNum == 0) g.removeConnection(1, 2);
        else g.removeConnection(1, n+1);
        while (vList.size() < v.getSize())
        {
            p = randomConnection(vList, g);
            vList.add(p.getValue());
            v.addConnection(p.getKey(), p.getValue());
            g.removeConnection(p.getKey(), p.getValue());
        }
        //System.out.println("vList: " + vList);
        return v;
    }
}

class Pair
{
    private int Key;
    private int Value;
    public Pair(int k, int v)
    {
        Key = k;
        Value = v;
    }
    public int getKey() { return Key; }
    public int getValue() { return Value; }
}
