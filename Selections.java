import java.util.*;

public class Selections {
    public Selections() {
    }

    /**
     * Roulette Wheel Selection.
     * 
     * @param fitnessValues Array of fitness values corresponding to the population.
     * @return Index of the selected individual.
     */

    public static void sortbyColumn(double population[][], int colToSort) 
    { 
        // Using built-in sort function Arrays.sort 
        Arrays.sort(population, new Comparator<double[]>() { 
            
          @Override              
          // Compare values according to columns 
          public int compare(final double[] entry1,  
                             final double[] entry2) { 
  
            // To sort in descending order
            if (entry1[colToSort-1] < entry2[colToSort-1]) 
                return 1; 
            else
                return -1; 
          } 
        });
    }

    public int rouletteWheelSelection(double[] fitnessValues) {
        // Sum up the fitnessValues of all the selectees.
        double fitnessSum = 0;
        for (double fitness : fitnessValues) {
            fitnessSum += fitness;
        }

        // Compute the corresponding probabilities.
        double[] probs = new double[fitnessValues.length];
        for (int i = 0; i < probs.length; i++) {
            probs[i] = fitnessValues[i] / fitnessSum;
        }

        // Init a random probability and return its corresponding index.
        double prob = new Random().nextDouble();
        double probSum = 0;
        int i = 0;
        while (prob - probSum > 0) {
            probSum += probs[i];
            i++;
        }
        return i - 1;
    }
    
    public double[][] survSelection(double[][] population, int intensionSize){
		sortbyColumn(population, population[0].length); //sort the current matrix of the population based on the fitness stored at the last column
		double[][] new_population = new double[intensionSize][population[0].length]; //create a new matrix for storing only the top numbers of individuals
		for(int i = 0; i < intensionSize; i++){
			for(int j = 0; j < population[0].length; j++){
				new_population[i][j] = population[i][j]; //copy from the old matrix to the new matrix
			}
		}
		return new_population;
	}
}
