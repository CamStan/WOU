import java.util.ArrayList;
import java.util.Arrays;

/**
 * CS262 Final Project
 * Calculates the penny to $1 change in coins for all the input amounts. 
 * 
 * @author Cameron Stanavige
 * @version 12/7/2015
 */
public class GiveChange {

	private final int[] DENOMINATIONS = { 100, 50, 25, 10, 5, 1 };

	// calculate the change for the given amount
	public void processChange(String amount) {
		ArrayList<Integer> change = new ArrayList<Integer>();
		change = calculateChange(change, 0, readAmount(amount));

		if (verifyAnswer(readAmount(amount), change))
			printChange(amount, change);
		else
			System.out.println("Change for " + amount + " was incorrect.");
	}

	// recursive helper function to calculate change
	private ArrayList<Integer> calculateChange(ArrayList<Integer> change, int ds,
			int amount) {
		if (amount <= 0)
			return change;
		int d = DENOMINATIONS[ds];
		if (amount >= d) {
			change.add(d);
			calculateChange(change, ds, amount - d);
		} else {
			calculateChange(change, ds + 1, amount);
		}
		return change;
	}

	// turn each String amount into an int
	private int readAmount(String c) {
		return Math.round(Float.parseFloat(c) * 100);
	}

	// verify the calculated change adds up to the input amount
	private boolean verifyAnswer(int amount, ArrayList<Integer> answer) {
		return amount == answer.stream().mapToInt(c -> c).sum();
	}

	// print the change to the console
	private void printChange(String amount, ArrayList<Integer> change) {
		System.out.println("(\"" + amount + "\", " + change + ")");
	}

	public static void main(String[] args) {
        if (args.length < 1)
            System.out.println("ERROR: Enter dollar amounts in the format \"java GiveChange 4.62 9.17 2.74 0.48 0.00 0.2 0.99\"");
        else {
            GiveChange gc = new GiveChange();
            Arrays.asList(args).forEach(e -> gc.processChange(e));
        }
	}
} // end class
