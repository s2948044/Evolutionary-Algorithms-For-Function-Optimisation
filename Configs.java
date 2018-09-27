public class Configs {

    private final int populationSize = 100;
    private final int dimension = 10;
    private final int addtionalDimension = 1;
    private final boolean DEBUG = true;
    private final int lowerBound = -5;
    private final int upperBound = 5;

    private double mutationRate;
    private int mutationSize;
    private double mutationLearningRate;
    private int randomSelected;
    private int parentSelected;
    private double mixingFactor;
    private double initSigma;
    private double mutationStepSize;
    private double mutationStepSizeBound;
    private double[] ndMutationStepSize;
    private double secondaryMutationLearningRate;

    public Configs() {
    }

    public void initParams() {
        setMutationRate(0.1);
        setMutationSize(1);
        setMutationLearningRate(1 / Math.sqrt(2 * this.dimension));
        setRandomSelected(50);
        setParentSelected(20);
        setMixingFactor(0.3);
        setInitSigma(0.05);
        setMutationStepSize(1);
        setMutationStepSizeBound(0.00001);
        setSecondaryMutationLearningRate(1 / Math.sqrt(2 * Math.sqrt(this.dimension)));
        setNdMutationStepSize(new double[] { 0.001, 0.001, 0.001, 0.001, 0.001, 0.001, 0.001, 0.001, 0.001, 0.001 });
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

}
