package app;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame implements ChangeListener, AdjustmentListener
{
    private JToolBar toolBar;
    private YearPanel yp;
    private static MonthListPanel mlp;
    private static JTabbedPane tp;
    private JButton next;
    private JButton prev;
    private JSpinner sp;
    private JScrollBar sb;
    private int selectedTab;
    public Window()
    {
        super("Calendar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 690));
        initToolBar();
        add(toolBar, BorderLayout.PAGE_END);
        yp = new YearPanel();
        mlp = new MonthListPanel();
        tp = new JTabbedPane();
        tp.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        tp.add("2020", yp);
        tp.add(MonthPanel.months[Main.calendar.getMonth()], mlp);
        tp.addChangeListener(e -> {
            selectedTab = tp.getSelectedIndex();
            if (selectedTab == 0)
            {
                sp.setValue(Main.calendar.getYear());
                sb.setValues(Main.calendar.getYear(), 10, 1, 4000);
            }
            else
            {
                sp.setValue(Main.calendar.getMonth() + 1);
                sb.setValues(Main.calendar.getMonth() + 1, 1, 1, 12);
            }
        });
        add(tp, BorderLayout.CENTER);
        pack();
        setResizable(false);
        selectedTab = 0;
    }
    private void initToolBar()
    {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        next = new JButton("Next");
        prev = new JButton("Prev");
        sp = new JSpinner();
        sb = new JScrollBar(JScrollBar.HORIZONTAL, 2020, 10, 1, 4000);
        sb.addAdjustmentListener(this);
        sp.setPreferredSize(new Dimension(50, 28));
        sp.setMaximumSize(new Dimension(50, 30));
        sp.setValue(Main.calendar.getYear());
        sp.addChangeListener(this);
        next.addActionListener(e -> {
            if (selectedTab == 0)
            {
                Main.calendar.nextYear();
                sp.setValue(Main.calendar.getYear());
            }
            else
            {
                Main.calendar.nextMonth();
                sp.setValue(Main.calendar.getMonth()+1);
            }
        });
        prev.addActionListener(e -> {
            if (selectedTab == 0)
            {
                Main.calendar.prevYear();
                sp.setValue(Main.calendar.getYear());
            }
            else
            {
                Main.calendar.prevMonth();
                sp.setValue(Main.calendar.getMonth()+1);
            }
        });
        toolBar.add(prev);
        toolBar.add(next);
        toolBar.add(sp);
        toolBar.add(sb);
    }
    public void stateChanged(ChangeEvent e)
    {
        if(e.getSource() == sp)
        {
            if (selectedTab == 0)
            {
                Main.calendar.setYear((Integer) sp.getValue());
                mlp.setYear((Integer) sp.getValue());
            }
            else
            {
                int v = (Integer) sp.getValue();
                if (v < 1) sp.setValue(1);
                else if (v > 12) sp.setValue(12);
                else
                {
                    Main.calendar.setMonth(v-1);
                    mlp.setMonth(v-1);
                }
            }
            refreshTabs();
        }
    }
    public void adjustmentValueChanged(AdjustmentEvent e)
    {
        Integer i = e.getValue();
        sp.setValue(i);
    }
    public static void refreshTabs()
    {
        mlp.setMonth(Main.calendar.getMonth());
        String title = Integer.toString(Main.calendar.getYear());
        tp.setTitleAt(0, title);
        title = MonthPanel.months[Main.calendar.getMonth()];
        tp.setTitleAt(1, title);
    }
}
