package mechanic;

import java.util.*;

public class Game
{
    boolean european;
    boolean started;
    ArrayList<Integer> board;
    ArrayList<Integer> euBoard;
    ArrayList<Integer> brBoard;
    public Game(boolean eu)
    {
        european = eu;
        started = false;
        board = new ArrayList<>();
        brBoard = new ArrayList<>(Arrays.asList(1, 2, 6, 7, 8, 9, 13, 14, 36, 37, 41, 42, 43, 44, 48, 49));
        euBoard = new ArrayList<>(Arrays.asList(1, 2, 6, 7, 8, 14, 36, 42, 43, 44, 48, 49));
        for (int i = 1; i <= 49; i++)
        {
            if (i == 25) board.add(0);
            else if (european && euBoard.contains(i)) board.add(-1);
            else if (!european && brBoard.contains(i)) board.add(-1);
            else board.add(1);
        }
    }
    public int get(int p)
    {
        if (p < 1 || p > 49) return -1;
        return board.get(p-1);
    }
    public void set (int p, int v) { if (!(p < 1 || p > 49)) board.set(p-1, v); }
    public boolean getAny(int p)
    {
        int row = (p - 1) / 7;
        int col = (p - 1) % 7;
        if (get(p) == 1)
            if ((get(p-1) == 1 && get(p-2) == 0 && col > 1) ||
                (get(p+1) == 1 && get(p+2) == 0 && col < 5) ||
                (get(p-7) == 1 && get(p-14) == 0 && row > 1) ||
                (get(p+7) == 1 && get(p+14) == 0 && row < 5)) return true;
        return false;
    }
    public boolean getLeft(int p)
    {
        int row = (p - 1) / 7;
        int col = (p - 1) % 7;
        if (row < 0 || row > 6 || col <= 1) return false;
        if (get(p-1) == 1 && get(p-2) == 0) return true;
        return false;
    }
    public boolean getUp(int p)
    {
        int row = (p - 1) / 7;
        if (row <= 1 || row > 6 ) return false;
        if (get(p-7) == 1 && get(p-14) == 0) return true;
        return false;
    }
    public boolean getRight(int p)
    {
        int row = (p - 1) / 7;
        int col = (p - 1) % 7;
        if (row < 0 || row > 6 || col >= 5) return false;
        if (get(p+1) == 1 && get(p+2) == 0) return true;
        return false;
    }
    public boolean getDown(int p)
    {
        int row = (p - 1) / 7;
        if (row < 0 || row >= 5 ) return false;
        if (get(p+7) == 1 && get(p+14) == 0) return true;
        return false;
    }
    public boolean getFinish()
    {
        boolean f = true;
        for (int i = 1; i <= 49; i++)
            if (getAny(i)) f = false;
        return f;
    }
    public boolean moveLeft(int p)
    {
        if (getLeft(p))
        {
            set(p, 0);
            set(p-1, 0);
            set(p-2, 1);
            started = true;
            return true;
        }
        return false;
    }
    public boolean moveRight(int p)
    {
        if (getRight(p))
        {
            set(p, 0);
            set(p+1, 0);
            set(p+2, 1);
            started = true;
            return true;
        }
        return false;
    }
    public boolean moveUp(int p)
    {
        if (getUp(p))
        {
            set(p, 0);
            set(p-7, 0);
            set(p-14, 1);
            started = true;
            return true;
        }
        return false;
    }
    public boolean moveDown(int p)
    {
        if (getDown(p))
        {
            set(p, 0);
            set(p+7, 0);
            set(p+14, 1);
            started = true;
            return true;
        }
        return false;
    }
    public void tryMove(int k, int l)
    {
        int rowK = (k-1) / 7;
        int colK = (k-1) % 7;
        int rowL = (l-1) / 7;
        int colL = (l-1) % 7;
        if (rowK == rowL)
        {
            if (k < l) moveRight(k);
            if (k > l) moveLeft(k);
        }
        if (colK == colL)
        {
            if (k < l) moveDown(k);
            if (k > l) moveUp(k);
        }
    }
    public int getRemaining()
    {
        int w = 0;
        for (int i = 1; i <= 49; i++) if (get(i) == 1) w++;
        return w;
    }
    public boolean getStarted() { return started; }
}
