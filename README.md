# Roller Coaster Problem

## Description

This is a work from the Concurrent Programming course in which two solutions were implemented using different methods of synchronization. The methods used to solve the problem were implicit locks - with monitor implementation - and explicit locks.

## Problem

#### A roller coaster with only one car

A roller coaster is a popular attraction of amusement parks and modern theme parks. It consists of a steel structure forming a trail made up of elevations followed by falls and, sometimes, inversions. Most of the roller coasters have several cars in which passengers sit and are restrained (obviously for safety reasons), while some work with a single car and a larger number of passengers.

Suppose a roller coaster that has a single car with capacity for *C* passengers. Passengers, in a total of *n* (*n* &#62; *C*), often wait to get into the car, which can only be allowed to enter the trail when it is full. After completing a ride, each passenger strolls through the amusement park before returning to the roller coaster to try to enter on another ride. For safety reasons, the car carries out only *P* tours per day and, once reached that number, it is turned off.

Design and implement a program that simulates the operation of a roller coaster meeting the following requirements:

- the car allows the entry of exactly *C* passengers;
- no passenger can enter the car while it is moving on the track;
- no passenger can jump off from the car while it is moving on the track;
- no passenger can enter the car again without first leaving it.

During the execution of the program, the following actions must be performed for each entity:

- the actions carried out by passengers are boarding and unloading (unboarding) of the car;
- the car allows the entry (load) and exit (unload) of passengers;
- the car movement is started by executing the run operation;
- passengers cannot enter the car until the load operation is performed by the car;
- the car cannot start its movement until all the passengers have entered;
- passengers cannot leave the car until the unload operation is performed by the car.
- the time each passenger walks through the park before going back to the roller coaster is random and different on each run of the program.

For readability purposes, the program shall display on the standard output the execution of each of the operations performed by the passengers and the car. The total number of passengers *n*, the capacity of car *C*, and the maximum number of *P*-trips that can be performed per day should be provided as input to the program as command line arguments. Case *C* &#60; *n*, the program should show an error message to the user and be immediately shut down.

## Compiling and Running

We usually use an IDE (like NetBeans or Eclipse) to compile and build the program, but if you want to compile via terminal use the following commands.

### Dependencies

- Java 8.0 or greater

### Roller Coaster Monitor

Open the project folder:

```bash
cd RollerCoasterMonitor
```

#### How to compile

Run the following commands:

```bash
./make.sh
```

##### ```make.sh``` content

```sh
#!/bin/bash
cd src
# Compile
javac rollercoastermonitor/*.java rollercoastermonitor/rollercoaster/*.java
# Generate jar
jar cfm ../RollerCoasterMonitor.jar ../manifest.mf rollercoastermonitor/*.class rollercoastermonitor/rollercoaster/*.class
```

#### How to run

```bash
java -jar RollerCoasterMonitor.jar
```

### Roller Coaster Lock

Open the project folder:

```bash
cd RollerCoasterLock
```

#### How to compile

Run the following commands:

```bash
./make.sh
```

##### ```make.sh``` content

```sh
#!/bin/bash
cd src
# Compile
javac rollercoasterlock/*.java rollercoasterlock/rollercoaster/*.java
# Generate jar
jar cfm ../RollerCoasterLock.jar ../manifest.mf rollercoasterlock/*.class rollercoasterlock/rollercoaster/*.class
```

#### How to run

```bash
java -jar RollerCoasterLock.jar
```

## Team

[<img src="https://avatars2.githubusercontent.com/u/17532418?v=3&s=400" width="100"/>](https://github.com/brenov) | [<img src="https://avatars2.githubusercontent.com/u/17392686?v=3&s=400" width="100"/>](https://github.com/Pekorishia)
---|---
[Breno Viana](https://github.com/brenomfviana) | [Patrícia Cruz](https://github.com/Pekorishia)
