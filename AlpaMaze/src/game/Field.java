package game;

import mechanic.Methods;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class Field extends Canvas
{
    private int bufferWidth;
    private int bufferHeight;
    private Image bufferImage;
    private Graphics bufferGraphics;
    private FieldBuild fb;
    private Image grass;
    private Image fenceFront;
    private Image fenceSide;
    private Image fencePicket;
    private Image hero;
    private Image black;
    private Image over;
    private Image end;
    int heroLoc;
    int game;
    public Field()
    {
        setFocusable(false);
        setBackground (Color.GRAY);
        setSize(54 * Main.size, 54 * Main.size);
        game = 0;
        fb = new FieldBuild();
        fb.init(Main.maze);
        try
        {
            grass = ImageIO.read(new File("grass.png"));
            fenceFront = ImageIO.read(new File("FenceFront.png"));
            fenceSide = ImageIO.read(new File("FenceSide.png"));
            fencePicket = ImageIO.read(new File("FencePicket.png"));
            hero = ImageIO.read(new File("hero.png"));
            black = ImageIO.read(new File("black.png"));
            over = ImageIO.read(new File("over.png"));
            end = ImageIO.read(new File("end.png"));
        }
        catch (Exception e)
        {
            System.out.println("No png");
        }
        heroLoc = ThreadLocalRandom.current().nextInt(2, Main.size * Main.size + 1);
    }
    public void update(Graphics g) { paint(g); }
    public void paint(Graphics g)
    {
        if(bufferImage == null || bufferGraphics == null) resetBuffer();
        if(bufferGraphics != null)
        {
            bufferGraphics.clearRect(0,0, bufferWidth, bufferHeight);
            paintBuffer(bufferGraphics);
            g.drawImage(bufferImage,0,0, getWidth(), getHeight(), 0, 0, bufferWidth, bufferHeight, this);
        }
    }
    public void paintBuffer (Graphics g)
    {
        int row, col;
        if (game != 2) for (int i = 1; i <= Main.size * Main.size; i++)
        {
            row = (i - 1) / Main.size;
            col = (i - 1) % Main.size;
            g.drawImage(grass, 54 * col, 54 * row, null);
            if (fb.getFront(i)) g.drawImage(fenceFront, 54 * col, 54 * row, null);
            if (fb.getSide(i)) g.drawImage(fenceSide, 54 * col, 54 * row, null);
            if (!(fb.getFront(i) || fb.getSide(i))) g.drawImage(fencePicket, 54 * col, 54 * row, null);
            if (i == 1) g.drawImage(end, 54 * col, 54 * row, null);
            if (i == heroLoc) g.drawImage(hero, 54 * col, 54 * row, null);
            if (game == 1) g.drawImage(black, 54 * col, 54 * row, null);
        }
        if (game == 1)
        {
            int n = Main.size;
            if (n % 2 == 0) g.drawImage(over, (n - 4) / 2 * 54, 54 * n / 2 - 27, null);
            else g.drawImage(over, (n - 4) / 2 * 54 + 27, 54 * n / 2, null);
        }
        //System.out.println("Drawing");
    }
    private void resetBuffer(){
        bufferWidth = 54 * Main.size;
        bufferHeight = 54 * Main.size;
        if (bufferGraphics != null)
        {
            bufferGraphics.dispose();
            bufferGraphics = null;
        }
        if(bufferImage != null)
        {
            bufferImage.flush();
            bufferImage = null;
        }
        System.gc();
        bufferImage = createImage(bufferWidth, bufferHeight);
        bufferGraphics = bufferImage.getGraphics();
    }
    public void move(int m)
    {
        int n = Main.maze.getLevel();
        switch (m)
        {
            case 0: //reset
                game = 2;
                heroLoc = ThreadLocalRandom.current().nextInt(2, Main.size * Main.size + 1);
                Main.maze = Methods.generateMaze(Main.size);
                fb = new FieldBuild();
                fb.init(Main.maze);
                game = 0;
                break;
            case 1: //up
                if (heroLoc > n)
                    if (Main.maze.checkConnection(heroLoc, heroLoc - n)) heroLoc -= n;
                break;
            case 2: //down
                if (heroLoc <= (n-1)*n)
                    if (Main.maze.checkConnection(heroLoc, heroLoc + n)) heroLoc += n;
                break;
            case 3: //left
                if (heroLoc % n != 1)
                    if (Main.maze.checkConnection(heroLoc, heroLoc - 1)) heroLoc -= 1;
                break;
            case 4: //right
                if (heroLoc % n != 0)
                    if (Main.maze.checkConnection(heroLoc, heroLoc + 1)) heroLoc += 1;
                break;
        }
        if (game == 0 && heroLoc == 1) game = 1;
    }
}
