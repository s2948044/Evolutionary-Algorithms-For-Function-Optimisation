# ec_assignments
### Compile
1. javac -cp contest.jar player19.java XXX.java(additional classes)
2. jar cmf MainClass.txt submission.jar player19.class XXX.class(compiled additional classes)
3. jar uf testrun.jar XXX.class(compiled additional classes)
### Run
java -jar testrun.jar -submission=player19 -evaluation=BentCigarFunction -seed=1
