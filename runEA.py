import os
import subprocess
import plotly
import plotly.plotly as py
import plotly.graph_objs as go

subprocess.run("makeme")
result = subprocess.run(['java','-jar','testrun.jar','-submission=player19','-evaluation=BentCigarFunction', '-seed=1'], stdout=subprocess.PIPE)

string_array = result.stdout[:-43].decode('utf-8')

string_array = string_array.split(',')

dataset = [];
for i in range(len(string_array)):

	dataset.append(float(string_array[i]))

print(dataset)

# Create a trace
trace = go.Scatter(
    x = [range(len(dataset))],
    y = random_y
)

data = [trace]

py.iplot(data, filename='basic-line')



