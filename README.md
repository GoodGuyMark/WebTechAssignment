# Web Technologies Assignment

This project was an assignment in my Web Technologies module during my Masters course. It is a Java, single page web application (SPA) that uses RESTful web services, along with CRUD actions from a MySQL database. The project is run on a local Wildfly server and we were also tasked with implementing a Bootstrap CSS into out front-end. The application manages two entities, users and an entity of your our choice (I chose games). Both these entities are to be able to have CRUD actions performed on them.

How to install the application:
1. Download the code by cloning the repository or downloading the zip folder directly.
2. Create the database by running the .sql file in a local MySQL server (You will need the MySQL Workbench to do this. You can download this here: https://dev.mysql.com/downloads/workbench/)
3. Import your project into a Java IDE (I use Eclipse but other IDEs should have no problem running this).
4. Create a Wildfly server to be able to run the project. You can download the latest version of Wildfly here: https://www.wildfly.org/
5. Add the datasource and JDBC driver to bind to the server. This can be done by starting the Wildfly server and going to http://localhost:8080/ on a web browser and clikcing 'Administration Console'. From there, click on 'Configuration' on the navigation bar and click 'Subsystems' -> 'Datasources & Drivers' to add datasources and drivers. If it is your first time creating a Wildfly server, you will have to create an admin user to access the admin console.
6. To start the application, run the project on the recetnly created Wildfly server.

How to use the application:
1. Upon starting the application you are brought to the login screen. Here you will enter a username and password and log in as that user. You can use the username 'user1' and password 'password1' to log in as one of the users already hardcoded into the database. 
2. Once you have logged in your are brought to the main admin dashboard. The cards at the top list the total number of users and games in the database. The tables lists the users and games separately, and there is full search functionality for the tables.
3. Clicking the 'Info' button on any of the entities bring up a modal pop-up with the full details of that entity. From here you can perform CRUD actions on that entity and also create a new entity. 
4. To create a new user, click the green 'Add New User' button at the top of the modal. The forms will empty and you can enter your details for the new user. When oyu are finished entering your details click the blue 'Save New User' button at the bottom of the modal to successfully create the new user.
5. To update a user's details, click the 'Info' button on that user and edit the necessary details. Click the yellow 'Update User' button to confirm the changes.
6. To delete a user, click the 'Info' button on the user you want to delete and then click the red 'Delete' button.
7. Points 3-6 can be done similarly for the games table.
