# Tech
- Java version used: 8
- framework used: spring-boot

# Intructions to use:
  The user need to have Maven  and JVM installed on the macchine and do one of this two steps:
- run from CLI the command on root folder of the project __mvn package__ to generate a JAR and execute with  __java -jar target/covid-demo-0.0.1-SNAPSHOT.jar__
- or  from CLI on root folder __mvn sprint-boot:run__

## Getting results
- In order to get the results for all countries open your browser at [localhost:8081/all](http://localhost:8081/all)
- In order to get the results for an specific continent open your browser at localhost:8081/{continent} ex: [localhost:8081/europe](http://localhost:8081/europe)
