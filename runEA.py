import os
import subprocess
import numpy as np
import matplotlib.pyplot as plt
import json

def plot_result(x,y):
	plt.plot(x,y)
	plt.xlabel('x every 100 evalution')
	plt.ylabel('Fitnessscore')

	plt.show()


def make():
	subprocess.run("makeme")	

def getTerminalOutput():
	population = 20
	mixingfactor = 0.6
	# return subprocess.run(['java','-jar','testrun.jar','-submission=player19','-evaluation=BentCigarFunction', '-seed=1'], stdout=subprocess.PIPE)
	return subprocess.run(['java','-jar', '-Dpopulation='+str(population), '-DmixingFactor='+str(mixingfactor),'testrun.jar','-submission=player19','-evaluation=SchaffersEvaluation', '-seed=1'], stdout=subprocess.PIPE)


def getJsonString():
	# return getTerminalOutput().stdout[:-44].decode('utf-8').replace('\'','"')
	return getTerminalOutput().stdout[:-47].decode('utf-8').replace('\'','"')




def main():
	make()
	# print(getJsonString())
	js = json.loads(getJsonString())
	# print(json.dumps(js, indent=2, sort_keys=True))
	plot_result(js["data"]["x"],js["data"]["y"])





main()


