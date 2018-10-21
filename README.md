# Evolutionary algorithms (EA) for function optimisation

### About
This repository contains code for both deterministic EAs where only one crossover operator
can be used throughout the whole process, and a probabilistic EA where multiple crossover operators
can be adopted with specific probability proportions.

A meta-EA is also implemented to find the best probability proportion scheme of multiple crossovers
operators.

### Branches
1. Master branch is used for running evaluations with the deterministic benchmark EAs.
2. PythonCommandLine branch is used for the grid search trivial tuning and data analysis.
3. meta_opt branch is used for the development of the meta-EA (CMA-ES) in Python.

### Compile
1. Under the main folder, use the Makefile (command "make") to compile.
2. Run with java -jar testrun.jar -submission=player19 -evaluation=KatsuuraEvaluation -seed=1
    or java -jar testrun.jar -submission=player19 -evaluation=SchaffersEvaluation -seed=1
    or java -jar testrun.jar -submission=player19 -evaluation=BentCigarFunction -seed=1

Thus, the benchmark is Simple x Correlated (MBF = 5.2050985)
