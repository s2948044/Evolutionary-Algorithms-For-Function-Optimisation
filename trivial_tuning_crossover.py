import os
import subprocess
import numpy as np
import matplotlib.pyplot as plt
import json
import time

start_time = time.time()

mixingfactors = [0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1]
xoverChoices = [0, 1, 2, 3]
mutationChoices = [0, 1, 2, 3, 4, 5, 6]
# mixingfactors = [0, 0.1]
# varchoices = [0, 1]

evalChoices = [0, 1, 2]
epochs = 100

evaluations = ["BentCigarFunction", "KatsuuraEvaluation", "SchaffersEvaluation"]
xoverNames = ["singleArithmeticCrossOver", "simpleArithmeticCrossOver", "wholeArithmeticCrossOver", "blendCrossOver"]

# makefile (change to your own 'make.exe' command)


def make():
    subprocess.run("mingw32-make")

# run algorithm with parameter values. Returns json string


def runEA(mixingfactor, xoverChoice, mutationChoice, evalChoice):
    result = subprocess.run(['java', '-jar', '-DmixingFactor=' + str(mixingfactor), '-DxoverChoice=' +
                             str(xoverChoice + 1), '-DmutationChoice=' + str(mutationChoice + 1), 'testrun.jar', '-submission=player19', '-evaluation=' + str(evaluations[evalChoice]), '-seed=1'], stdout=subprocess.PIPE)
    jsonstring = result.stdout.decode('utf-8').replace('\'', '"').split('\r')[0]

    return jsonstring

# runs EA with possible parameter tweeks


def main():

    make()

    colors = ['b', 'g', 'r', 'c']
    evalChoice = evalChoices[0]
    mutationChoice = 0

    for xoverChoice in xoverChoices:
        MBF = []
        for mixingfactor in mixingfactors:
            BF = []
            for i in range(epochs):
                # make json object
                js = json.loads(runEA(mixingfactor=mixingfactor,
                                      xoverChoice=xoverChoice, mutationChoice=mutationChoice, evalChoice=evalChoice))
                x = js['data']['x']
                y = js['data']['y']
                BF.append(y)
            MBF.append(np.mean(BF))
            # plot data of algortihm
        plt.plot(mixingfactors, MBF, label=xoverNames[xoverChoice], color=colors[xoverChoice])
    plt.legend()
    plt.xlabel("Mixing Factor")
    plt.ylabel("Score")
    plt.show()


main()
print("--- %s minutes ---" % str((time.time() - start_time) / 60))
