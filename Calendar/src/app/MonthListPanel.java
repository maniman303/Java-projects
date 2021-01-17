package app;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import mech.*;

public class MonthListPanel extends JPanel
{
    private JList jl1;
    private JList jl2;
    private JList jl3;
    private DateThing prevCal;
    private DateThing nextCal;
    public MonthListPanel()
    {
        super();
        prevCal = new DateThing();
        nextCal = new DateThing();
        prevCal.prevMonth();
        nextCal.nextMonth();
        ListCellRenderer renderer = new CustomRenderer();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.PAGE_START;
        jl1 = new JList(prevCal);
        jl1.setBackground(getBackground());
        jl1.setCellRenderer(renderer);
        jl1.setBorder(BorderFactory.createTitledBorder(MonthPanel.months[prevCal.getMonth()]));
        c.gridx = 0;
        add(jl1, c);
        jl2 = new JList(Main.calendar);
        jl2.setBackground(getBackground());
        jl2.setCellRenderer(renderer);
        jl2.setBorder(BorderFactory.createTitledBorder(MonthPanel.months[Main.calendar.getMonth()]));
        c.gridx = 1;
        add(jl2, c);
        jl3 = new JList(nextCal);
        jl3.setBackground(getBackground());
        jl3.setCellRenderer(renderer);
        jl3.setBorder(BorderFactory.createTitledBorder(MonthPanel.months[nextCal.getMonth()]));
        c.gridx = 2;
        add(jl3, c);
    }
    public void setYear(int y)
    {
        prevCal.setYear(y);
        nextCal.setYear(y);
    }
    public void setMonth(int m)
    {
        setYear(Main.calendar.getYear());
        prevCal.setMonth(m);
        prevCal.prevMonth();
        nextCal.setMonth(m);
        nextCal.nextMonth();
        jl1.setBorder(BorderFactory.createTitledBorder(MonthPanel.months[prevCal.getMonth()]));
        jl2.setBorder(BorderFactory.createTitledBorder(MonthPanel.months[Main.calendar.getMonth()]));
        jl3.setBorder(BorderFactory.createTitledBorder(MonthPanel.months[nextCal.getMonth()]));
    }
}

class CustomRenderer implements ListCellRenderer {
    protected static Border noFocusBorder = new EmptyBorder(1, 10, 1, 1);
    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        renderer.setBorder(noFocusBorder);
        String s = (String) value;
        if (s.contains("Sunday")) renderer.setForeground(Color.RED);
        return renderer;
    }
}
