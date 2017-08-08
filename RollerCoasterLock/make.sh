#!/bin/bash
cd src
# Compile
javac rollercoasterlock/*.java rollercoasterlock/rollercoaster/*.java
# Generate jar
jar cfm ../RollerCoasterLock.jar ../manifest.mf rollercoasterlock/*.class rollercoasterlock/rollercoaster/*.class
