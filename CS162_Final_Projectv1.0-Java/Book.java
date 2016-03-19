import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Calendar;
import java.util.Date;

/**
 * A Book project is an abstract object that contains a total word count, the total time (in minutes) spent
 * on the project, and a list of the entries for each block of time spent on the project.
 * 
 * It can also have additional elements added to it such as editor, publisher, date published,
 * number in a series, etc.
 * 
 * More subclasses for this class can be added to adapt to different genre of writers.
 * 
 * @author Cameron Stanavige
 * @version 5/22/2014
 */
public abstract class Book implements java.io.Serializable
{
    //Fields
    private long total_word_count;
    private long total_time_spent;
    private List<Entry> entries;

    //Constructors
    /**
     * Default contstructor that creates a new Book project with all empty fields.
     */
    public Book()
    {
        total_word_count = 0;
        total_time_spent = 0;
        entries = new ArrayList<Entry>();
    }

    /**
     * Non-default constructor that creates a new book Project with the input amount of total
     * word cound and total time already spent on the project.
     * @param totalCount The total word count already put into the project.
     * @param totalTime The total amount of time (in minutes) already spent on the project
     */
    public Book(long totalCount, long totalTime)
    {
        total_word_count = totalCount;
        total_time_spent = totalTime;
        entries = new ArrayList<Entry>();
    }

    //Methods
    //WordCount Methods
    /**
     * Gets the total count of words in this book.
     * @return The total count for this book.
     */
    public long getTotalCount()
    {
        return total_word_count;
    }

    /**
     * Sets the total count of words in this book.
     * @param newTotal The total count in this book.
     */
    public void setTotalCount(long newTotal)
    {
        total_word_count = newTotal;
    }

    /**
     * Adds more words to the total word count.
     * @param count The amount of words to add to the total.
     */
    public void incrementTotalCount(int count)
    {
        total_word_count += count;
    }

    /**
     * Removes words from the total word count.
     * @param count The amount of words to remove from the total.
     */
    public void decrementTotalCount(int count)
    {
        total_word_count -= count;
    }

    //TotalTime Methods
    /**
     * Gets the total amount of time (in minutes) spent on this project.
     * @return The total time (in minutes) spent on this project.
     */
    public long getTotalTime()
    {
        return total_time_spent;
    }

    /**
     * Sets the total amount of time (in minutes) spent on this project.
     * @param newTotal The total amount of time (in minutes) spent on this project.
     */
    public void setTotalTime(long newTotal)
    {
        total_time_spent = newTotal;
    }

    /**
     * Adds time to the total time spent on this project.
     * @param time The time (in minutes) to add to the total.
     */
    public void incrementTotalTime(int time)
    {
        total_time_spent += time;
    }

    /**
     * Removes time from the total time spent on this project.
     * @param time The time (in minutes) to remove from the total.
     */
    public void decrementTotalTime(int time)
    {
        total_time_spent -= time;
    }

    //Collection Methods
    /**
     * Gets the number of Entry sessions made on this project.
     * @return The number of entries.
     */
    public int numberOfEntries()
    {
        return entries.size();
    }

    /**
     * Adds an entry to the collection of entries and adds its time and count to the totals.
     * @param entry The Entry to add.
     */
    public void addEntry(Entry entry)
    {
        incrementTotalCount(entry.getCount());
        incrementTotalTime(entry.getTimeSpent());
        entries.add(entry);
    }

    /**
     * Gets the collections of entries.
     * @return The collection of entries.
     */
    public List<Entry> getEntries()
    {
        return entries;
    }

    /**
     * Change an incorrect word count within a specific Entry.
     * @param entry The number of the entry to edit.
     * @param newCount The correct word count.
     */
    public void editEntryCount(int entry, int newCount)
    {
        Entry edit = entries.get(entry - 1);
        decrementTotalCount(edit.getCount());
        incrementTotalCount(newCount);
        edit.setCount(newCount);
    }

    /**
     * Change an incorrect time spent within a specific Entry.
     * @param entry The number of the entry to edit.
     * @param newTime The correct time (in minutes).
     */
    public void editEntryTime(int entry, int newTime)
    {
        Entry edit = entries.get(entry - 1);
        decrementTotalTime(edit.getTimeSpent());
        incrementTotalTime(newTime);
        edit.setTimeSpent(newTime);
    }

    /**
     * Change an incorrect date within a specific Entry.
     * @param entry The number of the entry to edit.
     * @param year The correct year.
     * @param month The correct month.
     * @param day The correct day.
     */
    public void editEntryDate(int entry, int year, int month, int day)
    {
        Entry edit = entries.get(entry - 1);
        edit.setDate(year, month, day);
    }

    /**
     * Sorts the collection of entries sequentially by date.
     */
    public void sortEntries_byDate()
    {
        Entry entry;
        Entry temp;
        boolean doAgain = true;
        while(doAgain) {
            doAgain = false;
            for(int i = 0; i < entries.size() - 1; i++) {
                entry = entries.get(i);
                Calendar date = entry.getDate();
                Entry compare = entries.get(i+1);
                Calendar date2 = compare.getDate();
                if(date2.before(date)) {
                    temp = entry;
                    entries.set(i, compare);
                    entries.set((i+1), temp);
                    doAgain = true;
                }
            }
        }
    }

    //Averaging Methods
    /**
     * Gets the average word count between today and the input number of days in the past.
     * @param days The number of days in the past.
     * @return The average word count.
     */
    public long averageCount(int days)
    {
        Calendar period = getPastDate(days);
        int arrayNumber = getEntry_withDate(period);
        long totalCount = countAdder(arrayNumber);
        return totalCount/days;
    }
    
    /**
     * Gets the average time spent between today and the input number of days in the past.
     * @param days The number of days in the past.
     * @return The average time spent.
     */
    public long averageTime(int days)
    {
        Calendar period = getPastDate(days);
        int arrayNumber = getEntry_withDate(period);
        long totalTime = timeAdder(arrayNumber);
        return totalTime/days;
    }

    /**
     * Gets the average word count between two set days.
     * @param year1 The year of the start date.
     * @param month1 The month of the start date.
     * @param day1 The day of the start date.
     * @param year2 The year of the end date.
     * @param month2 The year of the end date.
     * @param day2 The day of the end date.
     * @return The average word count.
     */
    public long averageCount(int year1, int month1, int day1, int year2, int month2, int day2)
    {
        Calendar startDate = getCalendar(year1, month1, day1);
        Calendar endDate = getCalendar(year2, month2, day2);
        int startIndex = getEntry_withDate(startDate);
        int endIndex = getEntry_withDate2(endDate);
        long totalCount = countAdder(startIndex, endIndex);
        int days = numberOfDays(startDate, endDate);
        return totalCount/days;
    }
    
     /**
     * Gets the average time spent between two set days.
     * @param year1 The year of the start date.
     * @param month1 The month of the start date.
     * @param day1 The day of the start date.
     * @param year2 The year of the end date.
     * @param month2 The year of the end date.
     * @param day2 The day of the end date.
     * @return The average time spent.
     */
    public long averageTime(int year1, int month1, int day1, int year2, int month2, int day2)
    {
        Calendar startDate = getCalendar(year1, month1, day1);
        Calendar endDate = getCalendar(year2, month2, day2);
        int startIndex = getEntry_withDate(startDate);
        int endIndex = getEntry_withDate2(endDate);
        long totalTime = timeAdder(startIndex, endIndex);
        int days = numberOfDays(startDate, endDate);
        return totalTime/days;
    }

    /**
     * Private method
     * Gets the date of the day that is the input number of days before today.
     * @param days The number of days in the past.
     * @return The date.
     */
    private Calendar getPastDate(int days)
    {
        Date newDate = new Date();
        Calendar today = Calendar.getInstance();
        Calendar period = Calendar.getInstance();
        period.add(Calendar.DATE, - days);
        newDate = period.getTime();
        period.setTime(newDate);
        return period;
    }

    /**
     * Gets the Calendar date and time of the input day.
     * @param year The year to set the date to.
     * @param month The month to set the date to.
     * @param day The day to set the date to.
     * @return The Calendar date.
     */
    private Calendar getCalendar(int year, int month, int day)
    {
        Calendar calDate = Calendar.getInstance();
        Date newDate = new Date();
        calDate.set(Calendar.YEAR, year);
        calDate.set(Calendar.MONTH, month - 1);
        calDate.set(Calendar.DATE, day);
        newDate = calDate.getTime();
        calDate.setTime(newDate);
        return calDate;
    }

    /**
     * Private method
     * Determines the number of days between two dates.
     * @param day1 The earlier date.
     * @param day2 The later date.
     * @return The number of days.
     */
    private int numberOfDays(Calendar day1, Calendar day2)
    {
        long diff = day2.getTimeInMillis() - day1.getTimeInMillis();
        diff /= (1000 * 60 * 60 * 24);
        int days = (int) diff;
        return days;
    }

    /**
     * Private method
     * Starts at beginning of list.
     * Gets the index number from the list entries of the entry with the input date or the entry
     * with the NEXT available date.
     * @param period The date to find.
     * @return The index number.
     */
    private int getEntry_withDate(Calendar period)
    {
        boolean found = false;
        int i = 0;
        while ((i < entries.size()) && (found == false)) {
            Entry comparison = entries.get(i);
            Calendar date = comparison.getDate();
            boolean same = isSameDay(date, period);
            if(same == true || date.after(period)){
                found = true;
            }
            else {
                i++;
            }
        }
        return i;
    }

    /**
     * Private method
     * Starts at end of list.
     * Gets the index number from the list of entries of the entry with the input date or the entry
     * with the first PREVIOUS date.
     * @param period The date to find.
     * @return The index number.
     */
    private int getEntry_withDate2(Calendar period)
    {
        boolean found = false;
        int i = entries.size() - 1;
        while ((i > 0) && (found == false)) {
            Entry comparison = entries.get(i);
            Calendar date = comparison.getDate();
            boolean same = isSameDay(date, period);
            if(same == true || date.before(period)){
                found = true;
            }
            else {
                i--;
            }
        }
        return i;
    }

    /**
     * Private method
     * Checks to see if two days are the same day.
     * @param day1 The first day to compare.
     * @param day2 The second day to compare.
     * @return If they are the same day.
     */
    private boolean isSameDay(Calendar day1, Calendar day2)
    {
        if(day1 == null || day2 == null) {
            return false;
        }
        return (day1.get(Calendar.YEAR) == day2.get(Calendar.YEAR)
            && day1.get(Calendar.MONTH) == day2.get(Calendar.MONTH)
            && day1.get(Calendar.DATE) == day2.get(Calendar.DATE));
    }

    /**
     * Private method
     * Adds the word count of each entry starting at the input index to the end of the list.
     * @param arrayNumber The index to start at.
     * @return The total word count.
     */
    private long countAdder(int arrayNumber)
    {
        long count = 0;        
        for(int i = arrayNumber; i < entries.size(); i++) {
            Entry entry = entries.get(i);
            count += entry.getCount();
        }
        return count;
    }
    
    /**
     * Private method
     * Adds the time spent of each entry starting at the input index to the end of the list.
     * @param arrayNumber The index to start at.
     * @return The total time spent.
     */
    private long timeAdder(int arrayNumber)
    {
        long time = 0;        
        for(int i = arrayNumber; i < entries.size(); i++) {
            Entry entry = entries.get(i);
            time += entry.getTimeSpent();
        }
        return time;
    }

    /**
     * Private method
     * Adds the word count of each entry between two index points in the list.
     * @param startIndex The index of the array to start at.
     * @param endIndex The index of the array to end at.
     * @return The total word count.
     */
    private long countAdder(int startIndex, int endIndex)
    {
        long count = 0;
        for(int i = startIndex; i <= endIndex; i++) {
            Entry entry = entries.get(i);
            count += entry.getCount();
        }
        return count;
    }
    
    /**
     * Private method
     * Adds the time spent on each entry between two index points in the list.
     * @param startIndex The index of the array to start at.
     * @param endIndex The index of the array to end at.
     * @return The total time spent.
     */
    private long timeAdder(int startIndex, int endIndex)
    {
        long time = 0;
        for(int i = startIndex; i <= endIndex; i++) {
            Entry entry = entries.get(i);
            time += entry.getTimeSpent();
        }
        return time;
    }

    //Abstract Methods
    /**
     * Gets the title of the Book
     * @return The title of the book.
     */
    public abstract String getTitle();

    /**
     * Gets the genre of the Book
     * @return The genre of the book
     */
    public abstract String getGenre();

    //toString
    /**
     * The toString for Book.
     * @return The toString for this class.
     */
    public String toString()
    {
        return "Word Count: " + total_word_count + "\nTime Spent on Project: " + total_time_spent/60 + " hours " + 
        total_time_spent%60 + " minutes\nSession Entries: " + numberOfEntries() + "\n";
    }
}
