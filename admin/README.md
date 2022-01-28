1) Install mysql
2) create schema mmg;
3) check in application.properties that username, password and schema name are proper
4) mvn clean install
5) run select * from user; ( you should see the data from data.sql )
6) mvn spring-boot:run
7) try localhost:8083 you should see the json output
8) Install postman and configure it as shown in postman and postman_header image files in the root folder of the source code. 


Links
https://github.com/hiiamrohit/Countries-States-Cities-database