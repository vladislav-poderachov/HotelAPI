In terminal write command: mvn spring-boot:run
Main database driver is h2. If you want use mySQL, change the file application.properties where you need correct the line: spring.profiles.active=h2. Result line must be like this: spring.profiles.active=mysql.
