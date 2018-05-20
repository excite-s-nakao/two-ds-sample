# two-ds-sample

A sample project for using two datasources in Spring Boot.

## purpose

- show how to handle two dataasources(in this case, 2 MySQL) in Spring Boot environment.
- share the example of lines that are 
  - DataSource (in this sample, these are DataSource1Config.java, DataSource2Config.java)
  - how to register Mappers to the specific datasource.


## to run

### prerequisites

- You need docker, and that "docker-compose" command can run containers.

### run

1. run containers

```
$ cd container
$ docker-compose up
```

2. run tests

```
$ ./gradlew test
```

## notes

