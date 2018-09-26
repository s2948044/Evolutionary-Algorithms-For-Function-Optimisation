public class Configs {

    private final int populationSize = 100;
    private final int dimension = 10;
    private final int addtionalDimension = 1;
    private final boolean DEBUG = true;
    private final int lowerBound = -5;
    private final int upperBound = 5;

    private double mutationRate = 0.1;
    private int mutationSize = 1;
    private int randomSelected = 50;
    private int parentSelected = 20;
    private double mixingFactor = 0.3;
    private double initSigma = 2;
    private double mutationStepSize = 1;

    public Configs() {
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

}