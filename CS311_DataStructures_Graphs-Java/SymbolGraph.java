import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//
//SymbolGraph.java
//
//
// Below is the syntax highlighted version of SymbolGraph.java from §4.1 Undirected Graphs.   Here is the Javadoc. 
//

/*************************************************************************
 *  Compilation:  javac SymbolGraph.java
 *  Execution:    java SymbolGraph filename.txt delimiter
 *  Dependencies: ST.java Graph.java In.java StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41undirected/routes.txt
 *                http://algs4.cs.princeton.edu/41undirected/movies.txt
 *                http://algs4.cs.princeton.edu/41undirected/moviestiny.txt
 *                http://algs4.cs.princeton.edu/41undirected/moviesG.txt
 *                http://algs4.cs.princeton.edu/41undirected/moviestopGrossing.txt
 *  
 *  %  java SymbolGraph routes.txt " "
 *  JFK
 *     MCO
 *     ATL
 *     ORD
 *  LAX
 *     PHX
 *     LAS
 *
 *  % java SymbolGraph movies.txt "/"
 *  Tin Men (1987)
 *     Hershey, Barbara
 *     Geppi, Cindy
 *     Jones, Kathy (II)
 *     Herr, Marcia
 *     ...
 *     Blumenfeld, Alan
 *     DeBoy, David
 *  Bacon, Kevin
 *     Woodsman, The (2004)
 *     Wild Things (1998)
 *     Where the Truth Lies (2005)
 *     Tremors (1990)
 *     ...
 *     Apollo 13 (1995)
 *     Animal House (1978)
 *
 * 
 *  Assumes that input file is encoded using UTF-8.
 *  % iconv -f ISO-8859-1 -t UTF-8 movies-iso8859.txt > movies.txt
 *
 *************************************************************************/

/**
 * The <tt>SymbolGraph</tt> class represents an undirected graph, where the
 * vertex names are arbitrary strings. By providing mappings between string
 * vertex names and integers, it serves as a wrapper around the {@link Graph}
 * data type, which assumes the vertex names are integers between 0 and
 * <em>V</em> - 1. It also supports initializing a symbol graph from a file.
 * <p>
 * This implementation uses an {@link ST} to map from strings to integers, an
 * array to map from integers to strings, and a {@link Graph} to store the
 * underlying graph. The <em>index</em> and <em>contains</em> operations take
 * time proportional to log <em>V</em>, where <em>V</em> is the number of
 * vertices. The <em>name</em> operation takes constant time.
 * <p>
 * For additional documentation, see <a
 * href="http://algs4.cs.princeton.edu/41undirected">Section 4.1</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @author Altered by Cameron Stanavige for a specific structure.
 */
public class SymbolGraph {
	private ST<String, Integer> st; // string -> index
	private String[] keys; // index -> vertex
	private Graph G;

	private boolean[] actors; // true if actor

	private boolean[] visited;
	private int[] edgeTo;

	/**
	 * Initializes a graph from a file using the specified delimiter. Each line
	 * in the file contains the name of a vertex, followed by a list of the
	 * names of the vertices adjacent to that vertex, separated by the
	 * delimiter.
	 * 
	 * @param filename
	 *            the name of the file
	 * @param delimiter
	 *            the delimiter between fields
	 */
	public SymbolGraph(String filename, String delimiter) {
		st = new ST<String, Integer>();

		// First pass builds the index by reading strings to associate
		// distinct strings with an index
		In in = new In(filename);
		// while (in.hasNextLine()) {
		while (!in.isEmpty()) {
			String[] a = in.readLine().split(delimiter);
			for (int i = 0; i < a.length; i++) {
				if (!st.contains(a[i]))
					st.put(a[i], st.size());
			}
		}
		StdOut.println("Done reading " + filename);

		// inverted index to get string keys in an array
		keys = new String[st.size()];
		for (String name : st.keys()) {
			keys[st.get(name)] = name;
		}

		// fill arrays
		visited = new boolean[st.size()];
		Arrays.fill(visited, Boolean.FALSE);
		actors = new boolean[st.size()];
		Arrays.fill(actors, Boolean.FALSE);

		// second pass builds the graph by connecting first vertex on each
		// line to all others
		G = new Graph(st.size());
		in = new In(filename);
		while (in.hasNextLine()) {
			String[] a = in.readLine().split(delimiter);
			int v = st.get(a[0]);
			setActor(v);
			for (int i = 1; i < a.length; i++) {
				int w = st.get(a[i]);
				G.addEdge(v, w);
			}
		}
	}

	/**
	 * Does the graph contain the vertex named <tt>s</tt>?
	 * 
	 * @param s
	 *            the name of a vertex
	 * @return <tt>true</tt> if <tt>s</tt> is the name of a vertex, and
	 *         <tt>false</tt> otherwise
	 */
	public boolean contains(String s) {
		return st.contains(s);
	}

	/**
	 * Returns the integer associated with the vertex named <tt>s</tt>.
	 * 
	 * @param s
	 *            the name of a vertex
	 * @return the integer (between 0 and <em>V</em> - 1) associated with the
	 *         vertex named <tt>s</tt>
	 */
	public int index(String s) {
		return st.get(s);
	}

	/**
	 * Returns the name of the vertex associated with the integer <tt>v</tt>.
	 * 
	 * @param v
	 *            the integer corresponding to a vertex (between 0 and
	 *            <em>V</em> - 1)
	 * @return the name of the vertex associated with the integer <tt>v</tt>
	 */
	public String name(int v) {
		// return (String) keys[v].getData();
		return keys[v];
	}

	/**
	 * Returns the graph assoicated with the symbol graph. It is the client's
	 * responsibility not to mutate the graph.
	 * 
	 * @return the graph associated with the symbol graph
	 */
	public Graph G() {
		return G;
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
	public List<String> getPerformersMovies(String performer) {
		LinkedList<String> list = new LinkedList<String>();
		String actor = reformatName(performer); // changes name format
		if (actor == null) {
			list.add("Actor" + actor + "not known");
			return list;
		}
		int value = index(actor);
		for (int i : G.adj(value)) {
			list.add(name(i));
		}
		Collections.sort(list);
		return list;
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
	public List<String> topPerformers(int n) {
		LinkedList<String> list = new LinkedList<String>();
		ArrayList<String> actors = new ArrayList<String>();
		for (int i = 0; i < G.V() - 1; i++) { // gets all the actors
			String perf = name(i);
			int vertex = index(perf);
			if (isActor(vertex))
				actors.add(perf);
		}
		int count = 1;
		while (count <= n) { // gets the top n performers
			String top = actors.get(0);
			for (String v : actors) { // gets next top performer
				int vert = index(v);
				if ((isVisited(vert) == false)
						&& (G().degree(vert) > (G().degree(index(top))))) {
					top = v;
				}
			}
			visit(index(top));
			list.add(top);
			count++;
		}
		resetVisited();
		return list;
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
	public List<String> topMovies(int n) {
		LinkedList<String> list = new LinkedList<String>();
		ArrayList<String> movies = new ArrayList<String>();
		for (int i = 0; i < G().V() - 1; i++) { // gets all the movies
			String movie = name(i);
			int vertex = index(movie);
			if (!(isActor(vertex)))
				movies.add(movie);
		}
		int count = 1;
		while (count <= n) { // get top n movies
			String top = movies.get(0);
			for (String v : movies) { // gets next top movie
				int vert = index(v);
				if ((isVisited(vert) == false)
						&& (G().degree(vert)) > (G().degree(index(top))))
					top = v;
			}

			visit(index(top));
			list.add(top);
			count++;
		}
		resetVisited();
		return list;
	}

	/**
	 * Private Method: Determines if an vertex has already been visited
	 * 
	 * @param v
	 *            The vertex to be determined
	 * @return True if the vertex has already been visited.
	 */
	private boolean isVisited(int v) {
		return visited[v];
	}

	/**
	 * Private Method: Visits a particular vertex.
	 * 
	 * @param v
	 *            The vertex to visit
	 */
	private void visit(int v) {
		visited[v] = true;
	}

	/**
	 * Private Method: Resets all the vertices back to not having been visited.
	 */
	private void resetVisited() {
		Arrays.fill(visited, Boolean.FALSE);
	}

	private void resetEdgeTo() {
		if (edgeTo == null) {
			edgeTo = new int[st.size()];
		}
		Arrays.fill(edgeTo, 0);
	}

	/**
	 * Private Method: Determines if a particular vertex represents an actor or
	 * a movie.
	 * 
	 * @param v
	 *            The vertex to be determined.
	 * @return True if the index represents an actor.
	 */
	private boolean isActor(int v) {
		return actors[v];
	}

	/**
	 * Private Method: Sets that a particular vertex represents an actor.
	 * 
	 * @param v
	 *            The vertex to have set as an actor.
	 */
	private void setActor(int v) {
		actors[v] = true;
	}

	/**
	 * Private Method: Takes a name and determines if the graph has that name in
	 * that format. If not, the format is switched then the case of the first
	 * letters are switched.
	 * 
	 * @param performer
	 *            The name on which to have the formatting checked.
	 * @return The name in the correct format.
	 */
	private String reformatName(String performer) {
		String actor;
		if (!(contains(performer))) { // name is wrong format
			String[] target = performer.split(" ");
			actor = target[1] + ", " + target[0];
			if (!(contains(actor))) { // wrong letter case
				actor = changeFirstLetter(target);
				if (!(contains(actor))) { // actor not in list
					actor = null;
				}
			}
		} else { // name already was correct format
			actor = performer;
		}
		return actor;
	}

	/**
	 * Private Method: Changes the case first letters of the performer's name to
	 * the opposite of what it currently is.
	 * 
	 * @param name
	 *            The name to be changed
	 * @return The name with the changed case.
	 */
	private String changeFirstLetter(String[] name) {
		char[] firstName = name[0].toCharArray();
		char[] lastName = name[1].toCharArray();
		if ((Character.isLowerCase(firstName[0]))
				&& (Character.isLowerCase(lastName[0]))) {
			firstName[0] = Character.toUpperCase(firstName[0]);
			lastName[0] = Character.toUpperCase(lastName[0]);
			return new String(lastName) + ", " + new String(firstName);
		} else {
			firstName[0] = Character.toLowerCase(firstName[0]);
			lastName[0] = Character.toLowerCase(lastName[0]);
			return new String(lastName) + ", " + new String(firstName);
		}
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
	public List<String> getKBNumber(String actor1, String actor2,
			boolean commonNameFormat) throws ActorNotFoundException,
			PathNotFoundException {
		if ((actor1.equals("") | actor2.equals(""))) {
			throw new ActorNotFoundException();
		}
		List<String> movies = new ArrayList<String>();
		if (actor1.equals(actor2)) { // same actor: KBN of 0
			return movies;
		}
		actor1 = reformatName(actor1);
		actor2 = reformatName(actor2);
		if (actor1 == null | actor2 == null) { // actor not known
			throw new ActorNotFoundException();
		}
		int act1 = index(actor1);
		int act2 = index(actor2);
		bfs(act1, act2); // finds path between actors
		if (visited[act2] == false) { // no path between actors
			throw new PathNotFoundException();
		}
		for (int x = act2; x != act1; x = edgeTo[x]) {// walks path
			if (!isActor(x)) // adds movie vertices to list
				movies.add(name(x));
		}
		return movies;
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
	public List<Colleagues> findTopActorPairings() {
		List<Colleagues> topList = new ArrayList<Colleagues>();
		int mostMovies = 0; // most movies of a Colleague
		for (int actor = 0; actor < keys.length; actor++) {
			if (isActor(actor) && (G.degree(actor) >= mostMovies)) {
				// skips actors that have been in less movies than current high
				Colleagues topColleague = findTopColleague(actor);
				if (!(topColleague == null)) {// has a top colleague
					int mov = topColleague.numMoviesShared;
					if (mov > mostMovies) { // new high
						topList.clear();
						mostMovies = mov;
						topList.add(topColleague);
					}
					if ((mov == mostMovies) // same as current high
							&& topList.contains(topColleague) == false) {
						topList.add(topColleague);
					}
				}
			}
		}
		return topList;
	}

	/**
	 * Private Method: Finds the Colleague that has been in the most movies with
	 * a specific actor.
	 * 
	 * @param actor
	 *            The actor to find the top acting Colleague for.
	 * @return The top Colleagues object with the desired actor and their top
	 *         acting Colleague.
	 */
	private Colleagues findTopColleague(int actor) {
		ST<Integer, Integer> colleagues = new ST<Integer, Integer>();
		// tree to hold this actors colleagues and num shared movies
		for (int v : G.adj(actor)) { // actor to movies edges
			for (int a : G.adj(v)) { // movies to actor edges
				if ((a != actor)) {
					if (colleagues.contains(a)) {
						colleagues.put(a, colleagues.get(a) + 1);
					} else {
						colleagues.put(a, 1);
					}
				}
			}
		}
		if (!colleagues.isEmpty()) { // no colleagues in this movie list
			int topCol = colleagues.min();
			for (Integer x : colleagues) { // find colleague with most movies
				if (colleagues.get(x) > colleagues.get(topCol))
					topCol = x;
			}
			return new Colleagues(name(actor), name(topCol),
					colleagues.get(topCol));
		} else { // no colleagues in this movie list
			return null;
		}
	}

	/**
	 * Finds the path and creates the edgeTo array between two actors.
	 * 
	 * @param start
	 *            The starting actor.
	 * @param target
	 *            The actor to find the path to.
	 */
	private void bfs(int start, int target) {
		resetVisited();
		resetEdgeTo();
		java.util.Queue<Integer> queue = new java.util.LinkedList<Integer>();
		visited[start] = true;
		queue.add(start);
		while (!queue.isEmpty()) {
			int v = queue.poll();
			for (int w : G.adj(v)) {
				if (visited[w] == false) {
					edgeTo[w] = v;
					visited[w] = true;
					queue.add(w);
				}
			}
			if (visited[target] == true) { // breaks loop if target reached
				queue.clear();
			}
		}
	}

	/**
	 * Unit tests the <tt>SymbolGraph</tt> data type.
	 */
	// public static void main(String[] args) {
	// String filename = args[0];
	// String delimiter = args[1];
	// SymbolGraph sg = new SymbolGraph(filename, delimiter);
	// Graph G = sg.G();
	// while (StdIn.hasNextLine()) {
	// String source = StdIn.readLine();
	// if (sg.contains(source)) {
	// int s = sg.index(source);
	// for (int v : G.adj(s)) {
	// StdOut.println("   " + sg.name(v));
	// }
	// }
	// else {
	// StdOut.println("input not contain '" + source + "'");
	// }
	// }
	// }
}
//
//
//
//
//
// Copyright © 2002–2010, Robert Sedgewick and Kevin Wayne.
// Last updated: Wed Dec 18 21:57:12 EST 2013.
