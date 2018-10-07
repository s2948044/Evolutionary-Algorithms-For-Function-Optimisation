import java.util.*;
import umontreal.ssj.rng.*;
import umontreal.ssj.randvar.*;
import umontreal.ssj.randvarmulti.*;
import cern.colt.matrix.*;
// import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
// import org.apache.commons.math3.random.JDKRandomGenerator;
// import org.apache.commons.math3.random.RandomGenerator;

public class Variations {
	private Configs cfgs;

	public Variations(Configs cfgs) {
		this.cfgs = cfgs;
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

	/**
	 * Uniform mutation operator.
	 * 
	 * @param individual
	 */
	public void uniformMutation(double[] individual) {
		for (int i = 0; i < this.cfgs.getDimension(); i++) {
			if (new Random().nextInt((int) (1 / this.cfgs.getMutationRate())) == 0) {
				individual[i] = new Random().nextDouble() * (this.cfgs.getUpperBound() - this.cfgs.getLowerBound())
						+ this.cfgs.getLowerBound();
			}
		}
	}

	/**
	 * Non-uniform mutation operator.
	 * 
	 * @param individual
	 */
	public void nonUniformMutation(double[] individual) {
		for (int i = 0; i < this.cfgs.getDimension(); i++) {
			individual[i] = individual[i] + new Random().nextGaussian() * this.cfgs.getMutationStepSize();
		}
	}

	/**
	 * Older version of non-uniform mutation operator(wrondly implmented).
	 * 
	 * @param individual
	 */
	public void customizedMutation(double[] individual) {
		int[] mutationInds = new int[this.cfgs.getMutationSize()];
		for (int i = 0; i < this.cfgs.getMutationSize(); i++) {
			mutationInds[i] = new Random().nextInt(this.cfgs.getDimension());
			if (i != 0) {
				boolean overlapping = true;
				while (overlapping) {
					overlapping = false;
					for (int j = 0; j < i; j++) {
						if (mutationInds[i] == mutationInds[j]) {
							mutationInds[i] = new Random().nextInt(this.cfgs.getDimension());
							overlapping = true;
							break;
						}
					}
				}
			}
		}
		for (int mutationInd : mutationInds) {
			individual[mutationInd] = individual[mutationInd]
					+ new Random().nextGaussian() * this.cfgs.getMutationStepSize();
		}
	}

	/**
	 * Self-adaptive uncorrelated mutation with single mutation step size.
	 * 
	 * @param individual
	 */
	public void singleUncorrelatedMutation(double[] individual) {
		this.cfgs.setMutationStepSize(this.cfgs.getMutationStepSize()
				* Math.exp(this.cfgs.getMutationLearningRate() * new Random().nextGaussian()));
		if (this.cfgs.getMutationStepSize() < this.cfgs.getMutationStepSizeBound()) {
			this.cfgs.setMutationStepSize(this.cfgs.getMutationStepSizeBound());
		}
		for (int i = 0; i < this.cfgs.getDimension(); i++) {
			individual[i] = individual[i] + this.cfgs.getMutationStepSize() * new Random().nextGaussian();
		}
	}

	/**
	 * Self-adaptive uncorrelated mutation with multiple mutation step sizes.
	 * 
	 * @param individual
	 */
	public void multiUncorrelatedMutation(double[] individual) {
		double[] ndMutationStepSize = this.cfgs.getNdMutationStepSize();
		double overallNormalDist = new Random().nextGaussian();
		for (int i = 0; i < this.cfgs.getDimension(); i++) {
			ndMutationStepSize[i] = ndMutationStepSize[i]
					* Math.exp(this.cfgs.getMutationLearningRate() * overallNormalDist
							+ this.cfgs.getSecondaryMutationLearningRate() * new Random().nextGaussian());
			if (ndMutationStepSize[i] < this.cfgs.getMutationStepSizeBound()) {
				ndMutationStepSize[i] = this.cfgs.getMutationStepSizeBound();
			}
			individual[i] = individual[i] + ndMutationStepSize[i] * new Random().nextGaussian();
		}
		// System.out.println(Arrays.toString(cfgs.getNdMutationStepSize()));
	}

	public void correlatedMutation(double[] individual) {
		double[] ndMutationStepSize = this.cfgs.getNdMutationStepSize();
		double[] correlationFactors = this.cfgs.getCorrelationFactors();
		double overallNormalDist = new Random().nextGaussian();
		double[] means = new double[this.cfgs.getDimension()];

		for (int i = 0; i < this.cfgs.getDimension(); i++) {
			ndMutationStepSize[i] = ndMutationStepSize[i]
					* Math.exp(this.cfgs.getMutationLearningRate() * overallNormalDist
							+ this.cfgs.getSecondaryMutationLearningRate() * new Random().nextGaussian());
			if (ndMutationStepSize[i] < this.cfgs.getMutationStepSizeBound()) {
				ndMutationStepSize[i] = this.cfgs.getMutationStepSizeBound();
			}
		}

		// System.out.println(Arrays.toString(ndMutationStepSize));

		for (int i = 0; i < this.cfgs.getDimension() * (this.cfgs.getDimension() - 1) / 2; i++) {
			correlationFactors[i] = correlationFactors[i] + this.cfgs.getCorrelationAngle() * overallNormalDist;
			if (Math.abs(correlationFactors[i]) > Math.PI) {
				correlationFactors[i] = correlationFactors[i] - 2 * Math.PI * Math.signum(correlationFactors[i]);
			}
		}

		this.cfgs.setCovarianceMatrix(ndMutationStepSize, correlationFactors);
		// DoubleMatrix2D covarianceMatrix = new
		// DoubleFactory2D.make(this.cfgs.getCovarienceMatrix());
		RandomStream stream = new MRG31k3p();
		NormalGen generator1 = new NormalGen(stream);
		// MultinormalGen generator2 = new MultinormalGen(generator1, means,
		// covarianceMatrix);
		// MultinormalGen generator2 = new MultinormalGen(generator1, means,
		// this.cfgs.getCovarienceMatrix());
		MultinormalCholeskyGen generator2 = new MultinormalCholeskyGen(generator1, means,
				this.cfgs.getCovarienceMatrix());
		double[] tmp = new double[this.cfgs.getDimension()];
		generator2.nextPoint(tmp);
		// System.out.println(Arrays.toString(tmp));
		// RandomGenerator rng = new JDKRandomGenerator();
		// MultivariateNormalDistribution sampler = new
		// MultivariateNormalDistribution(rng, means, covarianceMatrix);
		// double[] tmp = sampler.sample();
		for (int i = 0; i < this.cfgs.getDimension(); i++) {
			individual[i] = individual[i] + tmp[i];
		}

	}

	/**
	 * Single arithmetic Crossover operator.
	 * 
	 * @param population
	 * @param parent1
	 * @param parent2
	 * @return Renewed population with the children.
	 */
	public double[][] singleArithmeticCrossOver(double[][] population, double[] parent1, double[] parent2) {
		double[][] renewedPopulation = new double[population.length + 2][population[0].length];
		double[] child1 = new double[population[0].length];
		double[] child2 = new double[population[0].length];

		int k = new Random().nextInt(this.cfgs.getDimension());
		// System.out.println(k);
		// System.out.println(this.cfgs.getDimension());
		for (int i = 0; i < k; i++) {
			child1[i] = parent1[i];
			child2[i] = parent2[i];
		}

		child1[k] = this.cfgs.getMixingFactor() * parent2[k] + (1 - this.cfgs.getMixingFactor()) * parent1[k];
		child2[k] = this.cfgs.getMixingFactor() * parent1[k] + (1 - this.cfgs.getMixingFactor()) * parent2[k];

		for (int i = k + 1; i < this.cfgs.getDimension(); i++) {
			child1[i] = parent1[i];
			child2[i] = parent2[i];
		}

		for (int i = 0; i < population.length; i++) {
			renewedPopulation[i] = population[i];
		}
		renewedPopulation[population.length] = child1;
		renewedPopulation[population.length + 1] = child2;

		return renewedPopulation;
	}

	/**
	 * Simple arithmetic Crossover operator.
	 * 
	 * @param population
	 * @param parent1
	 * @param parent2
	 * @return Renewed population with the children.
	 */
	public double[][] simpleArithmeticCrossOver(double[][] population, double[] parent1, double[] parent2) {
		double[][] renewedPopulation = new double[population.length + 2][population[0].length];
		double[] child1 = new double[population[0].length];
		double[] child2 = new double[population[0].length];

		int k = new Random().nextInt(this.cfgs.getDimension());
		for (int i = 0; i < k; i++) {
			child1[i] = parent1[i];
			child2[i] = parent2[i];
		}

		for (int i = k; i < this.cfgs.getDimension(); i++) {
			child1[i] = this.cfgs.getMixingFactor() * parent2[i] + (1 - this.cfgs.getMixingFactor()) * parent1[i];
			child2[i] = this.cfgs.getMixingFactor() * parent1[i] + (1 - this.cfgs.getMixingFactor()) * parent2[i];
		}

		for (int i = 0; i < population.length; i++) {
			renewedPopulation[i] = population[i];
		}
		renewedPopulation[population.length] = child1;
		renewedPopulation[population.length + 1] = child2;

		return renewedPopulation;
	}

	/**
	 * Whole arithmetic Crossover operator.
	 * 
	 * @param population
	 * @param parent1
	 * @param parent2
	 * @return Renewed population with the children.
	 */
	public double[][] wholeArithmeticCrossOver(double[][] population, double[] parent1, double[] parent2) {
		double[][] renewedPopulation = new double[population.length + 2][population[0].length];
		double[] child1 = new double[population[0].length];
		double[] child2 = new double[population[0].length];

		for (int i = 0; i < this.cfgs.getDimension(); i++) {
			child1[i] = this.cfgs.getMixingFactor() * parent2[i] + (1 - this.cfgs.getMixingFactor()) * parent1[i];
			child2[i] = this.cfgs.getMixingFactor() * parent1[i] + (1 - this.cfgs.getMixingFactor()) * parent2[i];
		}

		for (int i = 0; i < population.length; i++) {
			renewedPopulation[i] = population[i];
		}
		renewedPopulation[population.length] = child1;
		renewedPopulation[population.length + 1] = child2;

		return renewedPopulation;
	}

	/**
	 * Blend Crossover operator.
	 * 
	 * @param population
	 * @param parent1
	 * @param parent2
	 * @return Renewed population with the children.
	 */
	public double[][] blendCrossOver(double[][] population, double[] parent1, double[] parent2) {
		double[][] renewedPopulation = new double[population.length + 2][population[0].length];
		double[] child1 = new double[population[0].length];
		double[] child2 = new double[population[0].length];

		for (int i = 0; i < this.cfgs.getDimension(); i++) {
			double d = Math.abs(parent1[i] - parent2[i]);
			child1[i] = (Math.min(parent1[i], parent2[i]) - this.cfgs.getMixingFactor() * d)
					+ new Random().nextDouble() * ((Math.min(parent1[i], parent2[i]) + this.cfgs.getMixingFactor() * d)
							- (Math.min(parent1[i], parent2[i]) - this.cfgs.getMixingFactor() * d));

			child2[i] = (Math.min(parent1[i], parent2[i]) - this.cfgs.getMixingFactor() * d)
					+ new Random().nextDouble() * ((Math.min(parent1[i], parent2[i]) + this.cfgs.getMixingFactor() * d)
							- (Math.min(parent1[i], parent2[i]) - this.cfgs.getMixingFactor() * d));
		}

		for (int i = 0; i < population.length; i++) {
			renewedPopulation[i] = population[i];
		}
		renewedPopulation[population.length] = child1;
		renewedPopulation[population.length + 1] = child2;

		return renewedPopulation;
	}

	/**
	 * Order-one Crossover operator.
	 * 
	 * @param population
	 * @param parent1
	 * @param parent2
	 * @return Renewed population with the children.
	 */
	public double[][] order1CrossOver(double[][] population, double[] parent1, double[] parent2) {
		double[][] renewedPopulation = new double[population.length + 2][population[0].length];
		double[] child1 = new double[population[0].length];
		double[] child2 = new double[population[0].length];

		int startInd = new Random().nextInt(this.cfgs.getDimension());
		int endInd = new Random().nextInt(this.cfgs.getDimension() - startInd) + startInd;

		for (int i = 0; i < startInd; i++) {
			child1[i] = parent2[i];
			child2[i] = parent1[i];
		}

		for (int i = startInd; i < endInd; i++) {
			child1[i] = parent1[i];
			child2[i] = parent2[i];
		}

		for (int i = endInd; i < this.cfgs.getDimension(); i++) {
			child1[i] = parent2[i];
			child2[i] = parent1[i];
		}

		for (int i = 0; i < population.length; i++) {
			renewedPopulation[i] = population[i];
		}
		renewedPopulation[population.length] = child1;
		renewedPopulation[population.length + 1] = child2;
		return renewedPopulation;
	}

}
