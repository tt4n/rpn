# Command-line based RPN calculator

Requires the following to run, build & package the project:
- JDK 8+
- sbt 1.4+: to install on Mac ```brew install sbt``` ([detailed instructions if not on Mac](https://www.scala-sbt.org/1.x/docs/Setup.html))
- scala 2.13 (only required for running the packaged jar file)

To run the application:
```
sbt run
```

To run the unit tests:
```
sbt test
```

To run the integration test (against the 8 examples provided in the PDF document)
```
sbt it:test
```

To package the project into a jar file runnable with Scala
```
sbt package
```

To run the packaged jar file with Scala
```
scala target/scala-2.13/rpn_2.13-0.1.jar
```
