build: Beamdrone.java Curse.java
	javac Beamdrone.java
	javac Curse.java

run-p3:
	java Beamdrone

run-p4:
	java Curse

clean:
	rm -f *.class
	
.PHONY: build clean