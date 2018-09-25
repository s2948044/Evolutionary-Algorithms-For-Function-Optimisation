import java.util.*;

public class Variations {
	public Variations() {

	}

	/**
	 * Random swap mutation operator.
	 * 
	 * @param individual
	 */
	public void rnd_swap(double[] individual) {

		// take 2 idx numbers between 0 and 9
		int idx1 = new Random().nextInt(9);
		int idx2 = new Random().nextInt(9);

		// check for uniqueness else change
		while (idx1 == idx2) {
			idx2 = new Random().nextInt(9);
		}

		// switch numbers
		double temp = individual[idx1];
		individual[idx1] = individual[idx2];
		individual[idx2] = temp;

	}

}
