import os
import subprocess
import numpy as np
import matplotlib.pyplot as plt

# subprocess.run("makeme")
result = subprocess.run(['java','-jar','testrun.jar','-submission=player19','-evaluation=BentCigarFunction', '-seed=1'], stdout=subprocess.PIPE)

string_array = result.stdout[:-43].decode('utf-8')

string_array = string_array.split(',')

dataset = []
for i in range(len(string_array)):
	dataset.append(float(string_array[i]))

x = [i for i in range(len(dataset)) ]
plt.plot(x,dataset)
plt.show()



