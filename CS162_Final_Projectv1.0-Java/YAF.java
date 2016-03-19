
/**
 * A Young Adult Fiction(YAF) book is a subtype of book with its own unique title.
 *  
 * @author Cameron Stanavige 
 * @version 5/22/2014
 */
public class YAF extends Book
{
    private String title;
    private String genre;

    /**
     * Default constructor that creates a new YAF book with a title and genre.
     * @param title The title of the book.
     * @param genre The genre of the book.
     */
    public YAF(String title)
    {
        super();
        this.title = title;
        this.genre = "Young Adult Fiction";
    }

    /**
     * Non-default constructor that creates an already going YAF book with the input amount
     * of total word count and time already spent on the project.
     * @param title The title of the book.
     * @param totalCount The total word count already put into the project.
     * @param totalTime The total amount of time (in minutes) already spent on the project.
     */
    public YAF(String title, long totalCount, long totalTime)
    {
        super(totalCount, totalTime);
        this.title = title;
        this.genre = "Young Adult Fiction";
    }

    /**
     * Gets the title of the book.
     * @return The title of the book.
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Sets the title of the book.
     * @param newTitle The new title of the book.
     */
    public void setTitle(String newTitle)
    {
        title = newTitle;
    }

    /**
     * Gets the genre of the book.
     * @return The genre of the book.
     */
    public String getGenre()
    {
        return genre;
    }

    /**
     * The toString for Book.
     * @return The toString for Book.
     */
    public String toString()
    {
        return "Title: " + title + "\nGenre: " + genre + "\n" + super.toString();
    }
}
