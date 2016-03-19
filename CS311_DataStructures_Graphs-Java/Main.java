import java.io.IOException;
import java.util.List;

/**
 * CS 311 Lab 2: Graphs, Part I
 * 
 * This lab reads in a text file containing actors and actresses and the movies
 * they have starred in, and creates an undirected graph representing these
 * relationships.
 * 
 * After building the graph, information about it can be obtained by calling the
 * static methods below. In addition, simple searches may be performed.
 * 
 * @author Cameron Stanavige
 */

public class Main {
	private static SymbolGraph sg;

	/**
	 * Entry-point method. You must call this before invoking any of the other
	 * methods.
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out
					.println("Please pass this program the file to read, like this:");
			System.out.println("\tjava Main movies_2000.txt");
			System.out.println("or\n\t java Main movies_2000.txt print");
			System.out.println("if you want to print a report");
			System.exit(1);
		}
		String infile = args[0];
		System.out.println("Reading actors/actresses/movies from: " + infile);

		// Do what you need to do in order to have everything ready for the
		// methods that follow
		sg = new SymbolGraph(infile, "\\|");

		// Print a report
		if (args.length == 2 && args[1].equals("print")) {
			System.out.println("----------------------------------");
			System.out.printf("File %s contains:\n", infile);
			System.out.printf("\t%d vertices\n\t%d edges\n",
					getNumberOfVertices(), getNumberOfEdges());
			System.out
					.println("Sean Connery has acted in the following movies:");
			for (String movie : getMovies("Sean Connery")) {
				System.out.println("\t" + movie);
			}
			System.out
					.println("The top 10 performers who have starred in the most movies are:");
			for (String a : getTopPerformers(10)) {
				System.out.println("\t" + a);
			}
			System.out
					.println("The top 10 movies that have the most performers are:");
			for (String a : getTopMovies(10)) {
				System.out.println("\t" + a);
			}
			System.out.println("----------------------------------");
		}
	}

	/**
	 * Get the total number of vertices in the graph.
	 * 
	 * @return |V|, which includes both movies and performers
	 */
	public static int getNumberOfVertices() {
		return sg.G().V();
	}

	/**
	 * Get the total number of edges in the graph.
	 * 
	 * @return |E|, for which every edge connects a performer with a movie
	 */
	public static int getNumberOfEdges() {
		return sg.G().E();
	}

	/**
	 * Get a list of all the movies that the given actress or actor has acted
	 * in.
	 * 
	 * @param performer
	 *            A string representing the performer to search for. An exact
	 *            match is NOT required. Furthermore this string is assumed to
	 *            be in First Last format. e.g. "Sean Connery" or
	 *            "sean connery". You may use the first actor for which this
	 *            match is found.
	 * @return A list of the movies the performer has acted in, sorted
	 *         alphabetically.
	 */
	public static List<String> getMovies(String performer) {
		return sg.getPerformersMovies(performer);
	}

	/**
	 * Get a list of the top performers, according to how many movies they have
	 * acted in.
	 * 
	 * @param n
	 *            Run this search for the top n performers.
	 * @return A list of performers, ranked (sorted) by how many movies they
	 *         have acted in. The first entry in this list is the actress or
	 *         actor who has starred in the most movies.
	 */
	public static List<String> getTopPerformers(int n) {
		return sg.topPerformers(n);
	}

	/**
	 * Get a list of the top movies, according to how many performers they have.
	 * 
	 * @param n
	 *            Run this search for the top n movies.
	 * @return A list of movies, ranked (sorted) by how many performers they
	 *         have. The first entry in this list is the movie with the most
	 *         performers.
	 */
	public static List<String> getTopMovies(int n) {
		return sg.topMovies(n);

	}

	// Lab 3

	/**
	 * Find the Kevin Bacon number for actor2 assuming actor1 is the starting
	 * point. To find the actual KB# for actor Harvey Keitel, for example, you
	 * would call: getKGNumber("Kevin Bacon", "Harvey Keitel", true);
	 * 
	 * To find the DH# for Kate Winslet you would do this:
	 * getKBNumber("Hopper, Dennis", "Lawrence, Jennifer (III)", false)
	 * 
	 * As you can see the latter format allows you to define exactly which
	 * person you mean to search for.
	 * 
	 * @param actor1
	 *            The starting actor, assumed to be the first last format
	 * @param actor2
	 *            The ending actor, assumed to be the first last format
	 * @param commonNameFormat
	 *            true means that the names passed in for the actors is in First
	 *            Last format, false means the names are assumed to be in the
	 *            original Last, First format
	 * @return A list of movies that connects actor1 to actor2 and that is the
	 *         shortest path in the graph that connects such actors. The length
	 *         of this list is the KB# getKBNumber("Kevin Bacon", "Kevin Bacon")
	 *         should return an empty list giving KB# of 0
	 * @throws ActorNotFoundException
	 *             Thrown to indicate that one or both of the actors could not
	 *             be found.
	 * @throws PathNotFoundException
	 *             Thrown to indicate that a path could not be found between the
	 *             two actors.
	 */
	public static List<String> getKBNumber(String actor1, String actor2,
			boolean commonNameFormat) throws ActorNotFoundException,
			PathNotFoundException {
		if (actor1 == null | actor2 == null)
			throw new ActorNotFoundException();
		return sg.getKBNumber(actor1, actor2, commonNameFormat);
	}

	/**
	 * Find the two actors who have starred in the most movies together. If
	 * there are ties, return all tying parts.
	 * 
	 * @return A list of Colleagues, that represent the pairs of actors who have
	 *         the most number of movies in common. The actors must be distinct
	 *         (i.e. different). The list is unordered, but will all have the
	 *         same count of movies in common.
	 */
	public static List<Colleagues> findTopActorPairings() {
		return sg.findTopActorPairings();
	}

} // end class Main
