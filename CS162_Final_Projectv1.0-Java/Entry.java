import java.util.Calendar;
import java.util.Date;

/**
 * An Entry is one block of time spent on a project that consists of when the entry was made, the amount
 * of work done on the project and the time spent working on the project.
 * 
 * @author Cameron Stanavige
 * @version 5/22/2014
 */
public class Entry implements java.io.Serializable
{
    private Calendar date;
    private int count;
    private int timeSpent;

    /**
     * Creates a new entry with the current date, and input amount of count and time spent.
     * @param count The amount of work done to the project.
     * @param time The amount of time (in minutes) spent on project.
     */
    public Entry(int count, int time)
    {
        date = setDate();
        this.count = count;
        timeSpent = time;
    }

    /**
     * Private method.
     * Sets the new Entry's date to today.
     * @return Today's date.
     */
    private Calendar setDate()
    {
        Calendar today = Calendar.getInstance();
        Date time = new Date();
        time = today.getTime();
        today.setTime(time);
        return today;
    }

    /**
     * Changes the date to the desired date.
     * @param year The year to change the date to.
     * @param month The month to change the date to.
     * @param day The day to change the date to.
     */
    public void setDate(int year, int month, int day)
    {
        Date newDate = new Date();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month - 1);
        date.set(Calendar.DATE, day);
        newDate = date.getTime();
        date.setTime(newDate);
    }

    /**
     * Gets the date.
     * @return The date of the entry.
     */
    public Calendar getDate()
    {
        return date;
    }

    /**
     * Gets a String representation of the date.
     * @return The date of the entry.
     */
    public String stringDate()
    {
        return date.get(Calendar.YEAR) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.DATE);
    }

    /**
     * Gets the count for the amount of work done for this entry.
     * @return The count for this entry.
     */
    public int getCount()
    {
        return count;
    }

    /**
     * Sets the count for the amount of work done for this entry.
     * @param newCount The new count for this entry.
     */
    public void setCount(int newCount)
    {
        count = newCount;
    }

    /**
     * Gets the time spent on the project for this entry.
     * @return The time spent for this entry.
     */
    public int getTimeSpent()
    {
        return timeSpent;
    }

    /**
     * Sets the time spent on the project for this entry.
     * @param time The time spent for this entry.
     */
    public void setTimeSpent(int time)
    {
        timeSpent = time;
    }

    /**
     * The toString for an Entry
     * @return The toString for an Entry.
     */
    public String toString()
    {
        return "Date: " + stringDate() + "  Count: " + count + "    Time Spent: " + timeSpent + " minutes";
    }
}
