import java.util.*;

public class Configs {

    // Immutable parameters:
    /**
     * Dimension of the solution vector to the problem. (1 x N)
     */
    private final int dimension = 10;

    /**
     * Additional dimention to the solution vector to encode different values.
     */
    private final int addtionalDimension = 1;

    /**
     * Public flag for debugging.
     */
    private final boolean DEBUG = true;

    /**
     * Lower bound of domain for each element in the solution vector.
     */
    private final int lowerBound = -5;

    /**
     * Upper bound of domain for each element in the solution vector.
     */
    private final int upperBound = 5;

    // Mutable parameters:
    /**
     * Choice for the crossover method.
     */
    private int xoverChoice;

    /**
     * Choice for the mutation method.
     */
    private int mutationChoice;

    /**
     * Total number of individuals of each generation.
     */
    private int populationSize;

    /**
     * Size of Tournament.
     */
    private int tournamentSize;

    /**
     * Rate of occurence for mutation for each child.
     */
    private double mutationRate;

    /**
     * Number of dimensions to mutate during mutation.
     */
    private int mutationSize;

    /**
     * Learning rate for single uncorrelated self-adaptive mutation methods.
     */
    private double singleMutationLearningRate;

    /**
     * Overall learning rate for self-adaptive mutation methods.
     */
    private double mutationLearningRate;

    /**
     * Number of randomly selected candidates for parent selections based on
     * Elitism.
     */
    private int randomSelected;

    /**
     * Number of parents selected for reproduction.
     */
    private int parentSelected;

    /**
     * Mixing factor for some mutation methods(e.g. single/simple/whole arithmetic
     * mutation and blend crossover).
     */
    private double mixingFactor;

    /**
     * Standard deviation during initialization of the population.
     */
    private double initSigma;

    /**
     * Standard deviation of mutation change.
     */
    private double mutationStepSize;

    /**
     * Lower bound for mutation step size to avoid tending to 0.
     */
    private double mutationStepSizeBound;

    /**
     * Multidimensional mutation step size corresponding to each dimeansion in the
     * solution vector.
     */
    private double[] ndMutationStepSize;

    /**
     * Coordinate wise learning rate for self-adaptive mutations.
     */
    private double secondaryMutationLearningRate;

    /**
     * A factor to measure advantage of best individual (set from 1.0 ~ 2.0)
     */
    private double s_value;

    /**
     * Factors indicating the correlation between each dimensions in the solution
     * vector.
     */
    private double[] correlationFactors;

    /**
     * Covarience matrix for self-apative correlated mutation.
     */
    private double[][] covarienceMatrix;

    /**
     * Correlation angle for self-adaptive correlated mutation.
     */
    private double correlationAngle;

    /**
     * JSON string creater (For data block)
     */
    private String jstring;

    private ArrayList<Double> x_data = new ArrayList<Double>();

    /**
     * Mean best fitness data for statistics.
     */
    private ArrayList<Double> mbf_data = new ArrayList<Double>();

    private String methods_jstring;

    public String build_methods_jstring() {
        this.methods_jstring = "{ 'methods': {'variables': {'populationsize':" + Integer.toString(getPopulationSize())
                + ", 'mutationsize':" + Integer.toString(getMutationSize()) + ", 'mixingfactor':"
                + Double.toString(getMixingFactor()) + "}, 'crossover':" + Integer.toString(this.xoverChoice) + " }, ";
        return this.methods_jstring;
    }

    public void append_mbfdata(double mbf) {
        this.mbf_data.add(mbf);
    }

    public double return_mbfdata_3() {
        return this.mbf_data.get(this.x_data.size() - 3);
    }

    public void append_xdata(double x) {
        this.x_data.add(x);
    }

    public double return_xdata_3() {
        return this.x_data.get(this.x_data.size() - 3);
    }

    public void make_data_jstring(ArrayList x) {
        this.jstring = "'data' : {'y':[";

        for (int i = 0; i < x.size() - 1; i++) {
            this.jstring = this.jstring + Double.toString(this.x_data.get(i)) + ",";
        }
        this.jstring = this.jstring + Double.toString(this.x_data.get(x.size() - 1)) + "], 'x': [";

        for (int i = 0; i < x.size() - 1; i++) {
            this.jstring = this.jstring + Integer.toString(i) + ",";
        }
        this.jstring = this.jstring + Integer.toString(x.size()) + "] } }";

        this.methods_jstring = build_methods_jstring();
        concat_jstring(this.methods_jstring);
    }

    public ArrayList get_mbfdata() {
        return this.mbf_data;
    }

    public ArrayList get_xdata() {
        return this.x_data;
    }

    public String get_data_jstring() {
        return this.jstring;
    }

    public void concat_jstring(String json) {
        this.jstring = json + this.jstring;
    }

    public Configs() {
    }

    public void initParams() {
        setXoverChoice(Integer.parseInt(System.getProperty("xoverChoice")));
        setMutationChoice(Integer.parseInt(System.getProperty("mutationChoice")));
        // setPopulationSize(Integer.parseInt(System.getProperty("population")));
        // setMutationRate(0.1); // should be in range [populationSize, 0.1].
        setMutationSize(1);
        // setTournamentSize(100);
        setSingleMutationLearningRate(0.01 * (1 / Math.sqrt(this.dimension))); // the 0.01 can be manipulated.
        setMutationLearningRate(0.003 * (1 / Math.sqrt(2 * this.dimension))); // the 0.01 can be manipulated.
        setRandomSelected(50); // should be less than populationSize.
        // setParentSelected(20); // should be less than RandomSelected.
        setMixingFactor(0.5); // should be in range (0, 1).
        setInitSigma(2);
        setMutationStepSize(1);
        setMutationStepSizeBound(0.001);
        setSecondaryMutationLearningRate(0.007 * (1 / Math.sqrt(2 * Math.sqrt(this.dimension)))); // the 0.01 can be
                                                                                                  // manipulated.
        setNdMutationStepSize(new double[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
        setS_value(1.9); // should be in range (0, 2].
        setCorrelationFactors(new double[this.dimension * (this.dimension - 1) / 2]);
        initCovarianceMatrix();
        setCovarianceMatrix(this.ndMutationStepSize, this.correlationFactors);
        setCorrelationAngle(Math.toRadians(5));
    }

    public int getXoverChoice() {
        return this.xoverChoice;
    }

    public void setXoverChoice(int xoverChoice) {
        this.xoverChoice = xoverChoice;
    }

    public int getMutationChoice() {
        return this.mutationChoice;
    }

    public void setMutationChoice(int mutationChoice) {
        this.mutationChoice = mutationChoice;
    }

    public int getPopulationSize() {
        return this.populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getDimension() {
        return this.dimension;
    }

    public int getAdditionalDimension() {
        return this.addtionalDimension;
    }

    public boolean getDEBUG() {
        return this.DEBUG;
    }

    public int getLowerBound() {
        return this.lowerBound;
    }

    public int getUpperBound() {
        return this.upperBound;
    }

    public int getTournamentSize() {
        return this.tournamentSize;
    }

    public void setTournamentSize(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    public double getMutationRate() {
        return this.mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public int getMutationSize() {
        return this.mutationSize;
    }

    public void setMutationSize(int mutationSize) {
        this.mutationSize = mutationSize;
    }

    public int getRandomSelected() {
        return this.randomSelected;
    }

    public void setRandomSelected(int randomSelected) {
        this.randomSelected = randomSelected;
    }

    public int getParentSelected() {
        return this.parentSelected;
    }

    public void setParentSelected(int parentSelected) {
        this.parentSelected = parentSelected;
    }

    public double getMixingFactor() {
        return this.mixingFactor;
    }

    public void setMixingFactor(double mixingFactor) {
        this.mixingFactor = mixingFactor;
    }

    public double getInitSigma() {
        return this.initSigma;
    }

    public void setInitSigma(double initSigma) {
        this.initSigma = initSigma;
    }

    public double getMutationStepSize() {
        return this.mutationStepSize;
    }

    public void setMutationStepSize(double mutationStepSize) {
        this.mutationStepSize = mutationStepSize;
    }

    public double getSingleMutationLearningRate() {
        return this.singleMutationLearningRate;
    }

    public void setSingleMutationLearningRate(double singleMutationLearningRate) {
        this.singleMutationLearningRate = singleMutationLearningRate;
    }

    public double getMutationLearningRate() {
        return this.mutationLearningRate;
    }

    public void setMutationLearningRate(double mutationLearningRate) {
        this.mutationLearningRate = mutationLearningRate;
    }

    public double getMutationStepSizeBound() {
        return this.mutationStepSizeBound;
    }

    public void setMutationStepSizeBound(double mutationStepSizeBound) {
        this.mutationStepSizeBound = mutationStepSizeBound;
    }

    public double[] getNdMutationStepSize() {
        return this.ndMutationStepSize;
    }

    public void setNdMutationStepSize(double[] ndMutationStepSize) {
        this.ndMutationStepSize = ndMutationStepSize;
    }

    public double getSecondaryMutationLearningRate() {
        return this.secondaryMutationLearningRate;
    }

    public void setSecondaryMutationLearningRate(double secondaryMutationLearningRate) {
        this.secondaryMutationLearningRate = secondaryMutationLearningRate;
    }

    public double getS_value() {
        return this.s_value;
    }

    public void setS_value(double s_value) {
        this.s_value = s_value;
    }

    public void print_values(double score) {
        System.out.println(score);
    }

    public double[] getCorrelationFactors() {
        return this.correlationFactors;
    }

    public void setCorrelationFactors(double[] correlationFactors) {
        this.correlationFactors = correlationFactors;
    }

    public void initCovarianceMatrix() {
        this.covarienceMatrix = new double[this.dimension][this.dimension];
    }

    public double[][] getCovarienceMatrix() {
        return this.covarienceMatrix;
    }

    public void setCovarianceMatrix(double[] ndMutationStepSize, double[] correlationFactors) {
        int indCounter = 0;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = i; j < this.dimension; j++) {
                if (j == i) {
                    this.covarienceMatrix[i][j] = ndMutationStepSize[i] * ndMutationStepSize[i];
                } else {
                    this.covarienceMatrix[i][j] = 0.5
                            * (ndMutationStepSize[i] * ndMutationStepSize[i]
                                    - ndMutationStepSize[j] * ndMutationStepSize[j])
                            * Math.tan(2 * correlationFactors[indCounter]);
                    this.covarienceMatrix[j][i] = 0.5
                            * (ndMutationStepSize[j] * ndMutationStepSize[j]
                                    - ndMutationStepSize[i] * ndMutationStepSize[i])
                            * Math.tan(2 * correlationFactors[indCounter]);
                    indCounter++;
                }
            }
        }
        // System.out.println(Arrays.deepToString(covarienceMatrix));
    }

    public double getCorrelationAngle() {
        return this.correlationAngle;
    }

    public void setCorrelationAngle(double correlationAngle) {
        this.correlationAngle = correlationAngle;
    }

}
