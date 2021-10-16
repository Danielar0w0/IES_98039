--- 1_1

Recommended Java version: JDK 11 LTS
$ javac -version

Test the Maven installation:
$ mvn --version

Github setup:
$ git --version 
$ git config --list 

(If you do not get an output from git config including your name and email address, then configure the git installation)

Graphical Git clients:
SourceTree, GitKraken

--- 1_2

Build tool:
Tools to obtain dependencies, compile source code, package artifacts, etc.
Tools to state the project dependencies on external artifacts
- Maven (more professional) or Gradle

Artifacts: Plugin jars and other files

src/main/java: Project source ode
src/test/java: Test source
pom.xml: Project Object Model (the core of a project's configuration in Maven)

---

Google’s Gson is a Java library that can be used to convert Java Objects into their JSON representation

<dependency>
    <groupId>com.squareup.retrofit2</groupId>
    <artifactId>retrofit</artifactId>
    <version>2.9.0</version>
</dependency> 

<dependency>
    <groupId>com.squareup.retrofit2</groupId>
    <artifactId>converter-gson</artifactId>
    <version>2.9.0</version>
</dependency>

<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.8.8</version>
</dependency>

---

Compile and run project:

mvn package #get dependencies, compiles the project and creates the jar
mvn exec:java -Dexec.mainClass="pt.ua.deti.ies.lab1.weather.WeatherStarter"

Run project with arguments:

mvn exec:java -Dexec.mainClass="pt.ua.deti.ies.lab1.weather.WeatherStarter" -Dexec.args="1010500"

Note: "D" switch is used to define/pass a property to Maven in CLI.

--- 1_3

Exclusions: files/folders that are only of local interest and should not be passed to the common repository

./target folder: Compiled versions, should not go to the repository

https://gist.github.com/icoPT/31b343c987cb45ba0cde2bbee8cbd7ea
https://github.com/github/gitignore

---

Import project into the Git version:

$ cd project_folder # move to the root of the working folder to be imported

$ git init # initialize a local git repo in this folder

$ git remote add origin <REMOTE_URL>  #must adapt the url for your repo

$ git add.   # mark all existing changes in this root to be commited

$ git commit -m "Initial project setup for exercise 1_3" #create the commit snapshot locally

$ git push -u origin main #uploads the local commit to the shared repo 

--- 1_4