JC = javac
JC_FLAGS = -cp
JVM = java
JZIP = jar
MAIN_CLASS = player19.java
CLASSES = \
		Initializations.java \
		Variations.java \
		Selections.java 
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JC_FLAGS) contest.jar $(MAIN_CLASS) $(CLASSES)
default: classes runtime submission
classes: $(MAIN_CLASS:.java=.class) $(CLASSES:.java=.class)
runtime:
	$(JZIP) uf testrun.jar $(CLASSES:.java=.class)
submission:
	$(JZIP) cmf MainClass.txt submission.jar $(MAIN_CLASS:.java=.class) $(CLASSES:.java=.class)	