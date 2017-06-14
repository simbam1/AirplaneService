
AirplaneService API

Welcome to the AirplaneService Application. A SpringBoot application with app logic implemented in JAVA 8 and depends on a PostgreSQL database.  The application makes use of Swagger Docs, lombok, general spring mvc frameworks, and maven for project configuration.
Application workflow is simple. Any rest API call goes through the following workflow:

			resource -> service -> dao -> database

I considered configuring SpringBoot to natively manage a local database when starting, this makes the queue local to the AirplaneService. 


RUNNING APPLICATION:
To run the application, it is important to first check the JAVA version. This application runs on Java 8. To do so, run the following command:
1) echo $JAVA_HOME
a) If jdk1.7 is referenced or lower, first ensure that you have Java 8 installed:
i) brew update
ii) brew cask install java
2) If installed, reassign your JAVA_HOME variable to jdk 1.8. Here is an example, but the actual path may be differ:
i) JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_66.jdk/Contents/Home/
3) Navigate to the airplaneservice base folder and run the following:
a) mvn clean package
b) java -jar target/airplaneservice-0.0.1-SNAPSHOT.jar
i) If there are permission problems creating or deleting directories, the sudo command may be useful
4) Using any browser visit: http://localhost:8080/swagger-ui.html
i) Note the port may be different, if a different port is used, check the terminal. Springboot prints the port in use
ii) If user is intent on port 8080, run the following commands:
5) lsof -i tcp:8080
6) Using the PID, the user should run:
i) sudo kill <PID>
b) Re-visit http://localhost:8080/swagger-ui.html




BACKLOG:
Here is a list of items I wanted to complete but could not due to time constraints:
1) Integration tests in addition to more unit tests
2) Ensure timezone support for global application
3) Build SDK for communicating with the persistence layer. Would help facilitate with building and maintaining other micro services
4) Implement robust venue constraints
5) Ensure the bite sizes on the database are consistent with java bite sizes
6) Add check style
7) Add authentication on API
8) Map exceptions to the appropriate codes
9) Pipeline and deploy to an EC2 instance in AWS using Docker
10) Create proper mapping classes 
11) Make a user provisioning MicroService



# AirplaneService
