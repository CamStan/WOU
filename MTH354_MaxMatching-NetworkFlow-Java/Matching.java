import java.util.HashSet;

/**
 * This class represents a maximum matching in a bipartite graph. A matching
 * contains a list of edges that make up that matching. A matching also has a
 * total weight that is the sum of all the edge weights within the matching.
 * 
 * @author Cameron Stanavige
 *
 */
public class Matching {

	private HashSet<Edge> match;

	/**
	 * Constructor to create a new matching
	 */
	public Matching() {
		match = new HashSet<Edge>();
	}

	/**
	 * Gets the edges contained in this matching
	 * 
	 * @return Set of edges contained in this mapping
	 */
	public HashSet<Edge> getMatching() {
		return match;
	}

	/**
	 * Gets the size, or maxFlow, of this matching
	 * 
	 * @return The size of this matching
	 */
	public int size() {
		return match.size();
	}

	/**
	 * Gets the edge from this matching that is incident to the desired vertex
	 * 
	 * @param v
	 *            The vertex to get the edge incident to
	 * @return The edge incident to the input vertex
	 */
	public Edge getEdge(Vertex v) {
		for (Edge e : match) {
			if (e.getSource().equals(v) || e.getTarget().equals(v))
				return e;
		}
		return null;
	}

	/**
	 * Determines if there is a directed edge coming out of the desired vertex
	 * in this matching
	 * 
	 * @param v
	 *            The vertex of which to determine if this matching contains an
	 *            edge incident to
	 * @return True if this matching contains a directed edge extending from the
	 *         desired vertex
	 */
	public boolean containsEdgeFrom(Vertex v) {
		return match.stream().anyMatch(e -> e.getSource().equals(v));
	}

	/**
	 * Adds an edge to this matching. Removes any older edge that is incident to
	 * a vertex within the new edge
	 * 
	 * @param e
	 *            The edge to add to this matching
	 */
	public void addEdge(Edge e) {
		Edge old = getEdge(e.getSource());
		if (old != null)
			removeEdge(old);
		match.add(e);
	}

	/**
	 * Removes the desired edge from this matching
	 * 
	 * @param e
	 *            The edge to remove from this matching
	 */
	private void removeEdge(Edge e) {
		match.remove(e);
	}

	/**
	 * Gets the total weight of all the edges in this matching
	 * 
	 * @return The total weight of all the edges in this matching
	 */
	public int matchingWeight() {
		return match.stream().mapToInt(Edge::getWeight).sum();
	}

	public void printMatching() {
		System.out.print("[\n");
		for (Edge e : match) {
			System.out.println(e.getName() + ": " + e.getSource().getName() + " -> "
					+ e.getTarget().getName());
		}
		System.out.println("]\nweight = " + matchingWeight());
	}

	/**
	 * Override to determine if this matching is equal to another matching
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Matching))
			return false;
		Matching other = (Matching) obj;
		return match.equals(other.match);
	}

	/**
	 * Override to calculate the hashcode for this matching
	 */
	public int hashCode() {
		int result = 13;
		for (Edge e : match)
			result = 17 * result + e.hashCode();
		return result;
	}

}
