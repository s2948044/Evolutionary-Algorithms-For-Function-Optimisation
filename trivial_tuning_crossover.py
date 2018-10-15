import os
import subprocess
import numpy as np
import matplotlib.pyplot as plt
import json
import time
import pandas as pd
import glob

start_time = time.time()

mixs = [0.5, 0.55, 0.6, 0.65, 0.7]
# p1s = [0.01, 0.05, 0.1]
p2s = [0.1, 0.3, 0.5, 0.7]
p3s = [0.1, 0.3, 0.5, 0.7]
# xoverChoices = [0, 1, 2, 3]
xoverChoices = [2]

# mutationChoices = [0, 1, 2, 3, 4, 5, 6]
mutationChoices = [6]

# mix = [0, 0.1]
# varchoices = [0, 1]

evalChoices = [0, 1, 2]
epochs = 7

evaluations = ["BentCigarFunction", "KatsuuraEvaluation", "SchaffersEvaluation"]
xoverNames = ["singleArithmeticCrossOver", "simpleArithmeticCrossOver", "wholeArithmeticCrossOver", "blendCrossOver"]
mutationNames = ["rnd_swap", "uniformMutation", "nonUniformMutation", "customizedMutation",
                 "singleUncorrelatedMutation", "multiUncorrelatedMutation", "correlatedMutation"]

# makefile (change to your own 'make.exe' command)


def make():
    subprocess.run("makeme")

# run algorithm with parameter values. Returns json string


def runEA(mixingFactor, xoverChoice, mutationChoice, evalChoice, SingleMC, OverallMC, SecondaryMC):
    result = subprocess.run(['java', '-jar', '-DmixingFactor=' + str(mixingFactor), '-DxoverChoice=' +
                             str(xoverChoice + 1), '-DmutationChoice=' + str(mutationChoice + 1), '-DSingleMC='+str(SingleMC), '-DOverallMC='+str(OverallMC), '-DSecondaryMC='+str(SecondaryMC), 'testrun.jar', '-submission=player19', '-evaluation=' + str(evaluations[evalChoice]), '-seed=1'], stdout=subprocess.PIPE)
    jsonstring = result.stdout.decode('utf-8').replace('\'', '"').split('\r')[0]

    return jsonstring

# runs EA with possible parameter tweeks


def main():

    make()

    MBF = []
    colors = ['b', 'g', 'r', 'c']
    evalChoice = evalChoices[2]
    # mutationChoice = 0

    for xoverChoice in xoverChoices:
        for mutationChoice in mutationChoices:
            print(xoverNames[xoverChoice] + ", "+mutationNames[mutationChoice])
            for mix in mixs:
                for p2 in p2s:
                    for p3 in p3s:
                        BF = []
                        for i in range(epochs):
                            # make json object
                            print("mix:", mix , "xoverChoice:", xoverChoice,  "Overall:" ,  p2 , "Secondary:" , p3  , "run:" , i + 1)
                            js = json.loads(runEA(mixingFactor=mix, xoverChoice=xoverChoice, mutationChoice=mutationChoice,
                                                  evalChoice=evalChoice, SingleMC=0.0, OverallMC=p2, SecondaryMC=p3))
                            print("epochs loop")
                            # y = js['data']['y']
                            y = js['data']['bf']
                            BF.append(y)
                        MBF.append([('%f' % np.mean(BF)), xoverChoice, mutationChoice, mix, "-", p2, p3])
                        print("p3 loop")
                    print("p2 loop")
                print("mix loop")

    filenames = os.listdir("./csv")
    # print(filenames)
    savenum = int(filenames[-1].split('-')[1][0:3])
    savenum += 1

    my_df = pd.DataFrame(MBF)
    my_df.to_csv('csv/output-'+str("%03d" % savenum)+'.csv', index=False, header=['MBF', 'xover', 'mutation', 'mixingFactor', 'p1', 'p2', 'p3'])
    # plot data of algortihm
    # plt.plot(mix, MBF, label=xoverNames[xoverChoice], color=colors[xoverChoice])
    # plt.legend()
    # plt.xlabel("Mixing Factor")
    # plt.ylabel("Score")
    # plt.show()


main()
print("--- %s minutes ---" % str((time.time() - start_time) / 60))
