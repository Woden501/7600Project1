# 7600Project1

Dependencies:
Java JDK 11.0.1
Maven 3.5.4

How to build:
1) Open a command line and navigate to the project's root directory
2) Run: mvn package
	a) This will build the program's JAR
	b) This JAR is located in the target directory, and should be called 7600project1-0.0.1-SNAPSHOT.jar
3) Copy the JAR produced in step 2 to the location you want to run the program from
4) Run: java -jar {/absolute/path/to/input/file/english_big.txt}
	a) Ensure the java executable you are using is the JDK 11.0.1 executable or you will get an error
5) The project.arff file will be located in the directory you ran the jar file from