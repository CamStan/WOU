import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * GUI for the Matrix Multiplication program. Allows for the custom input of
 * matrix dimensions and then the input of the individual values in each matrix.
 * Then displays the result of the multiplication of the two matrices.
 * 
 * @author Cameron Stanavige
 * @version 5/31/2015
 *
 */
public class MatrixGUI extends JFrame {

	private MainThread mt;

	private JPanel contentPane;

	private JPanel matrixAPanel;
	private JPanel matrixBPanel;
	private JPanel matrixCPanel;

	// grid panels to hold the individual matrices
	private JPanel matrixA;
	private JPanel matrixB;
	private JPanel matrixC;

	// matrix arrays
	private JTextField[][] mA;
	private JTextField[][] mB;
	private JTextField[][] mC;

	// dimensions of the matrices
	private int m;
	private int k;
	private int n;

	/**
	 * Constructor for this GUI. Takes a handle from the MainThread class using
	 * this gui, creates the overall content pane and then launches the pop up
	 * windows for the input of matrix dimensions.
	 * 
	 * @param mt
	 *            The MainThread class that instantiated this gui.
	 */
	public MatrixGUI(MainThread mt) {
		super("Matrix Multiplication");
		this.mt = mt;

		makeContentPane();

		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.pack();
		this.setVisible(true);

		firstInputPanel();
		secondInputPanel();
	}

	/**
	 * Private Method: Creates the overall content pane with the input and
	 * output panels.
	 */
	private void makeContentPane() {
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel inputPanel = createInputPanel();
		contentPane.add(inputPanel, BorderLayout.WEST);

		JPanel solutionPanel = createSolutionPanel();
		contentPane.add(solutionPanel, BorderLayout.EAST);
	}

	/**
	 * Private Method: Creates the input panel for the two matrices being
	 * multiplied together.
	 * 
	 * @return The JPanel for the input area.
	 */
	private JPanel createInputPanel() {
		JPanel inputPanel = new JPanel(new BorderLayout(0, 0));

		JPanel matrixInputPanel = new JPanel();
		matrixInputPanel.setLayout(new BoxLayout(matrixInputPanel,
				BoxLayout.X_AXIS));
		inputPanel.add(matrixInputPanel, BorderLayout.NORTH);

		// matrix A left bracket
		JLabel aLeftB = new JLabel("[");
		matrixInputPanel.add(aLeftB);
		aLeftB.setHorizontalAlignment(SwingConstants.RIGHT);
		aLeftB.setFont(new Font("Calibri Light", Font.PLAIN, 125));

		// panel to hold matrix A
		matrixAPanel = new JPanel();
		matrixAPanel.setLayout(new BorderLayout(0, 0));
		matrixA = new JPanel();
		matrixAPanel.add(matrixA);
		matrixInputPanel.add(matrixAPanel);

		// matrix A right bracket
		JLabel aRightB = new JLabel("]");
		aRightB.setHorizontalAlignment(SwingConstants.LEFT);
		aRightB.setFont(new Font("Calibri Light", Font.PLAIN, 125));
		matrixInputPanel.add(aRightB);

		// multiplication symbol
		JLabel xLabel = new JLabel("x");
		matrixInputPanel.add(xLabel);
		xLabel.setHorizontalAlignment(SwingConstants.CENTER);
		xLabel.setFont(new Font("Calibri Light", Font.PLAIN, 55));

		// matrix B left bracket
		JLabel bLeftB = new JLabel("[");
		matrixInputPanel.add(bLeftB);
		bLeftB.setHorizontalAlignment(SwingConstants.RIGHT);
		bLeftB.setFont(new Font("Calibri Light", Font.PLAIN, 125));

		// panel to hold matrix B
		matrixBPanel = new JPanel();
		matrixBPanel.setLayout(new BorderLayout(0, 0));
		matrixB = new JPanel();
		matrixBPanel.add(matrixB);
		matrixInputPanel.add(matrixBPanel);

		// matrix B right bracket
		JLabel bRightB = new JLabel("]");
		matrixInputPanel.add(bRightB);
		bRightB.setHorizontalAlignment(SwingConstants.LEFT);
		bRightB.setFont(new Font("Calibri Light", Font.PLAIN, 125));

		// equal sign
		JLabel eLabel = new JLabel("=");
		eLabel.setHorizontalAlignment(SwingConstants.CENTER);
		eLabel.setFont(new Font("Calibri Light", Font.PLAIN, 55));
		matrixInputPanel.add(eLabel);

		JPanel processPanel = new JPanel();
		inputPanel.add(processPanel, BorderLayout.SOUTH);
		processPanel.setBorder(new LineBorder(Color.BLACK));

		// process button
		JButton processButton = new JButton("Process");
		processButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processPushed();
			}
		});
		processPanel.add(processButton);

		return inputPanel;
	}

	/**
	 * Private Method: Creates the solution panel for the output of the
	 * resulting matrix
	 * 
	 * @return The JPanel for the solution panel.
	 */
	private JPanel createSolutionPanel() {
		JPanel solutionPanel = new JPanel(new BorderLayout(0, 0));

		JPanel matrixSolutionPanel = new JPanel();
		matrixSolutionPanel.setLayout(new BoxLayout(matrixSolutionPanel,
				BoxLayout.X_AXIS));
		solutionPanel.add(matrixSolutionPanel, BorderLayout.NORTH);

		// matrix C left bracket
		JLabel cLeftB = new JLabel("[");
		cLeftB.setHorizontalAlignment(SwingConstants.RIGHT);
		cLeftB.setFont(new Font("Calibri Light", Font.PLAIN, 125));
		matrixSolutionPanel.add(cLeftB);

		// panel to hold matrix C
		matrixCPanel = new JPanel();
		matrixCPanel.setLayout(new BorderLayout(0, 0));
		matrixC = new JPanel();
		matrixCPanel.add(matrixC);
		matrixSolutionPanel.add(matrixCPanel);

		// matrix C right bracket
		JLabel cRightB = new JLabel("]");
		cRightB.setHorizontalAlignment(SwingConstants.LEFT);
		cRightB.setFont(new Font("Calibri Light", Font.PLAIN, 125));
		matrixSolutionPanel.add(cRightB);

		JPanel resetPanel = new JPanel();
		solutionPanel.add(resetPanel, BorderLayout.SOUTH);
		resetPanel.setBorder(new LineBorder(Color.BLACK));

		// reset button
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetPressed();
			}
		});
		resetPanel.add(resetButton);

		return solutionPanel;
	}

	/**
	 * Private Method: Creates the pop up pane for inputing the the dimensions
	 * for matrix A.
	 */
	private void firstInputPanel() {
		JPanel firstMatrixPanel = new JPanel(new BorderLayout());

		// title label
		JLabel title = new JLabel("First Matrix Dimensions",
				SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.PLAIN, 18));
		title.setBorder(new LineBorder(Color.BLACK));
		firstMatrixPanel.add(title, BorderLayout.NORTH);

		JPanel bottomPanel = new JPanel(new BorderLayout());

		// labels for input text fields
		JPanel labels = new JPanel(new GridLayout(1, 2, 0, 0));
		labels.add(new JLabel("Rows", SwingConstants.CENTER));
		labels.add(new JLabel("Columns", SwingConstants.CENTER));

		bottomPanel.add(labels, BorderLayout.NORTH);

		// text fields to input dimensions
		JPanel inputs = new JPanel(new GridLayout(1, 5, 3, 3));
		inputs.add(new JLabel(""));
		JTextField firstRow = new JTextField("3");
		firstRow.setFont(new Font("Tahoma", Font.PLAIN, 25));
		firstRow.setHorizontalAlignment(SwingConstants.CENTER);
		firstRow.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				firstRow.selectAll();
			}

			public void focusLost(FocusEvent e) {
			}
		});
		inputs.add(firstRow);

		inputs.add(new JLabel(""));
		inputs.add(new JLabel(""));

		JTextField firstColumn = new JTextField("2");
		firstColumn.setFont(new Font("Tahoma", Font.PLAIN, 25));
		firstColumn.setHorizontalAlignment(SwingConstants.CENTER);
		firstColumn.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				firstColumn.selectAll();
			}

			public void focusLost(FocusEvent e) {
			}
		});
		inputs.add(firstColumn);
		inputs.add(new JLabel(""));

		bottomPanel.add(inputs, BorderLayout.SOUTH);
		firstMatrixPanel.add(bottomPanel, BorderLayout.SOUTH);

		// option pane
		int result = JOptionPane.showConfirmDialog(this, firstMatrixPanel,
				"First Matrix Dimensions", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			try {
				m = Integer.parseInt(firstRow.getText());
				k = Integer.parseInt(firstColumn.getText());
			} catch (NumberFormatException e) { // catch non number values
				JOptionPane.showMessageDialog(this,
						"Please enter a valid integer.");
				firstInputPanel();
			}
		}
	}

	/**
	 * Private Method: Creates the pop up pane for inputing the dimensions for
	 * matrix B.
	 */
	private void secondInputPanel() {
		JPanel secondMatrixPanel = new JPanel(new BorderLayout());

		// title label
		JLabel title = new JLabel("Second Matrix Dimensions",
				SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.PLAIN, 18));
		title.setBorder(new LineBorder(Color.BLACK));
		secondMatrixPanel.add(title, BorderLayout.NORTH);

		JPanel bottomPanel = new JPanel(new BorderLayout());

		// labels for input text fields
		JPanel labels = new JPanel(new GridLayout(1, 2, 0, 0));
		labels.add(new JLabel("Rows", SwingConstants.CENTER));
		labels.add(new JLabel("Columns", SwingConstants.CENTER));

		bottomPanel.add(labels, BorderLayout.NORTH);

		// label indicating dimension of rows on matrix B
		JPanel inputs = new JPanel(new GridLayout(1, 5, 3, 3));
		inputs.add(new JLabel(""));
		JLabel secondRow = (new JLabel("" + k, SwingConstants.CENTER));
		secondRow.setFont(new Font("Tahoma", Font.PLAIN, 25));
		inputs.add(secondRow);

		inputs.add(new JLabel(""));
		inputs.add(new JLabel(""));

		// input field for column dimensions
		JTextField secondColumn = new JTextField("3");
		secondColumn.setFont(new Font("Tahoma", Font.PLAIN, 25));
		secondColumn.setHorizontalAlignment(SwingConstants.CENTER);
		secondColumn.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				secondColumn.selectAll();
			}

			public void focusLost(FocusEvent e) {
			}
		});
		inputs.add(secondColumn);
		inputs.add(new JLabel(""));

		bottomPanel.add(inputs, BorderLayout.SOUTH);
		secondMatrixPanel.add(bottomPanel, BorderLayout.SOUTH);

		// option pane
		int result = JOptionPane.showConfirmDialog(this, secondMatrixPanel,
				"Second Matrix Dimensions", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			try {
				n = Integer.parseInt(secondColumn.getText());
				mt.initialize(m, k, n);
				constructMatrices();
			} catch (NumberFormatException e) { // catch non number values
				JOptionPane.showMessageDialog(this,
						"Please enter a valid integer.");
				secondInputPanel();
			}
		}
	}

	/**
	 * Private Method: Constructs the two input matrices and solution matrix
	 * using the dimensions input by the use from the option panes.
	 */
	private void constructMatrices() {
		// first matrix
		matrixAPanel.remove(matrixA);
		matrixA = new JPanel(new GridLayout(m, k, 5, 5));
		mA = new JTextField[m][k];
		for (int i = 0; i < m; i++) { // row
			int a = i;
			for (int j = 0; j < k; j++) { // column
				int b = j;
				JTextField cur = mA[i][j];
				cur = new JTextField(" __ ");
				cur.setFont(new Font("Tahoma", Font.PLAIN, 25));
				cur.setHorizontalAlignment(SwingConstants.CENTER);
				matrixA.add(cur);
				mA[i][j] = cur;
				cur.addFocusListener(new FocusListener() {
					public void focusGained(FocusEvent e) {
						mA[a][b].selectAll();
					}

					public void focusLost(FocusEvent e) {
					}
				});
				cur.addKeyListener(new KeyListener() {
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER)
							processPushed();
					}

					public void keyTyped(KeyEvent e) {
					}

					public void keyReleased(KeyEvent e) {
					}
				});
			}
		}
		matrixAPanel.add(matrixA);

		// second matrix
		matrixBPanel.remove(matrixB);
		matrixB = new JPanel(new GridLayout(k, n, 5, 5));
		mB = new JTextField[k][n];
		for (int i = 0; i < k; i++) { // row
			int a = i;
			for (int j = 0; j < n; j++) { // column
				int b = j;
				JTextField cur = mB[i][j];
				cur = new JTextField(" __ ");
				cur.setFont(new Font("Tahoma", Font.PLAIN, 25));
				cur.setHorizontalAlignment(SwingConstants.CENTER);
				matrixB.add(cur);
				mB[i][j] = cur;
				cur.addFocusListener(new FocusListener() {
					public void focusGained(FocusEvent e) {
						mB[a][b].selectAll();
					}

					public void focusLost(FocusEvent e) {
					}
				});
				cur.addKeyListener(new KeyListener() {
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER)
							processPushed();
					}

					public void keyTyped(KeyEvent e) {
					}

					public void keyReleased(KeyEvent e) {
					}
				});
			}
		}
		matrixBPanel.add(matrixB);

		// solution matrix
		matrixCPanel.remove(matrixC);
		matrixC = new JPanel(new GridLayout(m, n, 5, 5));
		mC = new JTextField[m][n];
		for (int i = 0; i < m; i++) { // row
			for (int j = 0; j < n; j++) { // column
				JTextField cur = mC[i][j];
				cur = new JTextField("_");
				cur.setFont(new Font("Tahoma", Font.PLAIN, 25));
				cur.setHorizontalAlignment(SwingConstants.CENTER);
				cur.setEditable(false);
				matrixC.add(cur);
				mC[i][j] = cur;
			}
		}
		matrixCPanel.add(matrixC);
		this.pack();
		this.revalidate();
		mA[0][0].requestFocus();
	}

	/**
	 * Private Method: Action listener for the process button. Builds two
	 * matrices using the input values and sends them to be multiplied.
	 */
	private void processPushed() {
		try {
			int[][] aMatrix = new int[m][k];
			int[][] bMatrix = new int[k][n];
			// aMatrix
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < k; j++) {
					aMatrix[i][j] = Integer.parseInt(mA[i][j].getText());
				}
			}
			// bMatrix
			for (int i = 0; i < k; i++) {
				for (int j = 0; j < n; j++) {
					bMatrix[i][j] = Integer.parseInt(mB[i][j].getText());
				}
			}
			mt.process(aMatrix, bMatrix);
		} catch (NumberFormatException e) { // catch empty and non number values
			JOptionPane.showMessageDialog(this,
					"Please ensure all entries are valid integers.",
					"Process Failed", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Private Method: Action Listener for reset button. Simply allows the user
	 * to enter new dimensions to creates new matrices.
	 */
	private void resetPressed() {
		firstInputPanel();
		secondInputPanel();
	}

	/**
	 * Takes the incoming solution matrix and prints its values to the gui.
	 * 
	 * @param result
	 *            The solution matrix to be displayed.
	 */
	public void printResult(int[][] result) {
		for (int i = 0; i < m; i++) { // row
			for (int j = 0; j < n; j++) { // column
				mC[i][j].setText("" + result[i][j]);
			}
		}
		this.pack();
		this.revalidate();
	}
} // end class
