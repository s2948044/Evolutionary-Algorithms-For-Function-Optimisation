import numpy as np
import time
import math
import random
import pandas as pd
import os
from meta_ea import cma_es, individual


start_time = time.time()

xoverChoices = [1, 2]
mutationChoices = [4, 5, 6]
evalChoices = [0, 1, 2]


def main():
    ea = cma_es()
    ea.compile(make="mingw32-make")  # Set your make.
    # Set ea parameters.
    ea.dimension = 3
    ea.evalChoice = 1  # 0 for BentCigar, 1 for Katsuura, 2 for Schaffers
    if ea.evalChoice == 1:
        # Katsuura
        ea.epochs = 5
    else:
        ea.epochs = 10
    ea.gens_limit = 20

    ea.lambda_ea = 4 + math.floor(3 * math.log(ea.dimension))
    ea.mu = math.floor(float(ea.lambda_ea) / 2)
    # Initialize population.
    population = []
    sum_w_mu = 0.0
    sum_w_mu_2 = 0.0
    sum_w_lambda = 0.0
    sum_w_lambda_2 = 0.0
    sum_pos_w = 0.0
    sum_neg_w = 0.0
    for i in range(ea.lambda_ea):
        population.append(individual(ea.dimension))
        population[i].w = math.log(float(ea.lambda_ea + 1) / 2) - math.log(i + 1)
        if (i < ea.mu):
            sum_w_mu += population[i].w
            sum_w_mu_2 += population[i].w ** 2
        else:
            sum_w_lambda += population[i].w
            sum_w_lambda_2 += population[i].w ** 2

        if (population[i].w) > 0:
            sum_pos_w += population[i].w
        else:
            sum_neg_w += population[i].w

    # for i in range(ea.lambda_ea):
    #     population.append(individual(ea.dimension))
    #     if (i < ea.mu):
    #         population[i].w = math.log(float(ea.lambda_ea + 1) / 2) - math.log(i + 1)
    #         sum_w_mu += population[i].w

    # for i in range(ea.mu):
    #     population[i].w = population[i].w / sum_w_mu
    #     sum_w_mu_2 += population[i].w ** 2

    # Set default strategy parameters.
    ea.mu_eff = sum_w_mu ** 2 / sum_w_mu_2
    # ea.mu_eff = 1 / sum_w_mu_2
    mu_eff_n = sum_w_lambda ** 2 / sum_w_lambda_2
    ea.c_sigma = (ea.mu_eff + 2.0) / (float(ea.dimension) + ea.mu_eff + 5.0)
    ea.d_sigma = 1.0 + 2 * max(0, math.sqrt((ea.mu_eff - 1) / (ea.dimension + 1)) - 1) + ea.c_sigma
    ea.cc = (4.0 + ea.mu_eff / ea.dimension) / (ea.dimension + 4 + 2.0 * ea.mu_eff / ea.dimension)
    alpha_cov = 2.0
    ea.c1 = alpha_cov / ((ea.dimension + 1.3) ** 2 + ea.mu_eff)
    ea.c_mu = min(1 - ea.c1, alpha_cov * (ea.mu_eff - 2 + 1 / ea.mu_eff) / ((ea.dimension + 2.0) ** 2) + alpha_cov * ea.mu_eff / 2)
    ea.c_m = 1.0
    # Additional modifications on the weights.
    alpha_mu_n = 1 + ea.c1 / ea.c_mu
    alpha_mu_eff_n = 1 + (2 * mu_eff_n) / (ea.mu_eff + 2)
    alpha_pos_def_n = (1 - ea.c1 - ea.c_mu) / (ea.dimension * ea.c_mu)
    for i in range(ea.lambda_ea):
        if population[i].w >= 0:
            population[i].w = 1 / (sum_pos_w) * population[i].w
        else:
            population[i].w = min(alpha_mu_n, alpha_mu_eff_n, alpha_pos_def_n) / (-sum_neg_w) * population[i].w

    # Initialization.
    ea.p_sigma = np.zeros(shape=[ea.dimension, ], dtype=np.float32)
    ea.p_c = np.zeros(shape=[ea.dimension, ], dtype=np.float32)
    ea.covarianceMatrix = np.eye(N=ea.dimension)
    ea.evals = 0
    ea.gens = 0
    # ea.m = np.full(shape=[ea.dimension, ], fill_value=(1 / float(ea.dimension)), dtype=np.float32)
    ea.m = np.empty(shape=[ea.dimension, ], dtype=np.float32)
    for i in range(ea.dimension):
        ea.m[i] = random.random()
    ea.sigma = 0.3
    ea.expectation_N = math.sqrt(ea.dimension) * (1 - 1 / (4.0 * ea.dimension) + 1 / (21.0 * ea.dimension ** 2))

    BF_gen = []
    while (ea.gens < ea.gens_limit):
        # Sample new population of search points.
        ea.D = np.zeros(shape=[ea.dimension, ea.dimension], dtype=np.float32)
        ea.covarianceMatrix = np.triu(ea.covarianceMatrix) + np.transpose(np.triu(ea.covarianceMatrix, 1))
        eig_vals, ea.B = np.linalg.eig(ea.covarianceMatrix)

        if (np.min(eig_vals) < 1e-6) or (np.max(eig_vals) > 1e6 * np.min(eig_vals)):
            break

        for i in range(ea.dimension):
            ea.D[i, i] = math.sqrt(eig_vals[i])

        for k in range(ea.lambda_ea):
            population[k].z = np.random.multivariate_normal(np.zeros(shape=[ea.dimension, ], dtype=np.float32), np.eye(ea.dimension))
            population[k].y = np.dot(np.dot(ea.B, ea.D), population[k].z)
            population[k].x = ea.m + ea.sigma * population[k].y

            # For debugging.
            evaluation_time = time.time()
            population[k].fitness = ea.evaluation(ea.geno_to_pheno(population[k].x))
            print("Finished evaluating solution: " + str(population[k]))
            print("Time elapsed: {}s".format(time.time() - evaluation_time))

            if population[k].fitness > ea.bestScore:
                ea.bestScore = population[k].fitness
                ea.bestSolution = population[k].x

        # Selection and recombination.
        population.sort(key=lambda x: x.fitness, reverse=True)
        weighted_sum_y = np.zeros(shape=[ea.dimension, ], dtype=np.float32)
        for i in range(ea.mu):
            weighted_sum_y = weighted_sum_y + population[i].w * population[i].y
        ea.m = ea.m + ea.c_m * ea.sigma * weighted_sum_y

        # Step-size control.
        inv_D = np.zeros(shape=[ea.dimension, ea.dimension], dtype=np.float32)
        for i in range(ea.dimension):
            inv_D[i, i] = 1 / ea.D[i, i]

        inv_sqrt_C = np.dot(np.dot(ea.B, inv_D), np.transpose(ea.B))
        ea.p_sigma = (1 - ea.c_sigma) * ea.p_sigma + math.sqrt(ea.c_sigma * (2 - ea.c_sigma) * ea.mu_eff) * np.dot(inv_sqrt_C, weighted_sum_y)
        ea.sigma = ea.sigma * math.exp(ea.c_sigma / ea.d_sigma * (np.linalg.norm(ea.p_sigma) / ea.expectation_N - 1))

        # Covariance matrix adaptation.
        if ((np.linalg.norm(ea.p_sigma) / (math.sqrt(1 - (1 - ea.c_sigma) ** (2 * (ea.gens + 1))))) < ((1.4 + 2.0 / (ea.dimension + 1)) * ea.expectation_N)):
            ea.h_sigma = 1
        else:
            ea.h_sigma = 0

        ea.p_c = (1 - ea.cc) * ea.p_c + ea.h_sigma * math.sqrt(ea.cc * (2 - ea.cc) * ea.mu_eff) * weighted_sum_y
        sum_w_mu = 0.0
        for j in range(ea.mu):
            sum_w_mu += population[j].w
        tmp_sum = 0.0
        for i in range(ea.lambda_ea):
            if (population[i].w < 0):
                population[i].w = ea.dimension / (np.norm(np.dot(inv_sqrt_C, population[i].y)) ** 2)
            tmp_sum += population[i].w * np.dot(population[i].y, np.transpose(population[i].y))
        delta_h_sigma = (1 - ea.h_sigma) * ea.cc * (2 - ea.cc)
        ea.covarianceMatrix = (1 + ea.c1 * delta_h_sigma - ea.c1 - ea.c_mu * sum_w_mu) * ea.covarianceMatrix + \
            ea.c1 * np.dot(ea.p_c, np.transpose(ea.p_c)) + ea.c_mu * tmp_sum
        ea.normalizeBestSolution()
        print("Best score at generation {} : {}".format(ea.gens, ea.bestScore))
        print("Best solution: {}".format(ea.bestSolution))
        BF_gen.append([ea.gens, ('%f' % ea.bestSolution[0]), ('%f' % ea.bestSolution[1]), ('%f' % ea.bestSolution[2]), ('%f' % ea.bestScore)])
        ea.gens += 1

    ea.normalizeBestSolution()
    print("Best score: {}".format(ea.bestScore))
    print("Best solution: {}".format(ea.bestSolution))

    if ea.evalChoice == 0:
        filenames = os.listdir("./meta_results/BentCigar/")
        savenum = int(filenames[-1].split('-')[1][0:3])
        savenum += 1

        my_df = pd.DataFrame(BF_gen)
        my_df.to_csv('meta_results/BentCigar/output-'+str("%03d" % savenum)+'.csv', index=False, header=['generation', 'P_comb1', 'P_comb2', 'P_comb3', 'MBF'])
    elif ea.evalChoice == 1:
        filenames = os.listdir("./meta_results/Katsuura/")
        savenum = int(filenames[-1].split('-')[1][0:3])
        savenum += 1

        my_df = pd.DataFrame(BF_gen)
        my_df.to_csv('meta_results/Katsuura/output-'+str("%03d" % savenum)+'.csv', index=False, header=['generation', 'P_comb1', 'P_comb2', 'P_comb3', 'MBF'])
    else:
        filenames = os.listdir("./meta_results/Schaffers/")
        savenum = int(filenames[-1].split('-')[1][0:3])
        savenum += 1

        my_df = pd.DataFrame(BF_gen)
        my_df.to_csv('meta_results/Schaffers/output-'+str("%03d" % savenum)+'.csv', index=False, header=['generation', 'P_comb1', 'P_comb2', 'P_comb3', 'MBF'])


if __name__ == "__main__":
    main()
    print("--- %s minutes ---" % str((time.time() - start_time) / 60))
