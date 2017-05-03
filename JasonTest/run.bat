call mvn clean
call mvn assembly:assembly
call java -jar target/JasonTest-jar-with-dependencies.jar
