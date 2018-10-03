import os
import subprocess
import numpy as np
import matplotlib.pyplot as plt
import json
import time

# measure duration of algortithme
start_time = time.time()

# variation numbers
variations = [1,2,3,4,5]

# population chang
# populations = [100]*5
mixingfactors = [0.8,0.7,0.6,.5,.4,.3]
varchoice = 2
varNames = ["singleArithmeticCrossOver", "order1CrossOver", "singleArithmeticCrossOver", "blendCrossOver", "wholeArithmeticCrossOver"]

# makefile (change to your own 'make.exe' command)
def make():
	subprocess.run("makeme")	

# run algorithm with parameter values. Returns json string
def runEA(population, mixingfactor, varchoice):
	result = subprocess.run(['java','-jar','-Dpopulation='+str(population), '-DmixingFactor='+str(mixingfactor),'-DvarChoice='+str(varchoice),'testrun.jar','-submission=player19','-evaluation=SchaffersEvaluation', '-seed=1'], stdout=subprocess.PIPE)
	jsonstring = result.stdout.decode('utf-8').replace('\'','"').split('\r')[0]
	
	return jsonstring

# runs EA with possible parameter tweeks
def main():

	make()

	population = 20

	colors = ['b','g','r','c','m']

	count = 0

	# all crossovers
	for i in range(5):
		
		# get crossover
		varchoice = variations[i]

		# repeat 30 times with different variables
		for j in range(1000):
			mixingfactor = .8

			# make json object
			js = json.loads(runEA(population, mixingfactor, varchoice))
			x = js['data']['x']
			y = js['data']['y']

			# plot data of algortihm
			plt.plot(x,y, label=varNames[i]+'mixf='+str(mixingfactor), color=colors[i])

			count += 1
			print(count)
	# plt.legend()
	plt.show()





main()
print("--- %s minutes ---" % (time.time() - start_time)/60 )



