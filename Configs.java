public class Configs {

    private final int _populationSize = 100;
    private final int _dimension = 10;
    private final int _addtionalDimension = 1;
    private final boolean _DEBUG = true;
    private final int _lowerBound = -5;
    private final int _upperBound = 5;

    private double _mutationRate = 0.1;
    private int _mutationSize = 1;
    private int _randomSelected = 50;
    private int _parentSelected = 20;
    private double _mixingFactor = 0.3;
    private double _initSigma = 2;
    private double _mutationStepSize = 1;

    public Configs() {
    }

    public int getpopulationSize() {
        return _populationSize;
    }

    public int getdimension() {
        return _dimension;
    }

    public int getadditionalDimension() {
        return _addtionalDimension;
    }

    public boolean getDEBUG() {
        return _DEBUG;
    }

    public int getlowerBound() {
        return _lowerBound;
    }

    public int getupperBound() {
        return _upperBound;
    }

    public void setmutationRate(double mutationRate) {
        _mutationRate = mutationRate;
    }

    public double getmutationRate() {
        return _mutationRate;
    }

    public void setmutationSize(int mutationSize) {
        _mutationSize = mutationSize;
    }

    public int getmutationSize() {
        return _mutationSize;
    }

    public void setrandomSelected(int randomSelected) {
        _randomSelected = randomSelected;
    }

    public int getrandomSelected() {
        return _randomSelected;
    }

    public void setparentSelected(int parentSelected) {
        _parentSelected = parentSelected;
    }

    public int getparentSelected() {
        return _parentSelected;
    }

    public void setmixingFactor(double mixingFactor) {
        _mixingFactor = mixingFactor;
    }

    public double getmixingFactor() {
        return _mixingFactor;
    }

    public void setinitSigma(double initSigma) {
        _initSigma = initSigma;
    }

    public double getinitSigma() {
        return _initSigma;
    }

    public void setmutationStepSize(double mutationStepSize) {
        _mutationStepSize = mutationStepSize;
    }

    public double getmutationStepSize() {
        return _mutationStepSize;
    }

}