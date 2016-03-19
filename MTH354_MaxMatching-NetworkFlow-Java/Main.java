/**
 * The main method for this program, compile and run the program from here.
 * To compile from the command line, navigate to the file where all 5 of these
 * classes are located. In the command prompt, type:
 * 		javac Main.java
 * to compile. 
 * 
 * Then, to run the program, 3 arguments are required in the manner of:
 * 		1) list of vertices with single character names (abcdef)
 * 		2) list of edge pairs of those vertices (adaeafbdbecf)
 * 		3) edge weights for each edge pair entered separated by commas (16,5,10,2,99,55)
 * Thus the input will look like:
 * 		java Main abcdef adaeafbdbecf 16,5,10,2,99,55
 * 
 * Again, an example of the two command to run this from the command line are:
 * 		javac Main.java
 * 		java Main abcdef adaeafbdbecf 16,5,10,2,99,55
 * 
 * NOTE: There is no check to determine if the entered vertices/edges make a bipartite
 * graph, thus the responsibility is on the user to ensure the input graph is bipartite.
 * 
 * @author Justin Ross
 * @author Cameron Stanavige
 * @author Xiaoqi Liu (Chi)
 */
public class Main {
	static Graph graph;

	/**
	 * Entry-point method. You must call this before invoking any of the other
	 * methods.
	 * 
	 * Creates and populates the graph.
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out
					.println("Requires 3 arguments in the style of 'abcdefghij afagahaibfbicjdhdidjegehei 11,42,13,97,101,57,6,1,9,67,87,23,44'");
			// or 'abcdefghi afaibfbicecgchcidfdi 100,5,75,50,10,20,30,40,25,65' -- non complete bipartite graph
			// or k5,5 -- 'abcdefghij afagahaiajbfbgbhbibjcfcgchcicjdfdgdhdidjefegeheiej 15,19,1,109,99,45,32,23,156,12,17,45,78,123,88,2,90,42,11,3,111,113,13,66,29
			System.exit(1);
		}

		System.out.println("The Graph, G, is constructed.");

		// populate the graph
		char[] vertices = args[0].toCharArray();

		graph = new Graph(0);

		// create vertices
		for (int i = 0; i < vertices.length; i++) {
			graph.addVertex(new Vertex(vertices[i], i));
		}

		// get weights
		String[] s = args[2].split(",");
		int[] weights = new int[s.length];
		for (int i = 0; i < s.length; i++) {
			weights[i] = Integer.parseInt(s[i]);
		}

		// create edges
		char[] edges = args[1].toCharArray();

		for (int i = 0, j = 0; i < edges.length; i += 2, j++) {
			graph.addEdge(edges[i], edges[i + 1], weights[j], "e" + j);
		}

		// Output results
		System.out.println("The number of vertices is: " + graph.V());
		System.out.println("The number of edges is: " + graph.E());

		System.out.println("m is of size: " + graph.getApplicants().size());
		System.out.println("n is of size: " + graph.getJobs().size());

		System.out.println();

		Matching max = graph.findMaxWeightedMatching();
		System.out.println("\n~~~ The Maximum Weighted Matching ~~~");
		max.printMatching();
	}

} // end class Main