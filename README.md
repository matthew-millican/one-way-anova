# One-way ANOVA Calculator

## Purpose

This was a side-project from my school work which involved carrying out one-way ANOVA statistical tests on sets of data.

The code automates the calculations of the mean squares and F-ratio. 

##  How To Run

Simply clone the repository and compile the code (assuming a JDK is installed).

My machine successfully compiled and executed the code with:

openjdk version "17.0.4.1" 2022-08-12 LTS
OpenJDK Runtime Environment Corretto-17.0.4.9.1 (build 17.0.4.1+9-LTS)
OpenJDK 64-Bit Server VM Corretto-17.0.4.9.1 (build 17.0.4.1+9-LTS, mixed mode, sharing)

Compile with:

`$ javac *.java`

Then run with:

`$ java Calculator.java <file-name>`

## Inputs

The program accepts a single argument which is a relative filename. The file should be a CSV file with a given number of columns, and a consistent number
  of rows across the columns. E.g., 
 | 1| 2|
 |--|--|
 |21|22|
 |23|24|
 |20|19|
 |20|22|
  
  ## Program Limitations 
  
The program does not calculate the critical value at which the null hypothesis can be rejected however, improvements could be made to the program to accept an alpha value and calculate the critcal value, thus displaying whether the null hypothesis holds or not.
  
  
