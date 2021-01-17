package game;
import mechanic.*;

public class Main
{
    public static Graph maze;
    public static int size = 10;
    public static void main(String[] Args)
    {
        maze = Methods.generateMaze(size);
        Window window = new Window();
    }
}
