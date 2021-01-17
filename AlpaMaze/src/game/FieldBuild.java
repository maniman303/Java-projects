package game;

import java.util.*;
import mechanic.*;

public class FieldBuild
{
    class Fence
    {
        private boolean _front;
        private boolean _side;
        public Fence()
        {
            _front = false;
            _side = false;
        }
        public void setFront(boolean front) { _front = front; }
        public void setSide(boolean side) { _side = side; }
        public boolean getFront() { return _front; }
        public boolean getSide() { return _side; }
    }
    List<Fence> fence;
    public FieldBuild()
    {
        fence = new ArrayList<>();
    }
    public void init(Graph g)
    {
        for (int i = 1; i <= g.getSize(); i++)
        {
            Fence f = new Fence();
            if (i > g.getLevel())
            {
                if (!g.checkConnection(i, i - g.getLevel()))
                {
                    f.setFront(true);
                }
            }
            else f.setFront(true);
            if (i < g.getSize()) if (!g.checkConnection(i, i + 1)) f.setSide(true);
            fence.add(f);
        }
    }
    public boolean getFront(int i) { return fence.get(i-1).getFront(); }
    public boolean getSide(int i) { return fence.get(i-1).getSide(); }
}
