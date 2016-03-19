/**
 * This is the main class for this program. This program simply takes the two
 * hard coded matrices (A and B) and performs a matrix multiplication on them.
 * It does so by creating a new thread to perform the multiplication for each
 * position in the final matrix. The result is then stored in matrix C.
 * 
 * @author Cameron Stanavige
 * @version 5/31/2015
 *
 */
public class MainThread {

	protected static int M;
	protected static int K;
	protected static int N;

	private static int[][] A;
	private static int[][] B;
	private static int[][] C;

	private static int NUM_THREADS;
	private WorkerThread[] workers;

	private MatrixGUI gui;

	/**
	 * Constructor for this class. Creates a GUI for the user to interact with
	 * this program.
	 */
	public MainThread() {
		gui = new MatrixGUI(this);
	}

	/**
	 * Assigns the input dimensions for the matrices, creates the solution
	 * matrix, and creates the array for the worker threads to be used to do the
	 * calculations.
	 * 
	 * @param M
	 *            The number of rows for matrix A and C.
	 * @param K
	 *            The number of columns for matrix A and rows for matrix B.
	 * @param N
	 *            The number of columns for matrix B and C.
	 */
	public void initialize(int M, int K, int N) {
		this.M = M;
		this.K = K;
		this.N = N;
		this.C = new int[M][N];
		NUM_THREADS = M * N;
		workers = new WorkerThread[NUM_THREADS];
	}

	/**
	 * Assigns the two arrays coming in from the GUI to the two arrays to be
	 * multiplied together. Then calls the methods used to create the individual
	 * threads to do the calculations.
	 * 
	 * @param A
	 *            The first array being multiplied.
	 * @param B
	 *            The second array being multiplied.
	 */
	public void process(int[][] A, int[][] B) {
		this.A = A;
		this.B = B;
		createThreads();
		waitThreads();
		printResult();
	}

	/**
	 * Private Method: Creates and starts each of the individual threads for
	 * each slot in the resulting matrix.
	 */
	private void createThreads() {
		int thread = 0;
		for (int i = 0; i < M; i++) { // row
			for (int j = 0; j < N; j++) { // column
				workers[thread] = new WorkerThread(i, j, A, B, C);
				workers[thread].start();
				thread++;
			}
		}
	}

	/**
	 * Private Method: Waits for all the threads to finish.
	 */
	private void waitThreads() {
		for (int i = 0; i < NUM_THREADS; i++) {
			try {
				workers[i].join();
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Private Method: Sends the solution matrix to the gui to be printed out.
	 */
	private void printResult() {
		gui.printResult(C);
	}

	public static void main(String[] args) {
		MainThread main = new MainThread();
	}
} // end class
