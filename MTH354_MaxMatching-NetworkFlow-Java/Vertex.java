import java.util.LinkedList;

/**
 * This class represents a vertex in a graph. Each vertex contains a list of
 * it's own edges incident to it. Each vertex, in this case, can represent an
 * applicant or a job, can be visited, and can be matched with another vertex.
 * 
 * @author Justin Ross
 * @author Cameron Stanavige
 *
 */
public class Vertex {
	private char name;
	private int index;

	private Vertex match; // which vertex this vertex is matched with
	private boolean visited; // if this vertex has been visited
	private boolean applicant; // true if vertex represents an applicant

	private LinkedList<Edge> edgeList;

	/**
	 * Constructor to add name and index ID
	 * 
	 * @param param
	 *            The name of the Vertex
	 * @param ind
	 *            The index ID
	 */
	public Vertex(char param, int ind) {
		name = param;
		edgeList = new LinkedList<Edge>();
		index = ind;
		visited = false;
		applicant = false;
		match = null;
	}

	/**
	 * Gets the name of this vertex
	 * 
	 * @return The name of this vertex
	 */
	public char getName() {
		return name;
	}

	/**
	 * Gets the index ID of this Vertex
	 * 
	 * @return The ID of this Vertex
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sets the index ID of this Vertex
	 * 
	 * @param i
	 *            The desired ID for this Vertex
	 */
	public void setIndex(int i) {
		index = i;
	}

	/**
	 * Gets the degree of this Vertex
	 * 
	 * @return The degree of this Vertex
	 */
	public int degree() {
		return edgeList.size();
	}

	/**
	 * Determines if this Vertex represents an applicant or a job
	 * 
	 * @return True if this Vertex represents a job applicant
	 */
	public boolean isApplicant() {
		return applicant;
	}

	/**
	 * Determine if this Vertex has been visited
	 * 
	 * @return True if this Vertex has been visited
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * Sets the visited field to its current opposite
	 */
	public void setVisited() {
		visited = !visited;
	}

	/**
	 * Gets the vertex this vertex is matched with.
	 * 
	 * @return The vertex's match
	 */
	public Vertex getMatch() {
		return match;
	}

	/**
	 * Sets this vertex's match
	 * 
	 * @param v
	 *            The vertex to match with this one
	 */
	public void setMatch(Vertex v) {
		match = v;
	}

	/**
	 * Adds a directed edge with a weight to this Vertex
	 * 
	 * @param param
	 *            The target vertex for this Edge
	 * @param val
	 *            The weight for this edge
	 */
	public void addEdge(Vertex param, int val, String name) {
		if (!(this.getNeighbors().contains(param))) {
			edgeList.add(new Edge().createEdge(this, param, val, name));
			if (param.getName() != 'Z')
				this.applicant = true;
		}
	}

	/**
	 * Gets the edges incident to this vertex
	 * 
	 * @return List of edges incident to this vertex
	 */
	public LinkedList<Edge> getEdges() {
		return edgeList;
	}

	/**
	 * Gets the degree of this vertex based on the number of edges incident to
	 * this vertex that are currently usable
	 * 
	 * @return The number of usable edges incident to this vertex
	 */
	public int numUsableEdges() {
		int count = 0;
		for (Edge e : edgeList) {
			if (e.isUsable() == true)
				count++;
		}
		return count;
	}

	/**
	 * Gets the Vertices adjacent to this vertex
	 * 
	 * @return List of vertices adjacent to this vertex
	 */
	private LinkedList<Vertex> getNeighbors() {
		LinkedList<Vertex> neighbors = new LinkedList<Vertex>();
		for (Edge e : edgeList) {
			neighbors.add(e.getTarget());
		}
		return neighbors;
	}

	/**
	 * Override method to determine if this vertex is equal to another
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Vertex))
			return false;
		Vertex other = (Vertex) obj;
		return this.name == other.name;
	}

	/**
	 * Override method to calculate this objects hashcode
	 */
	public int hashCode() {
		int result = 17;
		return result = 37 * result + name;
	}

}
