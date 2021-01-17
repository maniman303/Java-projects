package mech;

import javax.swing.AbstractListModel;
import java.util.*;

public class DateThing extends AbstractListModel
{
    private int year;
    private int month;
    private GregorianCalendar gcal;
    private String[] days;
    private String[] months;
    public DateThing()
    {
        gcal = new GregorianCalendar();
        year = gcal.get(Calendar.YEAR);
        month = gcal.get(Calendar.MONTH);
        days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        months = new String[]{"Jan", "Feb", "Mar", "April", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    }
    public void prevYear()
    {
        year--;
        gcal.set(year, month, 1);
    }
    public void nextYear()
    {
        year++;
        gcal.set(year, month, 1);
    }
    public void prevMonth()
    {
        if (month == 0)
        {
            month = 11;
            year--;
        }
        else month--;
        gcal.set(year, month, 1);
        super.fireContentsChanged(this, 0, getSize() - 1);
    }
    public void nextMonth()
    {
        if (month == 11)
        {
            month = 0;
            year++;
        }
        else month++;
        gcal.set(year, month, 1);
        super.fireContentsChanged(this, 0, getSize() - 1);
    }
    public int getYear() { return year; }
    public void setYear(int y)
    {
        year = y;
        gcal.set(year, month, 1);
        super.fireContentsChanged(this, 0, getSize() - 1);
    }
    public int getMonth() { return month; }
    public void setMonth(int m)
    {
        month = m;
        gcal.set(year, month, 1);
        super.fireContentsChanged(this, 0, getSize() - 1);
    }
    public int getFirstDay(int i)
    {
        gcal.set(year, i, 1);
        int day = gcal.get(Calendar.DAY_OF_WEEK);
        gcal.set(year, month, 1);
        return (day+5)%7;
    }
    public int getMonthSize(int i)
    {
        gcal.set(year, i, 1);
        int s = getSize();
        gcal.set(year, month, 1);
        if (year == 1582 && i == 9) return s - 10;
        return s;
    }
    public int getSize()
    {
        if (year == 1582 && month == 9)
        {
            int t = gcal.getActualMaximum(Calendar.DAY_OF_MONTH) - 10;
            return t;
        }
        return gcal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    public int getDay(int m, int d)
    {
        if (year == 1582 && m == 9 && d > 4) return d + 10;
        else return d;
    }
    public Object getElementAt(int i)
    {
        if (year == 1582 && month == 9 && i > 3) i += 10;
        gcal.set(year, month, i+1);
        int day = gcal.get(Calendar.DAY_OF_WEEK);
        StringBuilder sb = new StringBuilder();
        sb.append(days[(day+5)%7]).append(" ").append(i+1).append(" ").append(months[month]);
        return sb.toString();
    }
}
