--- 1_1 Basic setup for Java development

Recommended Java version: JDK 11 LTS
$ javac -version

Test the Maven installation:
$ mvn --version

Github setup:
$ git --version 
$ git config --list 

(If you do not get an output from git config - including your name and email address -, then configure the git installation)

Graphical Git clients:
SourceTree, GitKraken


--- 1_2 Build management with the Maven tool

Build tool: Tools to obtain dependencies, compile source code, package artifacts, etc.
Tools to state the project dependencies on external artifacts
- Maven (more professional) or Gradle

Artifacts: Plugin jars and other files

src/main/java: Project source code
src/test/java: Test source
pom.xml: Project Object Model (the core of a project's configuration in Maven)

Create a Maven project:
mvn archetype:generate -DgroupId=pt.ua.deti.lab1.weather -DartifactId=WeatherForecast -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false

---

Google’s Gson is a Java library that can be used to convert Java Objects into their JSON representation
Retrofit turns a HTTP API into a Java interface

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

(Along with)

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.ipma.pt/open-data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

@GET(distrits-islands.json) - Relative URL
http://api.ipma.pt/open-data/distrits-islands.json

---

Compile and run Maven project:

mvn clean package #get dependencies, compiles the project and creates the jar
mvn exec:java -Dexec.mainClass="pt.ua.deti.ies.lab1.weather.WeatherStarter"

[OR]

mvn clean install
mvn exec:java -Dexec.mainClass="pt.ua.deti.ies.lab1.weather.WeatherStarter"

package VS install
package: Take the compiled code and package it in its distributable format, such as a JAR.
install: Install the package into the local repository, for use as a dependency in other projects locally

Run project with arguments:
mvn exec:java -Dexec.mainClass="pt.ua.deti.ies.lab1.weather.WeatherStarter" -Dexec.args="1010500"

[WINDOWS version]
mvn exec:java -D"exec.mainClass"="pt.ua.deti.ies.lab1.weather.WeatherStarter" -D"exec.args"="Aveiro"

Note: "-D" switch is used to define/pass a property to Maven in CLI.


--- 1_3 Source code management using Git

Exclusions: files/folders that are only of local interest and should not be passed to the common repository

./target folder: Compiled versions, should not go to the repository

https://gist.github.com/icoPT/31b343c987cb45ba0cde2bbee8cbd7ea
https://github.com/github/gitignore

---

Import project into the Git version:

$ cd project_folder # move to the root of the working folder to be imported

$ git init # initialize a local git repo in this folder

$ git remote add origin <REMOTE_URL>  #must adapt the url for your repo

$ git add .   # mark all existing changes in this root to be commited

$ git commit -m "Initial project setup for exercise 1_3" #create the commit snapshot locally

$ git push -u origin main #uploads the local commit to the shared repo 


--- 1_4 Introduction to Docker 

Clone a repository in a container (example):
docker run --name repo alpine/git clone \ url
docker cp repo:/git/getting-started/ .

Build a image:
cd getting-started
docker build -t docker101tutorial .

---

Running a container launches your application with private resources, securely isolated from the rest of your machine

Run a container: 
docker run -d -p 80:80 docker/getting-started
(You can combine single character flags to shorten the full command: -dp)

-d - run the container in detached mode (in the background)
-p 80:80 - map port 80 of the host to port 80 in the container
docker/getting-started - the image to use

---

Container: A sandboxed process on your machine that is isolated from all other processes on the host machine

When running a container, it uses an isolated filesystem. This custom filesystem is provided by a container image.
Since the image contains the container’s filesystem, it must contain everything needed to run an application - all dependencies, configuration, scripts, binaries, etc. 
The image also contains other configuration for the container, such as environment variables, a default command to run, and other metadata.

Docker container image: a lightweight, standalone, executable package of software that includes everything needed to run an application: code, runtime, system tools, system libraries and settings.

Dockerfile: A text-based script of instructions that is used to create a container image.

Build a new container image:
docker build -t getting-started .

The -t flag tags our image. Think of this simply as a human-readable name for the final image.

The . at the end of the docker build command tells that Docker should look for the Dockerfile in the current directory.

---

Check to see whether the Portainer Server container has started by running
root@server:~# docker ps
CONTAINER ID   IMAGE                                              COMMAND                  CREATED        STATUS        PORTS                                                                                  NAMES
f4ab79732007   portainer/portainer-ce:latest                      "/portainer"             2 weeks ago    Up 29 hours   0.0.0.0:8000->8000/tcp, :::8000->8000/tcp, 0.0.0.0:9443->9000/tcp, :::9443->9443/tcp   portainer

---

Run PostgresSQL using a pre-made image:
docker run --name pg-docker -e POSTGRES_PASSWORD=docker -e POSTGRES_DB=sampledb -e PGDATA=/tmp -d -p 5433:5432 -v ${PWD}:/var/lib/postgresql/data postgres:11 
(The PostgresQL is ready to connect and use. NOTE: Default is -p 5432:5432)

You may replace ${PWD} with an absolute path to the [host] location where you would like mapthe database storage, especially in Windows (${PWD} will not resolve). 

Connect to PSQL server:
https://dev.to/shree_j/how-to-install-and-run-psql-using-docker-41j2

Manage your postgres from the browser by launching http://localhost:5050 (PG-admin):
docker run --rm -p 5050:5050 thajeztah/pgadmin4

+ Enter the credentials to save and manage PSQL via GUI
Host - The IP address of your machine
Password - Password used while creating the PSQL server with docker

---

https://docs.docker.com/compose/gettingstarted/

Run one-off commands for your services:
docker-compose run (...)

See what environment variables are available to the web service:
docker-compose run web env

Remove the containers entirely and remove the data volume used by the Redis container:
docker-compose down --volumes


--- FINAL Review questions

A) Maven has three lifecycles: clean, site and default. Explain the main phases in the default lifecycle.

Clean lifecycle: Handles project cleaning
Site lifecycle: Handles the creation of your project's web site (project site documentation)
Default lifecycle: Handles your project deployment

Each life cycle consists of a sequence of phases. The default build life cycle consists of 23 phases as it's the main build lifecycle. 
The default lifecycle includes the following phases:

validate - validate the project is correct and all necessary information is available
compile - compile the source code of the project
test - test the compiled source code using a suitable unit testing framework. These tests should not require the code be packaged or deployed
package - take the compiled code and package it in its distributable format, such as a JAR.
verify - run any checks on results of integration tests to ensure quality criteria are met
install - install the package into the local repository, for use as a dependency in other projects locally
deploy - done in the build environment, copies the final package to the remote repository for sharing with other developers and projects.

These lifecycle phases (plus the other lifecycle phases not shown here) are executed sequentially to complete the default lifecycle. 

---

B) Maven is a build tool; is it appropriate to run your project to?

Build tools, such as Maven, are used for obtaining dependencies (required external components/libraries), compiling source code, packaging artifacts, updating documentation, installing on the server, etc. Maven can provide benefits for your build process by employing standard conventions and practices to accelerate your development cycle while at the same time helping you achieve a higher rate of success.

Maven is essentially a project management and comprehension tool and as such provides a way to help with managing:
- Builds
- Documentation
- Reporting
- Dependencies
- SCMs
- Releases
- Distribution

---

C) What would be a likely sequence of Git commands required to contribute with a new feature to a given project? (i.e., get a fresh copy, develop some increment, post back the added functionality) 

(Get a fresh copy)
$git pull origin

(Add feature)

(Post back the added functionality)
Optional: $ git status
$ git add <NEW_FEATURE>
$ git commit -m "New featured added"
$ git push -u origin main

---

D) There are strong opinions on how to write Git commit messages... Find some best practices online and give your own informed recommendations on how to write good commit messages (in a teamproject).  

(https://www.git-scm.com/book/en/v2/Distributed-Git-Contributing-to-a-Project#_commit_guidelines)

Having a good guideline for creating commits and sticking to it makes working with Git and collaborating with others a lot easier.

1. Try to make each commit a logically separate changeset
If you can, try to make your changes digestible. Split your work into at least one commit per issue, with a useful message per commit.

2. Keep each commit clear and concise
Your messages should start with a single line that’s no more than about 50 characters and that describes the changeset concisely, followed by a blank line, followed by a more detailed explanation. The Git project requires that the more detailed explanation include your motivation for the change and contrast its implementation with previous behavior — this is a good guideline to follow. 

3. Commit your message in the imperative

Other recommendations (https://chris.beams.io/posts/git-commit/):

1. Separate subject from body with a blank line
2. Limit the subject line to 50 characters
3. Capitalize the subject line
4. Do not end the subject line with a period
5. Use the imperative mood in the subject line
6. Wrap the body at 72 characters
7. Use the body to explain what and why vs. how

Your submissions should not contain any whitespace errors. Git provides an easy way to check for this — before you commit, run git diff --check, which identifies possible whitespace errors and lists them for you.

---

E) Docker automatically prepares the required volume space as you start a container. Why is it important that you take an extra step configuring the volumes for a (production) database? 

Volumes are the preferred mechanism for persisting data generated by and used by Docker containers. 
While bind mounts are dependent on the directory structure and OS of the host machine, volumes are completely managed by Docker.

In addition, volumes are often a better choice than persisting data in a container’s writable layer, because a volume does not increase the size of the containers using it, and the volume’s contents exist outside the lifecycle of a given container. You can create a volume explicitly using the docker volume create command, or Docker can create a volume during container or service creation.

However, we must take an extra step configuring the volumes for a (production) database, because the volumes size can grow exponentially and, even when no running container is using a volume, that same volume is still available to Docker and is not removed automatically.

