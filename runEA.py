import os
import subprocess
import numpy as np
import matplotlib.pyplot as plt
import json

varChoice = "singleArithmeticCrossOver"
populations = [100,90,80,60,50,40,30]
mixingfactors = [0.8,0.7,0.6,.5,.4,.3]
# varchoice = 2

def plot_result(x,y):
	plt.plot(x,y)
	plt.xlabel('x every 100 evalution')
	plt.ylabel('Fitnessscore')

	plt.show()


def make():
	subprocess.run("make")	


def runEA(population, mixingfactor, varChoice):
	# result = subprocess.run(['java','-jar', '-Dpopulation='+str(population),'testrun.jar','-submission=player19','-evaluation=BentCigarFunction', '-seed=1'], stdout=subprocess.PIPE)
	result = subprocess.run(['java','-jar','-Dpopulation='+str(population), '-DmixingFactor='+str(mixingfactor),'-DvarChoice='+str(varChoice),'testrun.jar','-submission=player19','-evaluation=BentCigarFunction', '-seed=1'], stdout=subprocess.PIPE)
	jsonstring = result.stdout.decode('utf-8').replace('\'','"').split('\r')[0]
	# print(jsonstring)
	
	return jsonstring
	# return result.stdout[:-44].decode('utf-8').replace('\'','"')


# def getJsonString():
	# return runEA()
	# return getTerminalOutput().stdout[:-47].decode('utf-8').replace('\'','"')





def main():
	make()
	# for i in range(6):
	population = populations[0]
	mixingfactor = mixingfactors[0]



	print(runEA(population, mixingfactor, varChoice))
		# js = json.loads(runEA(population, mixingfactor))
		# print(json.dumps(js, indent=2, sort_keys=True))
	# plot_result(js["data"]["x"],js["data"]["y"])





main()


