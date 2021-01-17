package game;

import java.awt.*;
import java.awt.event.*;

public class Window extends Frame implements KeyListener
{
    private Field field;
    public Window()
    {
        int size = Math.min(54 * Main.size, 700);
        setSize(size + 10,size + 42);
        setTitle("AlpaMaze");
        setLayout(new FlowLayout());
        field = new Field();
        add(field);
        addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent evt) { System.exit(0); }});
        addKeyListener(this);
        addComponentListener(new ComponentAdapter()
        {
            public void componentResized(ComponentEvent componentEvent)
            {
                Component c = (Component) componentEvent.getSource();
                int w = c.getWidth() - 10;
                int h = c.getHeight() - 42;
                int m = Math.min(w, h);
                field.setSize(m, m);
            }
        });
        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);
    }
    public void keyPressed(KeyEvent evt)
    {
        //System.out.println("Key pressed");
        int keyCode = evt.getKeyCode();
        if (field.game == 0) switch( keyCode )
        {
            case KeyEvent.VK_UP:
                field.move(1);
                break;
            case KeyEvent.VK_DOWN:
                field.move(2);
                break;
            case KeyEvent.VK_LEFT:
                field.move(3);
                break;
            case KeyEvent.VK_RIGHT :
                field.move(4);
                break;
        }
        else field.move(0);
        field.repaint();
    }
    public void keyReleased(KeyEvent evt) { }
    public void keyTyped(KeyEvent evt) { }
}
