import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
/**
 * BookManager is the class that holds and coordninates all the elements of this program.
 * 
 * The manipulationa and tracking of different projects is managed through this class.
 * 
 * @author Cameron Stanavige
 * @version 5/22/2014
 */
public class BookManager
{
    private BookList bookList;
    private boolean viewingAllBooks;

    private Book currentBook;

    private GUI gui;

    //Constructor
    /**
     * Creates a new ProjectManager with an empty collection of projects and books.
     */
    public BookManager()
    {
        bookList = new BookList();
        viewingAllBooks = false;
        currentBook = null;
        gui = new GUI(this, bookList);
    }

    public void setBookList(BookList newList)
    {
        bookList = newList;
    }

    //Methods for singleBooks
    /**
     * Gets the total word count for the current book being worked on.
     * @return The book's total word count.
     */
    public String getWordCount()
    {
        return currentBook.getTotalCount() + " words";
    }

    /**
     * Gets the total time spent (in minutes) for a single book in the collection.
     * @param title The title of the desired book.
     * @return The total time spent (in minutes).
     */
    public String getTimeSpent()
    {
        return currentBook.getTotalTime()/60 + " hrs " + currentBook.getTotalTime()%60 + " mins";
    }

    /**
     * Gets the number of entry sessons in the current book being worked on.
     * @return The number of entries.
     */
    public String getNumEntries()
    {
        return currentBook.numberOfEntries() + " entries";
    }

    /**
     * Prints out the entries from a single book in the collection.
     * @param title The title of the desired book.
     */
    public void printBookEntries()
    {
        List<Entry> entries = new ArrayList<Entry>();
        entries = currentBook.getEntries();
        int i = 1;
        for(Entry entry : entries) {
            gui.println(i + ") " + entry);
            i++;
        }
    }

    /**
     * Adds a new entry to the current book being worked on.
     * @param count The word count accomplished in the new session.
     * @param time The amount of time (in minutes) spent on new session.
     */
    public boolean addNewEntry(int count, int time)// throws InvalidParameterException
    {
        if(currentBook != null) {
            Entry newEntry = new Entry(count, time);
            gui.println(newEntry + " added to " + currentBook.getTitle() + " project.");
            currentBook.addEntry(newEntry);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Edits the word count of a particular entry within the current book being worked on.
     * @param entry The number of the entry to edit.
     * @param newCount The correct word count.
     */
    public boolean editCurrentBook_entryCount(int entry, int newCount)
    {
        if(currentBook != null) {
            currentBook.editEntryCount(entry, newCount);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Edits the time spent of a particular entry within the current book being worked on.
     * @param entry The number of the entry to edit.
     * @param newTime The correct time (in minutes).
     */
    public boolean editCurrentBook_entryTime(int entry, int newTime)
    {
        if(currentBook != null) {
            currentBook.editEntryTime(entry, newTime);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Edits the date of a particular entry within the current book being worked on.
     * @param entry The number of the entry to edit.
     * @param year The correct year
     * @param month The correct month
     * @param day The correct day
     */
    public boolean editCurrentBook_entryDate(int entry, int year, int month, int day)
    {
        if(currentBook != null) {
            currentBook.editEntryDate(entry, year, month, day);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Sorts the entries by date of the current book projects being worked on.
     */
    public boolean sortCurrentEntries()
    {
        if (currentBook != null) {
            currentBook.sortEntries_byDate();
            return true;
        }
        else {
            return false;   
        }
    }

    /**
     * Gets the average word count of a single book in the collection
     * @param title The title of the desired book.
     * @param days The number of days in the past.
     * @return The average word count.
     */
    public String averageWordCount(int days)
    {
        return currentBook.averageCount(days) + " words/day";
    }

    /**
     * Gets the average time spent of a single book in the collection
     * @param title The title of the desired book.
     * @param days The number of days in the past.
     * @return The average time spent.
     */
    public String averageTimeSpent(int days)
    {
        return currentBook.averageTime(days)/60 + " hours " + currentBook.averageTime(days)%60 + " minutes/day";
    }

    /**
     * private method
     * Gets the average word count of a single book in the collection.
     * @param title The title of the desired book.
     * @param year1 The year of the start date.
     * @param month1 The month of the start date.
     * @param day1 The day of the start date.
     * @param year2 The year of the end date.
     * @param month2 The year of the end date.
     * @param day2 The day of the end date.
     * @return The average word count.
     */
    private long averageWordCount(int year1, int month1, int day1, int year2, int month2, int day2)
    {
        return currentBook.averageCount(year1, month1, day1, year2, month2, day2);
    }

    /**
     * Private method
     * Gets the average time spent of a single book in the collection.
     * @param title The title of the desired book.
     * @param year1 The year of the start date.
     * @param month1 The month of the start date.
     * @param day1 The day of the start date.
     * @param year2 The year of the end date.
     * @param month2 The year of the end date.
     * @param day2 The day of the end date.
     * @return The average time spent.
     */
    private long averageTimeSpent(int year1, int month1, int day1, int year2, int month2, int day2)
    {
        return currentBook.averageCount(year1, month1, day1, year2, month2, day2);
    }

    /**
     * Gets the average word count on the current book or all books between two dates.
     * @param start The start date.
     * @param end The end date.
     * @return The average word count.
     */
    public String processDates_wordCount(String start, String end)
    {
        String one = start;
        String two = end;

        String year1 = one.substring(6, 10);
        int y1 = Integer.parseInt(year1);
        String month1 = one.substring(0, 2);
        int m1 = Integer.parseInt(month1);
        String day1 = one.substring(3, 5);
        int d1 = Integer.parseInt(day1);

        String year2 = two.substring(6, 10);
        int y2 = Integer.parseInt(year2);
        String month2 = two.substring(0, 2);
        int m2 = Integer.parseInt(month2);
        String day2 = two.substring(3, 5);
        int d2 = Integer.parseInt(day2);

        if(currentBook != null) {
            return averageWordCount(y1, m1, d1, y2, m2, d2) + " words/day";
        }
        else {
            return bookList.averageCount_allBooks(y1, m1, d1, y2, m2, d2) + " words/day"; 
        }
    }

    /**
     * Gets the average time spent on the current book or all books between two dates.
     * @param start The start date.
     * @param end The end date.
     * @return The average time spent.
     */
    public String processDates_timeSpent(String start, String end)
    {
        String one = start;
        String two = end;

        String year1 = one.substring(6, 10);
        int y1 = Integer.parseInt(year1);
        String month1 = one.substring(0, 2);
        int m1 = Integer.parseInt(month1);
        String day1 = one.substring(3, 5);
        int d1 = Integer.parseInt(day1);

        String year2 = two.substring(6, 10);
        int y2 = Integer.parseInt(year2);
        String month2 = two.substring(0, 2);
        int m2 = Integer.parseInt(month2);
        String day2 = two.substring(3, 5);
        int d2 = Integer.parseInt(day2);

        if(currentBook != null) {
            return averageTimeSpent(y1, m1, d1, y2, m2, d2)/60 + " hours " + 
            averageTimeSpent(y1, m1, d1, y2, m2, d2)%60 + " minutes/day";
        }
        else {
            return bookList.averageTime_allBooks(y1, m1, d1, y2, m2, d2)/60 + " hours " + 
            bookList.averageTime_allBooks(y1, m1, d1, y2, m2, d2)%60 + " minutes/day"; 
        }
    }

    /**
     * Sets a desired book to the current book project being worked on.
     * @param title The title of the desired book.
     */
    public void setCurrentBook(String title)
    {
        try {
            currentBook = bookList.getBook(title);
            viewingAllBooks = false;
            gui.println("***Currently working in " + currentBook.getTitle() + " project.***");
        }
        catch(InvalidParameterException e) {
            gui.println(e);
        }
    }

    /**
     * Checks to see if a single book projects is currently being worked on.
     * @return Whether or not a current book is being worked on.
     */
    public boolean checkCurrentBook()
    {
        if(currentBook != null) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Emptys the currentBook field.
     */
    public void removeCurrentBook()
    {
        currentBook = null;
    }

    //Methods for allBooks
    /**
     * Checks to see if user is currently utilizing the all book options.
     */
    public boolean checkViewingAllBooks()
    {
        return viewingAllBooks;
    }

    /**
     * Selects that ths user is utilizing options for all books and emptys the current book field.
     */
    public void selectViewingAllBooks()
    {
        removeCurrentBook();
        viewingAllBooks = true;
        gui.println();
        printBooks();
        gui.println("***Currently working in all book projects***");
    }

    /**
     * Prints out all the book projects in the collection
     */
    public void printBooks()
    {
        List<Book> allBooks = bookList.getAllBooks();
        if(bookList.numberOfBooks() > 0) {
            gui.println("***All Available Book Projects***");
            for(Book book : allBooks) {
                gui.println(book);
            }
        }
        else {
            gui.println("***No Available Book Projects***");
        }
    }

    /**
     * Prints out all the entries from all book projects sorted by date.
     */
    public void printAll_bookEntries()
    {
        List<Entry> allEntries = new ArrayList<Entry>();
        allEntries = bookList.getAll_bookEntries();
        int numberOfEntries = 0;
        for(Entry entry : allEntries) {
            gui.println(entry);
            numberOfEntries++;
        }
        gui.println("***Total Number of Entries: " + numberOfEntries);
    }

    /**
     * Prints out all book title of currect book projects.
     */
    public void printBookTitles()
    {
        List<Book> allBooks = bookList.getAllBooks();
        gui.println("*****All Book Project Titles*****");
        for(Book book : allBooks) {
            gui.println("~ " + book.getTitle());
        }
    }

    public static void main(String[] args)
    {
        BookManager pm = new BookManager();
    }
}
