# ec_assignments
### Compile
1. javac -cp contest.jar player19.java XXX.java(additional classes)
2. jar cmf MainClass.txt submission.jar player19.class XXX.class(compiled additional classes)
3. jar uf testrun.jar XXX.class(compiled additional classes)

### Run
java -jar testrun.jar -submission=player19 -evaluation=BentCigarFunction -seed=1

### Results for Schaffers
1. Simple x Correlated: Best setting is at mixing factor = 0.7, p2 = 0.3 and p3 = 0.1. (MBF = 8.662277)
2. Whole x Correlated: Best setting is at mixing factor = 0.4, p2 = 0.5 and p3 = 0.1. (MBF = 7.671354)
3. Blend x Correlated: Best setting is at mixing factor = 0.7, p2 = 0.5 and p3 = 0.1. (MBF = 4.04497)

Thus, the benchmark is Simple x Correlated (MBF = 8.662277)

### Results for BentCigar
1. Simple x Correlated: Best setting is at mixing factor = 0.6, p2 = 0.03 and p3 = 0.01. (MBF = 9.991142)
2. Whole x Correlated: Best setting is at mixing factor = 0.3, p2 = 0.07 and p3 = 0.05. (MBF = 9.983896)
3. Blend x Correlated: Best setting is at mixing factor = 0.6, p2 = 0.03 and p3 = 0.07. (MBF = 9.972013)

Thus, the benchmark is Simple x Correlated (MBF = 9.9911427)

### Results for Katsuura
