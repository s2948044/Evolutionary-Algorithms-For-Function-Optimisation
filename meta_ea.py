import subprocess
import json
import numpy as np


class cma_es:

    def __init__(self, make="mingw32-make"):
        self.evaluations = ["BentCigarFunction", "KatsuuraEvaluation", "SchaffersEvaluation"]
        self.make = make
        self.lambda_ea = 0
        self.mu = 0.0
        self.mu_eff = 0.0
        self.c_sigma = 0.0
        self.d_sigma = 0.0
        self.cc = 0.0
        self.c1 = 0.0
        self.c_mu = 0.0
        self.c_m = 0
        self.dimension = 0
        self.epochs = 0
        self.evalChoice = 0
        self.covarianceMatrix = np.empty(shape=self.dimension, dtype=np.float32)
        self.m = np.empty(shape=[self.dimension, ], dtype=np.float32)
        self.bestScore = -1.0
        self.bestSolution = np.empty(shape=[self.dimension, ], dtype=np.float32)
        self.gens = 0
        self.gens_limit = 0
        self.evals = 0
        self.evals_limit = 0
        self.p_c = np.empty(shape=[self.dimension, ], dtype=np.float32)
        self.p_sigma = np.empty(shape=[self.dimension, ], dtype=np.float32)
        self.sigma = 0.0
        self.B = np.empty(shape=self.dimension, dtype=np.float32)
        self.D = np.empty(shape=self.dimension, dtype=np.float32)
        self.h_sigma = 0
        self.expectation_N = 0.0

    def normalizeBestSolution(self):
        probSum = 0.0
        for i in range(self.bestSolution.shape[0]):
            probSum += abs(self.bestSolution[i])

        for i in range(self.bestSolution.shape[0]):
            self.bestSolution[i] = abs(self.bestSolution[i]) / probSum

    def compile(self):
        subprocess.run(self.make)

    def run_lower_ea(self, probs):
        result = subprocess.run(['java', '-jar', '-DProbs=' + ','.join(map(str, probs)), 'testrun.jar', '-submission=player19',
                                 '-evaluation=' + self.evaluations[self.evalChoice], '-seed=1'], stdout=subprocess.PIPE)
        jsonstring = result.stdout.decode('utf-8').replace('\'', '"').split('\r')[0]
        return jsonstring

    def evaluation(self, probs):
        BFs = []
        for i in range(self.epochs):
            js = json.loads(self.run_lower_ea(probs))
            BFs.append(js['data']['bf'])
        return np.mean(BFs)

    # TODO: Check whether the mapping is appropriate.
    def geno_to_pheno(self, solution):
        probs = []
        prob_sum = 0.0
        for i in range(self.dimension):
            prob_sum += abs(solution[i])

        for i in range(self.dimension):
            probs.append(abs(solution[i]) / prob_sum)
        return probs


class individual:

    def __init__(self, dimension):
        self.x = np.empty(shape=[dimension, ], dtype=np.float32)
        self.y = np.empty(shape=[dimension, ], dtype=np.float32)
        self.z = np.empty(shape=[dimension, ], dtype=np.float32)
        self.w = 0.0
        self.fitness = 0.0

    def __str__(self):
        return str(self.normalizeSolution()) + ", fitness: {}".format(self.fitness)

    def normalizeSolution(self):
        probSum = 0.0
        normalized = []
        for i in range(self.x.shape[0]):
            probSum += abs(self.x[i])

        for i in range(self.x.shape[0]):
            normalized.append(abs(self.x[i]) / probSum)
        return normalized
