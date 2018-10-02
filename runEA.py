import os
import subprocess
import numpy as np
import matplotlib.pyplot as plt
import json

def plot_result(x,y):
	plt.plot(x,y)
	plt.show()

def make():
	subprocess.run("makeme")	

def getTerminalOutput():
	return subprocess.run(['java','-jar','testrun.jar','-submission=player19','-evaluation=BentCigarFunction', '-seed=1'], stdout=subprocess.PIPE)

def getJsonString():
	return getTerminalOutput().stdout[:-44].decode('utf-8').replace('\'','"')


def main():
	js = json.loads(getJsonString())
	plot_result(js["data"]["x"],js["data"]["y"])



main()


