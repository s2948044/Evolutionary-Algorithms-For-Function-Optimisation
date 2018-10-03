import java.util.*;
public class Configs {


    private final String varChoice = System.getProperty("varChoice");
    // Immutable parameters:
    /**
     * Total number of individuals of each generation.
     */
    private final int populationSize = Integer.parseInt(System.getProperty("population"));

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
     * Covarience matrix for self-apative correalted mutation.
     */
    private double[][] covarienceMatrix;

    /**
     * JSON string creater (For data block)     
     */
    private String jstring;

    private ArrayList<Double> x_data = new ArrayList<Double>();

    private String methods_jstring;

    public String build_methods_jstring(){
        this.methods_jstring = "{ 'methods': {'variables': {'populationsize':" + Integer.toString(getPopulationSize()) +", 'mutationsize':" + Integer.toString(getMutationSize()) +", 'mixingfactor':" + Double.toString(getMixingFactor()) + "} } }";
        return this.methods_jstring;
   }

    public void append_xdata(double x){
        this.x_data.add(x);
    }

    public void make_data_jstring(ArrayList x){
        this.jstring = "{'data' : {'y':[";
        
        for (int i = 0; i < x.size()-1; i++) {
            this.jstring = this.jstring + Double.toString(this.x_data.get(i)) + ",";
        }
        this.jstring = this.jstring + Double.toString(this.x_data.get(x.size()-1))+ "], 'x': [";

        for (int i = 0; i < x.size()-1; i++ ) {
             this.jstring = this.jstring + Integer.toString(i) + ",";
        }
        this.jstring = this.jstring + Integer.toString(x.size()) + "] } }";
    }

    public ArrayList get_xdata(){
        return this.x_data;
    }

    public String get_data_jstring(){
        return this.jstring;
    }



    public void concat_jstring(String json){
        this.jstring = this.jstring + json;
    }

    public Configs() {
    }

    public void initParams() {
        setMutationRate(0.1);
        setMutationSize(1);
        setMutationLearningRate(1 / Math.sqrt(2 * this.dimension));
        setRandomSelected(50);
        setParentSelected(20);
        setMixingFactor(Double.parseDouble(System.getProperty("mixingFactor")));
        setInitSigma(0.05);
        setMutationStepSize(1);
        setMutationStepSizeBound(0.00001);
        setSecondaryMutationLearningRate(1 / Math.sqrt(2 * Math.sqrt(this.dimension)));
        setNdMutationStepSize(new double[] { 0.001, 0.001, 0.001, 0.001, 0.001, 0.001, 0.001, 0.001, 0.001, 0.001 });
        setS_value(1.9);

    }

    public String getVarChoice() {
        return this.varChoice;
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

    public void print_values(double score){
        System.out.println(score);
    }
}
