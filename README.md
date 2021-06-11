# Command-line based RPN calculator

Require the following to build & package the project:
- sbt 1.4.*
- scala 2.13.*

To run the application:
```
sbt run
```

To run the unit tests:
```
sbt test
```

To run the integration tests (against the 8 examples provided in the PDF file)
```
sbt it:test
```

The sbt-assembly plugin is used for packaging the project into a singe JAR file, so it can be run with Java, without requiring Scala:
```

```