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
        self.bestScore = 0.0
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

    # @property
    # def lambda_ea(self):
    #     return self._lambda

    # @lambda_ea.setter
    # def lambda_ea(self, value):
    #     self._lambda = value

    # @property
    # def mu(self):
    #     return self._mu

    # @mu.setter
    # def mu(self, value):
    #     self._mu = value

    # @property
    # def mu_eff(self):
    #     return self._mu_eff

    # @mu_eff.setter
    # def mu_eff(self, value):
    #     self._mu_eff = value

    # @property
    # def c_sigma(self):
    #     return self._c_sigma

    # @c_sigma.setter
    # def c_sigma(self, value):
    #     self._c_sigma = value

    # @property
    # def d_sigma(self):
    #     return self._d_sigma

    # @d_sigma.setter
    # def d_sigma(self, value):
    #     self._d_sigma = value

    # @property
    # def cc(self):
    #     return self._cc

    # @cc.setter
    # def cc(self, value):
    #     self._cc = value

    # @property
    # def c1(self):
    #     return self._c1

    # @c1.setter
    # def c1(self, value):
    #     self._c1 = value

    # @property
    # def c_mu(self):
    #     return self._c_mu

    # @c_mu.setter
    # def c_mu(self, value):
    #     self._c_mu = value

    # @property
    # def c_m(self):
    #     return self._c_m

    # @c_m.setter
    # def c_m(self, value):
    #     self._c_m = value

    # @property
    # def evaluations(self):
    #     return self._evaluations

    # @property
    # def dimension(self):
    #     return self._dimension

    # @dimension.setter
    # def dimension(self, value):
    #     self._dimension = value

    # @property
    # def epochs(self):
    #     return self._epochs

    # @epochs.setter
    # def epochs(self, value):
    #     self._epochs = value

    # @property
    # def evalChoice(self):
    #     return self._evalChoice

    # @evalChoice.setter
    # def evalChoice(self, value):
    #     self._evalChoice = value

    # @property
    # def covarianceMatrix(self):
    #     return self._covarianceMatrix

    # @covarianceMatrix.setter
    # def covarianceMatrix(self, value):
    #     self._covarianceMatrix = value

    # @property
    # def m(self):
    #     return self._m

    # @m.setter
    # def m(self, value):
    #     self._m = value

    # @property
    # def bestScore(self):
    #     return self._bestScore

    # @bestScore.setter
    # def bestScore(self, value):
    #     self._bestScore = value

    # @property
    # def bestSolution(self):
    #     return self._bestSolution

    # @bestSolution.setter
    # def bestSolution(self, value):
    #     self._bestSolution = value

    # @property
    # def gens(self):
    #     return self._gens

    # @gens.setter
    # def gens(self, value):
    #     self._gens = value

    # @property
    # def gens_limit(self):
    #     return self._gens_limit

    # @gens_limit.setter
    # def gens_limit(self, value):
    #     self._gens_limit = value

    # @property
    # def evals(self):
    #     return self._evals

    # @evals.setter
    # def evals(self, value):
    #     self._evals = value

    # @property
    # def evals_limit(self):
    #     return self._evals_limit

    # @evals_limit.setter
    # def evals_limit(self, value):
    #     self._evals_limit = value

    # @property
    # def p_sigma(self):
    #     return self._p_sigma

    # @p_sigma.setter
    # def p_sigma(self, value):
    #     self._p_sigma = value

    # @property
    # def p_c(self):
    #     return self._p_c

    # @p_c.setter
    # def p_c(self, value):
    #     self._p_c = value

    # @property
    # def sigma(self):
    #     return self._sigma

    # @sigma.setter
    # def sigma(self, value):
    #     self._sigma = value

    # @property
    # def B(self):
    #     return self._B

    # @B.setter
    # def B(self, value):
    #     self._B = value

    # @property
    # def D(self):
    #     return self._D

    # @D.setter
    # def D(self, value):
    #     self._D = value

    # @property
    # def h_sigma(self):
    #     return self._h_sigma

    # @h_sigma.setter
    # def h_sigma(self, value):
    #     self._h_sigma = value

    # @property
    # def expectation_N(self):
    #     return self._expection_N

    # @expectation_N.setter
    # def expectation_N(self, value):
    #     self._expection_N = value

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

    # @property
    # def x(self):
    #     return self._x

    # @x.setter
    # def x(self, value):
    #     self._x = value

    # @property
    # def y(self):
    #     return self._y

    # @y.setter
    # def y(self, value):
    #     self._y = value

    # @property
    # def z(self):
    #     return self._z

    # @z.setter
    # def z(self, value):
    #     self._z = value

    # @property
    # def w(self):
    #     return self._w

    # @w.setter
    # def w(self, value):
    #     self._w = value

    # @property
    # def fitness(self):
    #     return self._fitness

    # @fitness.setter
    # def fitness(self, value):
    #     self._fitness = value
