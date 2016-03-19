import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class represents a directed, weighted, bipartite graph.
 * 
 * @author Justin Ross
 * @author Cameron Stanavige
 * @author Xiaoqi Liu (Chi)
 */
public class Graph {
	private int V;
	private int E;
	private ArrayList<Vertex> adj;

	/**
	 * Initializes an empty graph with V vertices and 0 edges.
	 * 
	 * @param V
	 *            the number of vertices
	 * 
	 * @throws java.lang.IllegalArgumentException
	 *             if V < 0
	 */
	public Graph(int V) {
		if (V < 0)
			throw new IllegalArgumentException(
					"Number of vertices must be nonnegative");
		this.V = V;
		this.E = 0;
		adj = new ArrayList<Vertex>(V);

	}

	/**
	 * Adds a vertex to this graph.
	 * 
	 * @param param
	 *            The vertex to add to this graph
	 */
	public void addVertex(Vertex param) {
		adj.add(V, param);
		param.setIndex(V);
		V++;
	}

	/**
	 * Returns the number of vertices in the graph.
	 * 
	 * @return the number of vertices in the graph
	 */
	public int V() {
		return V;
	}

	/**
	 * Returns the number of edges in the graph.
	 * 
	 * @return the number of edges in the graph
	 */
	public int E() {
		return E;
	}

	/**
	 * Gets the collection of vertices in this graph
	 * 
	 * @return The list of vertices in this graph
	 */
	public ArrayList<Vertex> getVertices() {
		return adj;
	}

	/**
	 * Gets the vertices that represent job applicants
	 * 
	 * @return The list of job applicant vertices
	 */
	public ArrayList<Vertex> getApplicants() {
		ArrayList<Vertex> applicants = new ArrayList<Vertex>();
		for (Vertex v : adj) {
			if (v.isApplicant())
				applicants.add(v);
		}
		return applicants;
	}

	/**
	 * Gets the vertices that represent jobs
	 * 
	 * @return The list of job vertices
	 */
	public ArrayList<Vertex> getJobs() {
		ArrayList<Vertex> jobs = new ArrayList<Vertex>();
		for (Vertex v : adj) {
			if (!v.isApplicant())
				jobs.add(v);
		}
		return jobs;
	}

	/**
	 * Checks if the desired vertex is valid
	 * 
	 * @param v
	 *            The desired vertex
	 * 
	 * @throw IndexOutOfBoundsException unless 0 <= v < V
	 */
	private void validateVertex(Vertex v) {
		if (v.getIndex() < 0 || v.getIndex() >= V)
			throw new IndexOutOfBoundsException("vertex " + v
					+ " is not between 0 and " + (V - 1));
	}

	/**
	 * Adds a directed edge from v-w to the graph.
	 * 
	 * @param v
	 *            source vertex in the edge
	 * @param w
	 *            target vertex in the edge
	 * @param weight
	 *            The weight on this edge
	 * 
	 * @throws java.lang.IndexOutOfBoundsException
	 *             unless both 0 <= v < V and 0 <= w < V
	 */
	public void addEdge(char v, char w, int weight, String name) {
		if (v != w) {
			Vertex vv = null;
			Vertex ww = null;
			for (Vertex test : adj) {
				if (test.getName() == v) {
					vv = test;
				}
				if (test.getName() == w) {
					ww = test;
				}
			}
			if ((vv == null) | (ww == null)) {
				throw new IllegalArgumentException("Vertex not found");
			} else {
				validateVertex(vv);
				validateVertex(ww);
				E++;
				adj.get(vv.getIndex()).addEdge(ww, weight, name);
			}
		}
	}

	/**
	 * Finds the maximum matching in this graph with the highest sum of edge
	 * weights
	 * 
	 * @return The maximum matching with the highest edges weights
	 */
	public Matching findMaxWeightedMatching() {
		HashSet<Matching> M = new HashSet<Matching>();
		int maxFlow = 0;

		Matching originalMatching = findMatching();
		maxFlow = originalMatching.size();
		M.add(originalMatching);

		for (Vertex v : getApplicants()) {
			matchingByVertex(v, originalMatching, maxFlow, M, v);
		}

		int maxWeight = 0;
		Matching maxMatching = null;
		for (Matching mat : M) {
			if (mat.matchingWeight() > maxWeight) {
				maxWeight = mat.matchingWeight();
				maxMatching = mat;
			}
		}
		System.out.println("# of Maximum Matches: " + M.size());
		return maxMatching;
	}

	// finds the maximum matching
	private Matching findMatching() {
		Matching m = new Matching();
		for (Vertex v : getApplicants()) {
			resetVisited();
			matchingHelper(v, m);
		}
		return m;
	}

	// determines edges to used in the matching by finding augmenting paths
	private Edge matchingHelper(Vertex v, Matching m) {
		for (Edge edge : v.getEdges()) {
			Vertex w = edge.getTarget();
			if (!edge.isUsable() || w.isVisited())
				continue;
			w.setVisited();
			Vertex curMatch = w.getMatch();
			if (curMatch == null || matchingHelper(curMatch, m) != null) {
				m.addEdge(edge);
				v.setMatch(w);
				w.setMatch(v);
				return edge;
			}
		}
		return null;
	}

	// find alternate matchings by disable edges
	private void matchingByVertex(Vertex v, Matching m, int k,
			HashSet<Matching> matches, Vertex calling) {

		if (m.containsEdgeFrom(v)) {
			Edge e = m.getEdge(v);
			e.setUsable();
			if (!(numVerticesWithPosDegree() < k)) {
				resetMatches();
				Matching newMatch = findMatching();
				if (newMatch.size() == k && !matches.contains(newMatch)) {
					matches.add(newMatch);
					for (Vertex x : getApplicants()) {
						if (!x.equals(v))
							matchingByVertex(x, newMatch, k, matches, v);
					}
				}
				matchingByVertex(v, newMatch, k, matches, v);
			}
			e.setUsable();
		}
	}

	/**
	 * Resets each vertices match
	 */
	private void resetMatches() {
		for (Vertex v : adj)
			v.setMatch(null);
	}

	/**
	 * Resets the visited field of each vertex
	 */
	private void resetVisited() {
		for (Vertex v : adj) {
			if (v.isVisited())
				v.setVisited();
		}
	}

	/**
	 * Determines the total number of vertices in the graph that have degree > 0
	 * 
	 * @return The number of vertices with degree > 0
	 */
	private int numVerticesWithPosDegree() {
		int c = 0;
		for (Vertex v : adj) {
			if (v.numUsableEdges() > 0)
				c++;
		}
		return c;
	}
}
