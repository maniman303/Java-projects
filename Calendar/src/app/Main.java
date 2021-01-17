package app;

import mech.*;

public class Main
{
    public static DateThing calendar;
    public static void main(String[] SysArg)
    {
        calendar = new DateThing();
        Window window = new Window();
        window.setVisible(true);
    }
}
