import os
import subprocess
import numpy as np
import matplotlib.pyplot as plt
import json

populations = [100,90,80,60,50,40,30]
mixingfactors = [0.8,0.7,0.6,.5,.4,.3]
varchoice = 2

def plot_result(x,y):
	plt.plot(x,y)
	plt.xlabel('x every 100 evalution')
	plt.ylabel('Fitnessscore')

	plt.show()


def make():
	subprocess.run("makeme")	


def runEA(population, mixingfactor):
	result = subprocess.run(['java','-jar', '-Dpopulation='+str(population),  '-DmixingFactor='+str(mixingfactor), '-DvarChoice='+str(varchoice),'testrun.jar','-submission=player19','-evaluation=BentCigarFunction', '-seed=1'], stdout=subprocess.PIPE)
	# return subprocess.run(['java','-jar', '-Dpopulation='+str(population), '-DmixingFactor='+str(mixingfactor),'testrun.jar','-submission=player19','-evaluation=SchaffersEvaluation', '-seed=1'], stdout=subprocess.PIPE)
	jsonstring = result.stdout.decode('utf-8').replace('\'','"').split('\r')[0]
	
	return jsonstring
	# return result.stdout[:-44].decode('utf-8').replace('\'','"')


# def getJsonString():
	# return runEA()
	# return getTerminalOutput().stdout[:-47].decode('utf-8').replace('\'','"')





def main():
	# make()
	for i in range(6):
		population = populations[i]
		mixingfactor = mixingfactors[i]

		print(runEA(population, mixingfactor))
		# js = json.loads(runEA(population, mixingfactor))
		# print(json.dumps(js, indent=2, sort_keys=True))
	# plot_result(js["data"]["x"],js["data"]["y"])





main()


