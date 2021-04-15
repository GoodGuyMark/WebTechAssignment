This is a Maven, single page web application that uses RESTful web services, along with CRUD actions from a MySQL database.
The database consists of users and items and CRUD actions can be performed on both.
The application is run on a local Wildfly server.
The architecture for the project is detailed below:

![image](https://user-images.githubusercontent.com/68013944/114874873-a6be4300-9df4-11eb-9bd2-fbf12f1fcd37.png)

Note: Functionality for the web app only works on Google Chrome or Microsoft Edge.

Testing includes both unit tests and integration tests. Integration tests can be run using the Maven command: mvn clean test -Parq-wildfly-remote
