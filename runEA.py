import os
import subprocess
import numpy as np
import matplotlib.pyplot as plt
import json

def plot_result(x,y):
	plt.plot(x,y)
	plt.show()

subprocess.run("makeme")
result = subprocess.run(['java','-jar','testrun.jar','-submission=player19','-evaluation=BentCigarFunction', '-seed=1'], stdout=subprocess.PIPE)

json_string = result.stdout[:-44].decode('utf-8').replace('\'','"')
# print(json_string)

js = json.loads(json_string)

# string_array = result.stdout[:-43].decode('utf-8')

# string_array = string_array.split(',')

# data = []
# for i in range(len(string_array)):
# 	data.append(float(string_array[i]))

# x = [i for i in range(len(data)) ]
plot_result(js["data"]["x"],js["data"]["y"])






