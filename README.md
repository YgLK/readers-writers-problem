<h1 align="center"> Readers-Writers Problem

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
[![Generic badge](https://img.shields.io/badge/MAVEN-3.8.1-brightgreen.svg)](https://shields.io/)
[![Generic badge](https://img.shields.io/badge/coverage-60/100-green.svg)](https://shields.io/)

 
## Table of Contents
	
- [Problem description](#description)
- [Instructions](#instructions)
- [Messages sent by the server](#servermsg)
- [Javadoc & SonarQube raport](#doc_scube)

  
## Problem description  <a name="description"></a>

There is a shared resource which should be accessed by multiple processes.
There are two types of processes in this context. They are reader and writer.
Any number of readers can read from the shared resource simultaneously,
but only one writer can write to the shared resource. When a writer is
writing data to the resource, no other process can access the resource.
A writer cannot write to the resource if there are non-zero number of
readers accessing the resource at that time. [Source of the description](https://www.studytonight.com/operating-system/readers-writer-problem)

In my implementation of the problem there are 10 Readers and 3 Writers.
Only 1 Writer can have access to the resource and write the data.
Readers number is also limited - only 5 Readers can read from the resource in the
same time. A writer cannot write to the resource if there are non-zero number of
readers accessing the resource at that time. Similarly, if writer has access to the
resource, then no Readers can read from it. After reading/writing, every person
takes short break to 'have energy' for the next reading/writing session.


## Instructions  <a name="instructions"></a>
1. Build the project with Maven.
2. Find target directory in the main module.
3. Run the problem from your terminal after moving to the target directory in the main
 module by entering this command: <br>
    `java -jar .\rw-problem-1.0-jar-with-dependencies.jar`
4. Watch how Readers and Writers according to the given rules change their turns repeatedly
in the endless loop.

PS. On Win10 sometimes only one thread is running after running the JAR file (wait up to 4 sec). In that case
you can try to re-run the JAR with the same command (up to 5 tries) or use an IDE (such as Intellij IDEA)
and run main method in the Main class in the rw-problem module.


## Messages sent by the server <a name="servermsg"></a>
    
`Thread-0 wants to write.` - Writer thread #0 is ready to start writing the data to the resource.

`Thread-0 started writing.` - Writer thread #0 has been notified and started writing.

`Thread-0 stopped writing.` - Writer thread #0 stopped writing and is going to rest for a while (sleep).<br />
                              After the resting thread immediately wants to write.
                              
`Thread-1 wants to read.` - Reader thread #0 is ready to start reading the data from the resource.

`Thread-1 started reading.` - Reader thread #0 has been notified and started reading.

`Thread-1 stopped reading.` - Reader thread #0 stopped reading and is going to rest for a while (sleep).<br />
                              After the resting thread immediately wants to read.
                              
`Writers: X/Y Readers: Z/W` - X Writers wait for writing, Y Writers are writing right now.<br />
			                  Z Readers wait for reading, W Readers are reading right now.


## Javadoc & SonarQube raport <a name="doc_scube"></a>
 In order to explore `Javadoc` or `SonarQube raport` download appropriately `javadoc` or `sonar-cube` directory,<br> open them and for:
 > `Javadoc`: open [allclasses.html](https://github.com/YgLK/readers-writers-problem/blob/main/javadoc/allclasses.html) file <br> Then the project documentation will appear.
 
 or 
 
 > `SonarQube raport`: <br> 
> open [Readers-Writers problem.html](https://github.com/YgLK/readers-writers-problem/blob/main/sonar-cube/Readers-Writers%20problem.html) <br> Then project test coverage will appear. <br>
> open [Issues.html](https://github.com/YgLK/readers-writers-problem/blob/main/sonar-cube/Issues.html) <br>Then project code smells (issues) raport will appear.


