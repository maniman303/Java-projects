package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MonthPanel extends JPanel
{
    private int month;
    static String[] months = new String[]{"Jan", "Feb", "Mar", "April", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public MonthPanel(int m)
    {
        super();
        month = m;
        setPreferredSize(new Dimension(200, 190));
        setBorder(BorderFactory.createTitledBorder(months[month]));
        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                Main.calendar.setMonth(month);
                Window.refreshTabs();
            }
            public void mouseReleased(MouseEvent e) { }
        });
    }
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Dialog", Font.BOLD, 12));
        int day = 0, count = 0, minDay = Main.calendar.getFirstDay(month), maxDay = Main.calendar.getMonthSize(month);
        for (int i = 0; i < 6; i++)
            for (int k = 0; k < 7; k++)
            {
                count++;
                if (count > minDay && day < maxDay)
                {
                    day++;
                    if (k == 6) g2.setColor(Color.RED);
                    else g2.setColor(Color.BLACK);
                    g2.drawString(Integer.toString(Main.calendar.getDay(month, day)), k * 28 + 11, i * 28 + 32);
                }
            }
    }
}
