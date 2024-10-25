#!/bin/bash

# Check if Xvfb is installed, if not, install it (for Debian/Ubuntu)
if ! command -v Xvfb &> /dev/null
then
    echo "Xvfb could not be found, please install it."
    exit
fi

# Start Xvfb on display :99
Xvfb :99 -screen 0 800x600x16 &

# Set DISPLAY variable
export DISPLAY=:99

# Compile the Java program
javac BasicPlatformer.java

# Run the Java program
