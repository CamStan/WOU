import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * BookList is the collection of different book projects and the manipulation thereof.
 * 
 * @author Cameron Stanavige 
 * @version 6/3/2014
 */
public class BookList implements java.io.Serializable
{
    private ArrayList<Book> books;

    /**
     * Creates a new empty collection of books.
     */
    public BookList()
    {
        books = new ArrayList<Book>();
    }

    /**
     * Creates a new Book project and then adds it to the collections.
     * @param title The title of the book.
     */
    public void newBookProject(String title, String genre) throws InvalidParameterException
    {
        if(title.equals("")) {
            throw new InvalidParameterException("Title cannot be left blank");
        }
        else if(genre.equals("fantasy")) {
            Book newBook = new Fantasy(title);
            addProject(newBook);
        }
        else if(genre.equals("na")) {
            Book newBook = new NA(title);
            addProject(newBook);
        }
        else if(genre.equals("nf")) {
            Book newBook = new NF(title);
            addProject(newBook);
        }
        else {
            Book newBook = new YAF(title);
            addProject(newBook);
        }
    }

    /**
     * Add a project to the appropiate collection.
     * @param project The project to add.
     */
    public void addProject(Book project)
    {
        if(project == null) {
            throw new NullPointerException("Attempted addition was an empty project.");
        }
        else if(!(project instanceof Book)) {
            throw new IllegalArgumentException("Attempted addition was an invalid project.");
        }
        else {
            books.add( project);
        }
    }

    /**
     * Gets the number of book projects in the collection.
     * @return The number of books.
     */
    public int numberOfBooks()
    {
        return books.size();
    }

    /**
     * Gets a specific Book project from the collection
     * @param title The title of the desired book.
     * @return The desired book.
     */
    public Book getBook(String title) throws InvalidParameterException
    {
        boolean found = false;
        Book match = null;
        int i = 0;
        while((i < books.size()) && (found == false)) {
            Book book = books.get(i);
            String compare = book.getTitle().trim().toLowerCase();
            if(compare.equals(title.trim().toLowerCase())) {
                match = book;
                found = true;
            }
            i++;
        }
        if(found == false) {
            throw new InvalidParameterException("Desired book title not found.");
        }
        return match;
    }

    /**
     * Gets the total word count of all book projects.
     * @return The total word count.
     */
    public String getTotal_wordCount_allBooks()
    {
        long totalCount = 0;
        for(Book book : books) {
            totalCount += book.getTotalCount();
        }
        return totalCount + " words";
    }

    /**
     * Gets the total time (in minutes) spent on all book projects.
     * @return The total time.
     */
    public String getTotal_timeCount_allBooks()
    {
        long totalTime = 0;
        for(Book book : books) {
            totalTime += book.getTotalTime();
        }
        return totalTime/60 + " hrs " + totalTime%60 + " mins";
    }

    /**
     * Gets the total number of entries made on all book projects.
     * @return The total number of entries.
     */
    public String getTotal_entries_allBooks()
    {
        long totalEntries = 0;
        for(Book book : books) {
            totalEntries += book.numberOfEntries();
        }
        return totalEntries + " entries";
    }

    /**
     * Private method
     * Gets all the entries from all book projects, puts them in one list and sorts them by date.
     * @return The list of all sorted entries.
     */
    public List<Entry> getAll_bookEntries()
    {
        List<Entry> allEntries = new ArrayList<Entry>();
        for(Book book : books) {
            List<Entry> bookEntries = new ArrayList<Entry>();
            bookEntries = book.getEntries();
            allEntries.addAll(bookEntries);
        }
        allEntries = sortEntries(allEntries);
        return allEntries;
    }

    /**
     * Private method
     * Sorts all the entries in the given list by date.
     * @param entries The list of entries to sort.
     * @return The list of sorted entries by date.
     */
    private List<Entry> sortEntries(List<Entry> entries)
    {
        List<Entry> sortedEntries = new ArrayList<Entry>();
        sortedEntries = entries;

        Entry entry;
        Entry temp;
        boolean doAgain = true;
        while(doAgain) {
            doAgain = false;
            for(int i = 0; i < sortedEntries.size() - 1; i++) {
                entry = sortedEntries.get(i);
                Calendar date = entry.getDate();
                Entry compare = sortedEntries.get(i+1);
                Calendar date2 = compare.getDate();
                if(date2.before(date)) {
                    temp = entry;
                    sortedEntries.set(i, compare);
                    sortedEntries.set((i+1), temp);
                    doAgain = true;
                }
            }
        }
        return sortedEntries;
    }

    /**
     * Gets the total average word count of all book projects combined between today and the input
     * number of days in the past.
     * @param days The number of days in the past.
     * @return The average word count.
     */
    public String averageCount_allBooks(int days)
    {
        long totalAverage = 0;
        for(Book book : books) {
            totalAverage += book.averageCount(days);
        }
        return totalAverage + " words/day";
    }

    /**
     * Gets the collection of all book projects.
     * @return The collections of book projects.
     */
    public List<Book> getAllBooks()
    {
        return books;
    }

    /**
     * Private method
     * Gets the total average word count of all book projects combined between two days.
     * @param year1 The year of the start date.
     * @param month1 The month of the start date.
     * @param day1 The day of the start date.
     * @param year2 The year of the end date.
     * @param month2 The year of the end date.
     * @param day2 The day of the end date.
     * @return The average word count.
     */
    public long averageCount_allBooks(int year1, int month1, int day1, int year2, int month2, int day2)
    {
        long totalAverage = 0;
        for(Book book : books) {
            totalAverage += book.averageCount(year1, month1, day1, year2, month2, day2);
        }
        return totalAverage;
    }

    /**
     * Gets the total average time spent of all book projects combined between today and the input
     * number of days in the past.
     * @param days The number of days in the past.
     * @return The average time spent.
     */
    public String averageTime_allBooks(int days)
    {
        long totalAverage = 0;
        for(Book book : books) {
            totalAverage += book.averageTime(days);
        }
        return totalAverage/60 + " hours " + totalAverage%60 + " minutes/day";
    }

    /**
     * Private method
     * Gets the total average time spent of all book projects combined between two days.
     * @param year1 The year of the start date.
     * @param month1 The month of the start date.
     * @param day1 The day of the start date.
     * @param year2 The year of the end date.
     * @param month2 The year of the end date.
     * @param day2 The day of the end date.
     * @return The average time spent.
     */
    public long averageTime_allBooks(int year1, int month1, int day1, int year2, int month2, int day2)
    {
        long totalAverage = 0;
        for(Book book : books) {
            totalAverage += book.averageTime(year1, month1, day1, year2, month2, day2);
        }
        return totalAverage;
    }
}
