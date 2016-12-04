JFLAGS=-g
JC=javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

	
CLASSES = \
		Handlers.java \
		SystemCommandHandler.java \
		ThreadHandler.java \
		ProcessHandler.java \
		FileOperations.java \
		MainFrame.java \
		RunnerRunner.java \

default: classes
		

classes: $(CLASSES:.java=.class)
	
		
clean:
		$(RM) *.class
