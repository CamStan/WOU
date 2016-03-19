/**
 * The class represents a directed, weighted edge between a source vertex and a
 * target vertex that can be disabled.
 * 
 * @author Justin Ross
 * @author Cameron Stanavige
 *
 */
public class Edge {
	private String name;
	private int weight;

	private Vertex source;
	private Vertex target;

	private boolean usable; // indicate if this edge is usable

	/**
	 * Default constructor: Creates an empty edge
	 */
	public Edge() {
		// leave empty
	}

	/**
	 * Creates a directed edge with the desired source, target, and weight
	 * 
	 * @param src
	 *            The source Vertex
	 * @param tgt
	 *            The target Vertex
	 * @param val
	 *            The weight on the edge
	 * @param n
	 *            The name of this edge
	 * @return The newly created Edge
	 */
	public Edge createEdge(Vertex src, Vertex tgt, int val, String n) {
		source = src;
		target = tgt;
		weight = val;
		name = n;
		System.out.println("Edge: " + name + " connects " + source.getName()
				+ " -> " + target.getName() + " and has weight: " + weight);
		usable = true;
		return this;
	}

	/**
	 * Gets the name of this edge
	 * 
	 * @return The name of this edge
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the weight on the edge
	 * 
	 * @return The weight on this edge
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Sets the weight on the edge
	 * 
	 * @param weight
	 *            The new weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * Gets the source Vertex
	 * 
	 * @return The source Vertex
	 */
	public Vertex getSource() {
		return source;
	}

	/**
	 * Sets the source Vertex to the desired vertex
	 * 
	 * @param source
	 *            The new source Vertex
	 */
	public void setSource(Vertex source) {
		this.source = source;
	}

	/**
	 * Gets the target Vertex
	 * 
	 * @return The target Vertex
	 */
	public Vertex getTarget() {
		return target;
	}

	/**
	 * Sets the target Vertex
	 * 
	 * @param target
	 *            The new target Vertex
	 */
	public void setTarget(Vertex target) {
		this.target = target;
	}

	/**
	 * Determine if this edge is usable
	 * 
	 * @return True if this edge is usable
	 */
	public boolean isUsable() {
		return usable;
	}

	/**
	 * Sets this edge's usability to the opposite of its current value
	 */
	public void setUsable() {
		usable = !usable;
	}

	/**
	 * Override to determine if this edge is equal to another edge
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Edge))
			return false;
		Edge other = (Edge) obj;
		return this.name.equals(other.name);
	}

	/**
	 * Override to calculate the hashcode for this object
	 */
	public int hashCode() {
		int result = 13;
		result = 37 * result + source.hashCode();
		result = 37 * result + target.hashCode();
		return result;
	}

}
