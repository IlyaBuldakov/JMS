## Application startup instructions
___

### Properties

    You need to configure the properties for launching the broker, 
    connecting consumer and producer to it, and also set the limits 
    for triggering a warning when the metric value is exceeded


    All properties are given to a string type and are parsed inside
    the Java program.

    You can also change properties in the finished JAR 
    (properties.yaml file).

1. Set values for properties in:
   1. `server/src/main/resources`
   2. `producer/src/main/resources`
   3.  `consumer/src/main/resources`

(`properties.yaml` file)

### Maven build
1. Clean and package in main (JMS) module
2. You will see that the rest of the application modules have also been built

### JAR run
    Firstly you need to run a server module. Do it after "producer"
    and "consumer" modules

1. Open `server/target`. 
2. Execute this in terminal `java -jar server-1.0-SNAPSHOT-jar-with-dependencies.jar`
3. Repeat these steps with the consumer and producer modules