package app;

import javax.swing.*;
import java.awt.*;

class YearPanel extends JPanel
{
    public YearPanel()
    {
        super();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        MonthPanel mp;
        c.weightx = 1;
        c.weighty = 1;
        int count = 0;
        for (int i = 0; i < 3; i++)
            for (int k = 0; k < 4; k++)
            {
                mp = new MonthPanel(count);
                c.gridx = k;
                c.gridy = i;
                add(mp, c);
                count++;
            }
    }
}
