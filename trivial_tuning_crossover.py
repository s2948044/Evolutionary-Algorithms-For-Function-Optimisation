import os
import subprocess
import numpy as np
import matplotlib.pyplot as plt
import json
import time

start_time = time.time()

mixingfactors = [0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1]
varchoices = [0, 1, 2, 3]
# mixingfactors = [0, 0.1]
# varchoices = [0, 1]

evalchoices = [0, 1, 2]
epochs = 100

population = 100
evaluations = ["BentCigarFunction", "KatsuuraEvaluation", "SchaffersEvaluation"]
varNames = ["singleArithmeticCrossOver", "singleArithmeticCrossOver", "wholeArithmeticCrossOver", "blendCrossOver"]

# makefile (change to your own 'make.exe' command)


def make():
    subprocess.run("mingw32-make")

# run algorithm with parameter values. Returns json string


def runEA(population, mixingfactor, varchoice, evalchoice):
    result = subprocess.run(['java', '-jar', '-Dpopulation=' + str(population), '-DmixingFactor=' + str(mixingfactor), '-DvarChoice=' +
                             str(varchoice + 1), 'testrun.jar', '-submission=player19', '-evaluation=' + str(evaluations[evalchoice]), '-seed=1'], stdout=subprocess.PIPE)
    jsonstring = result.stdout.decode('utf-8').replace('\'', '"').split('\r')[0]

    return jsonstring

# runs EA with possible parameter tweeks


def main():

    make()

    colors = ['b', 'g', 'r', 'c']
    evalchoice = evalchoices[0]

    for varchoice in varchoices:
        MBF = []
        for mixingfactor in mixingfactors:
            for i in range(epochs):
                # make json object
                js = json.loads(runEA(population=population, mixingfactor=mixingfactor, varchoice=varchoice, evalchoice=evalchoice))
                x = js['data']['x']
                y = js['data']['y']
            MBF.append(np.mean(y))
            # plot data of algortihm
        plt.plot(mixingfactors, MBF, label=varNames[varchoice] + 'mixf=' + str(mixingfactor), color=colors[varchoice])
    # plt.legend()
    plt.show()


main()
print("--- %s minutes ---" % str((time.time() - start_time) / 60))
