public class Configs {
    // Immutable parameters:
    /**
     * Total number of individuals of each generation.
     */
    private final int populationSize = 100;

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
     * Rate of occurence for mutation for each child.
     */
    private double mutationRate;

    /**
     * Number of dimensions to mutate during mutation.
     */
    private int mutationSize;

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

    public Configs() {
    }

    public void initParams() {
        setMutationRate(0.1);
        setMutationSize(1);
        setMutationLearningRate(0.001 * 1 / Math.sqrt(2 * this.dimension));
        setRandomSelected(50);
        setParentSelected(20);
        setMixingFactor(0.7);
        setInitSigma(2);
        setMutationStepSize(1);
        setMutationStepSizeBound(0.00001);
        setSecondaryMutationLearningRate(0.001 * 1 / Math.sqrt(2 * Math.sqrt(this.dimension)));
        setNdMutationStepSize(new double[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
        setS_value(1.9);
        setCorrelationFactors(new double[this.dimension * (this.dimension - 1) / 2]);
        initCovarianceMatrix();
        setCovarianceMatrix(this.ndMutationStepSize, this.correlationFactors);
        setCorrelationAngle(5 * Math.PI / 180);

    }

    public int getPopulationSize() {
        return this.populationSize;
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
                    this.covarienceMatrix[i][j] = correlationFactors[indCounter];
                    this.covarienceMatrix[j][i] = correlationFactors[indCounter];
                    indCounter++;
                }
            }
        }
    }

    public double getCorrelationAngle() {
        return this.correlationAngle;
    }

    public void setCorrelationAngle(double correlationAngle) {
        this.correlationAngle = correlationAngle;
    }

}
