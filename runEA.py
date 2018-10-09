import os
import subprocess
import numpy as np
import matplotlib.pyplot as plt
import json
import time

# measure duration of algortithme
start_time = time.time()

# Crossover variation numbers
xoverVariations = [1, 2, 3, 4]
mutationvariations = [1, 2, 3, 4, 5, 6, 7]

# population chang
# populations = [100]*5
mixingfactors = [0.8, 0.7, 0.6, .5, .4, .3]
xoverNames = ["singleArithmeticCrossOver", "simpleArithmeticCrossOver", "wholeArithmeticCrossOver", "blendCrossOver"]

# makefile (change to your own 'make.exe' command)


def make():
    subprocess.run("mingw32-make")

# run algorithm with parameter values. Returns json string


def runEA(population, mixingfactor, xoverChoice, mutationChoice):
    result = subprocess.run(['java', '-jar', '-Dpopulation=' + str(population), '-DmixingFactor=' + str(mixingfactor), '-DxoverChoice=' +
                             str(xoverChoice), '-DmutationChoice=' + str(mutationChoice), 'testrun.jar', '-submission=player19', '-evaluation=SchaffersEvaluation', '-seed=1'], stdout=subprocess.PIPE)
    jsonstring = result.stdout.decode('utf-8').replace('\'', '"').split('\r')[0]

    return jsonstring

# runs EA with possible parameter tweeks


def main():

    make()

    population = 20

    colors = ['b', 'g', 'r', 'c']

    count = 0

    # all crossovers
    for i in range(4):

        # get crossover
        xoverChoice = xoverVariations[i]
        mutationChoice = 1

        # repeat 30 times with different variables
        for j in range(1):
            mixingfactor = .8

            # make json object
            js = json.loads(runEA(population, mixingfactor, xoverChoice, mutationChoice))
            x = js['data']['x']
            y = js['data']['y']

            # plot data of algortihm
            plt.plot(x, y, label=xoverNames[i] + 'mixf=' + str(mixingfactor), color=colors[i])

            count += 1
            print(count)
    # plt.legend()
    plt.show()


main()
print("--- %s minutes ---" % (time.time() - start_time) / 60)
