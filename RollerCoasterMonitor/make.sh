#!/bin/bash
cd src
# Compile
javac rollercoastermonitor/*.java rollercoastermonitor/rollercoaster/*.java
# Generate jar
jar cfm ../RollerCoasterMonitor.jar ../manifest.mf rollercoastermonitor/*.class rollercoastermonitor/rollercoaster/*.class
