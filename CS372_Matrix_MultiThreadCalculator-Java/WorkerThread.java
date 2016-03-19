/**
 * The individual worker threads for calculating a single slot in the resulting
 * matrix from two other matrices.
 * 
 * @author Cameron Stanavige
 * @version 5/31/2015
 *
 */
public class WorkerThread extends Thread {

	private int row;
	private int col;
	private int[][] A;
	private int[][] B;
	private int[][] C;

	/**
	 * Constructor for a new individual worker thread to perform its
	 * calculation.
	 * 
	 * @param row
	 *            The row to perform the calculation for.
	 * @param col
	 *            The column to perform the calculation for.
	 * @param A
	 *            The matrix whose row will be used.
	 * @param B
	 *            The matrix whose column will be used.
	 * @param C
	 *            The resulting matrix.
	 */
	public WorkerThread(int row, int col, int[][] A, int[][] B, int[][] C) {
		this.row = row;
		this.col = col;
		this.A = A;
		this.B = B;
		this.C = C;
	}

	/**
	 * Performs the multiplication and summation needed from the two matrices
	 * being multiplied together and stores the result in the corresponding slot
	 * in the resulting matrix.
	 */
	public void run() {
		int sum = 0;
		for (int i = 0; i < MainThread.K; i++) {
			sum += A[row][i] * B[i][col];
		}
		C[row][col] = sum;
	}

}
