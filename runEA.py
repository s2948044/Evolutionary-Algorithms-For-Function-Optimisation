import os

os.system("makefile")
for i in range(50):
		filedir = "data/test" + str(i) + ".csv"
		string = "java -jar testrun.jar -submission=player19 -evaluation=BentCigarFunction -seed=1 > " + filedir
		os.system(str(string))
