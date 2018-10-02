import os

# os.system("makeme")

# filedir = "data/test.csv"
# string = "java -jar testrun.jar -submission=player19 -evaluation=BentCigarFunction -seed=1 > " + filedir
# os.system(str(string))

import subprocess
result = subprocess.run(['java','-jar','testrun.jar','-submission=player19','-evaluation=BentCigarFunction', '-seed=1'], stdout=subprocess.PIPE)

string_array = result.stdout[:-43].decode('utf-8')

string_array = string_array.split(',')

# print(string_array)
datap = []
for i in range(0,len(string_array),2):
	# if((i+1)%2 == 0):
	# 		datap = []
	# datap.append(string_array[])



