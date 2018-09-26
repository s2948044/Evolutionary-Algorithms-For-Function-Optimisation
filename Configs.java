public class Configs {

    private final int _populationSize = 100;
    private final int _dimension = 10;
    private final boolean _DEBUG = true;

    private double _mutationRate = 0.1;
    private int _randomSelected = 50;
    private int _parentSelected = 10;
    private double _mixingFactor = 0.3;

    public Configs() {
    }

    public int getpopulationSize() {
        return _populationSize;
    }

    public int getdimension() {
        return _dimension;
    }

    public boolean getDEBUG() {
        return _DEBUG;
    }

    public void setmutationRate(double mutationRate) {
        _mutationRate = mutationRate;
    }

    public double getmutationRate() {
        return _mutationRate;
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

}