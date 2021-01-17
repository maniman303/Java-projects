package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Panel extends JPanel implements MouseListener
{
    private JPopupMenu popupmenu;
    private JMenuItem left;
    private JMenuItem up;
    private JMenuItem down;
    private JMenuItem right;
    private int focus;
    private int rightFocus;
    private boolean over;
    public Panel()
    {
        super();
        focus = rightFocus = 0;
        over = false;
        popupmenu = new JPopupMenu("Move Menu");
        up = new JMenuItem("Jump up");
        left = new JMenuItem("Jump left");
        right = new JMenuItem("Jump right");
        down = new JMenuItem("Jump down");
        popupmenu.add(left);
        popupmenu.add(up);
        popupmenu.add(right);
        popupmenu.add(down);
        left.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Main.game.moveLeft(rightFocus);
                rightFocus = 0;
                checkMap();
            }
        });
        up.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Main.game.moveUp(rightFocus);
                rightFocus = 0;
                checkMap();
            }
        });
        right.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Main.game.moveRight(rightFocus);
                rightFocus = 0;
                checkMap();
            }
        });
        down.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Main.game.moveDown(rightFocus);
                rightFocus = 0;
                checkMap();
            }
        });
        addMouseListener(this);
        setSize(400, 400);
    }
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        float thickness = 3;
        int row, col, stat, posX, posY;
        int width = getWidth();
        int height = getHeight();
        g2.setColor(getBackground());
        g2.fillRect(0, 0, width, height);
        int sizeBoard = Math.min(width, height);
        int size = sizeBoard / 7;
        int gapX = Math.max((width - height) / 2, 0);
        int gapY = Math.max((height - width) / 2, 0);
        g2.setColor(Window.boardColor);
        g2.fillRoundRect(gapX, gapY, sizeBoard, sizeBoard, 40, 40);
        g2.setStroke(new BasicStroke(thickness));
        if (Main.game != null)
        {
            for (int i = 1; i <= 49; i++)
            {
                row = (i-1) / 7;
                col = (i-1) % 7;
                posX = gapX + col * size + size / 4;
                posY = gapY + row * size + size / 4;
                stat = Main.game.get(i);
                if (i == focus)
                {
                    g2.setColor(Color.YELLOW);
                    g2.fillRoundRect(gapX + col * size + size / 8, gapY + row * size + size / 8, 3 * size / 4, 3 * size / 4, size / 2, size / 2);
                }
                if (stat == 0)
                {
                    g2.setColor(Color.GRAY);
                    g2.fillOval(posX, posY, size / 2, size / 2);
                }
                if (stat == 1)
                {
                    g2.setColor(Window.pawnColor);
                    if (Window.fillPawn) g2.fillOval(posX, posY, size / 2, size / 2);
                    g2.drawOval(posX, posY, size / 2, size / 2);
                }
            }
        }
    }
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e)
    {
        if (!over)
        {
            if (e.getButton() == 1) mouseClickLeft(e.getPoint());
            if (e.getButton() == 3) mouseClickRight(e.getPoint());
        }
    }
    public void mouseClickLeft(Point p)
    {
        int i = getIndex(p);
        int stat = Main.game.get(i);
        if (stat >= 0)
        {
            if (focus <= 0 && stat == 1) focus = i;
            else if (stat == 0)
            {
                Main.game.tryMove(focus, i);
                focus = 0;
            }
            else focus = 0;
        }
        else focus = 0;
        checkMap();
        if (over) Window.label.setText("Game over, remaining: " + Integer.toString(Main.game.getRemaining()));
    }
    public void mouseClickRight(Point p)
    {
        int i = getIndex(p);
        int stat = Main.game.get(i);
        if (i >= 1 && i <= 49 && stat == 1)
        {
            rightFocus = i;
            left.setEnabled(false);
            up.setEnabled(false);
            right.setEnabled(false);
            down.setEnabled(false);
            if (Main.game.getLeft(i)) left.setEnabled(true);
            if (Main.game.getUp(i)) up.setEnabled(true);
            if (Main.game.getRight(i)) right.setEnabled(true);
            if (Main.game.getDown(i)) down.setEnabled(true);
            popupmenu.show(this , (int)p.getX(), (int)p.getY());
        }
    }
    public void checkMap()
    {
        this.revalidate();
        this.repaint();
        over = Main.game.getFinish();
    }
    public int getFocus()
    {
        return focus;
    }
    public void jumpFocus(int d)
    {
        if (focus >= 1 && focus <= 49)
        {
            switch (d)
            {
                case 1:
                    if (Main.game.moveLeft(focus)) focus -= 2;
                    break;
                case 2:
                    if (Main.game.moveUp(focus)) focus -= 14;
                    break;
                case 3:
                    if (Main.game.moveRight(focus)) focus += 2;
                    break;
                case 4:
                    if (Main.game.moveDown(focus)) focus += 14;
                    break;
            }
            checkMap();
        }
    }
    public void resetFocus()
    {
        for (int i = 1; i <= 49; i++)
        {
            if (Main.game.get(i) == 1)
            {
                focus = i;
                i = 50;
            }
        }
        checkMap();
    }
    public void moveFocus(int d) // 1 left, 2 up, 3 right, 4 down
    {
        if (focus >= 1 && focus <= 49)
        {
            int newFocus = focus;
            switch (d)
            {
                case 1:
                    newFocus -= 1;
                    break;
                case 2:
                    newFocus -= 7;
                    break;
                case 3:
                    newFocus += 1;
                    break;
                case 4:
                    newFocus += 7;
                    break;
            }
            if (newFocus <= 0) newFocus += 49;
            if (newFocus > 49) newFocus -= 49;
            while (Main.game.get(newFocus) != 1)
            {
                switch (d)
                {
                    case 1:
                        newFocus -= 1;
                        break;
                    case 2:
                        newFocus -= 7;
                        break;
                    case 3:
                        newFocus += 1;
                        break;
                    case 4:
                        newFocus += 7;
                        break;
                }
                if (newFocus <= 0) newFocus += 49;
                if (newFocus > 49) newFocus -= 49;
            }
            focus = newFocus;
            checkMap();
        }
        else resetFocus();
    }
    private int getIndex(Point p)
    {
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height) / 7;
        int gapX = Math.max((width - height) / 2, 0);
        int gapY = Math.max((height - width) / 2, 0);
        if ((int)p.getX() <= gapX || (int)p.getX() >= gapX + size * 7) return -1;
        if ((int)p.getY() <= gapY || (int)p.getY() >= gapY + size * 7) return -1;
        int bX = ((int)p.getX() - gapX) / size + 1;
        int bY = ((int)p.getY() - gapY) / size;
        int i = bX + 7 * bY;
        return i;
    }
}
