import os

os.system("makeme")

# filedir = "data/test.csv"
# string = "java -jar testrun.jar -submission=player19 -evaluation=BentCigarFunction -seed=1 > " + filedir
# os.system(str(string))

import subprocess
result = subprocess.run(['java','-jar','testrun.jar','-submission=player19','-evaluation=BentCigarFunction', '-seed=1'], stdout=subprocess.PIPE)
print(result.stdout)