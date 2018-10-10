import os
import subprocess
import numpy as np
import matplotlib.pyplot as plt
import json
import time

start_time = time.time()

mixs = [0, 0.25, 0.5, 0.75, 1]
p1s = [0.01, 0.05, 0.1]
p2s = [0.01, 0.05, 0.1]
p3s = [0.01, 0.05, 0.1]
# xoverChoices = [0, 1, 2, 3]
xoverChoices = [1, 2]

# mutationChoices = [0, 1, 2, 3, 4, 5, 6]
mutationChoices = [4, 5, 6]

# mix = [0, 0.1]
# varchoices = [0, 1]

evalChoices = [0, 1, 2]
epochs = 1

evaluations = ["BentCigarFunction", "KatsuuraEvaluation", "SchaffersEvaluation"]
xoverNames = ["singleArithmeticCrossOver", "simpleArithmeticCrossOver", "wholeArithmeticCrossOver", "blendCrossOver"]
mutationNames = ["rnd_swap", "uniformMutation", "nonUniformMutation", "customizedMutation", "singleUncorrelatedMutation", "multiUncorrelatedMutation", "correlatedMutation"]

# makefile (change to your own 'make.exe' command)


def make():
    subprocess.run("makeme")

# run algorithm with parameter values. Returns json string


def runEA(mixingfactor, xoverChoice, mutationChoice, evalChoice):
    result = subprocess.run(['java', '-jar', '-DmixingFactor=' + str(mixingfactor), '-DxoverChoice=' +
                             str(xoverChoice + 1), '-DmutationChoice=' + str(mutationChoice + 1), 'testrun.jar', '-submission=player19', '-evaluation=' + str(evaluations[evalChoice]), '-seed=1'], stdout=subprocess.PIPE)
    jsonstring = result.stdout.decode('utf-8').replace('\'', '"').split('\r')[0]

    return jsonstring

# runs EA with possible parameter tweeks


def main():

    make()


    MBF = []


    colors = ['b', 'g', 'r', 'c']
    evalChoice = evalChoices[0]
    # mutationChoice = 0

    for xoverChoice in xoverChoices:
        for mutationChoice in mutationChoices:
            for mix in mixs:

                if mutationChoice == 4:
                    for p1 in p1s:  
                        BF = []
                        for i in range(epochs):
                            # make json object
                            js = json.loads(runEA(mixingfactor=mix, xoverChoice=xoverChoice, mutationChoice=mutationChoice, evalChoice=evalChoice))
                            y = js['data']['y']
                            BF.append(y)
                        MBF.append([np.mean(BF), xoverChoice, mutationChoice, mix, p1, "-", "-"])


                else:
                    MBF = []
                    for p2 in p2s:
                        for p3 in p3s:
                            BF = []
                            for i in range(epochs):
                                # make json object
                                js = json.loads(runEA(mixingfactor=mix, xoverChoice=xoverChoice, mutationChoice=mutationChoice, evalChoice=evalChoice))
                                y = js['data']['y']
                                BF.append(y)
                            MBF.append([np.mean(BF), xoverChoice, mutationChoice, mix, "-", p2, p3])


                print(MBF)
                break
            break
        break
            
            # plot data of algortihm
        # plt.plot(mix, MBF, label=xoverNames[xoverChoice], color=colors[xoverChoice])
    # plt.legend()
    # plt.xlabel("Mixing Factor")
    # plt.ylabel("Score")
    # plt.show()


main()
print("--- %s minutes ---" % str((time.time() - start_time) / 60))
