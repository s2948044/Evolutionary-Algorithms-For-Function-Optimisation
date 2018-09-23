import java.util.*;

public class Variations {
    public Variations() {


    }
	
	public static void rnd_swap(double[] arr){

			// take 2 idx numbers between 0 and 9
			int idx1 = new Random().nextInt(9);
			int idx2 = new Random().nextInt(9);

			// check for uniqueness else change
			while(idx1 == idx2){
				idx2 = new Random().nextInt(9);
			}

			// switch numbers
			double temp = arr[idx1];
			arr[idx1] = arr[idx2];
			arr[idx2] = temp;

	}

}
