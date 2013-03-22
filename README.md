spring-data-demo
================
Before running the demo:

  Create The MySQL schema and MongoDB database.

  Update src/main/resources/META-INF/spring/database.properties to match the MySQL or MongoDB instances you want to connect to.

Run demo by invoking mvn install

Change the comments in src/test/resources/integration-test.xml to exclude jpa and include mongodb.

  <!--import resource="classpath:META-INF/spring/applicationContext-jpa.xml"/-->

  \<import resource="classpath:META-INF/spring/applicationContext-mongo.xml" /\>
  
