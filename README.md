# MuttLab

This directory contains the Muttlab project.

The program's entry point is in the following class: "muttlab.Main".

_Author: __Théophile Champion__._

## How to add new commands in the core plugin ?

1 - Implement the command's class and place it in the ```commands``` package, e.g. ```public class CommandTranspose() extends Command { ... }```.

2 - Add a new entry in the map of the command factory, e.g. ```commands.put(MuttLabStrings.TRANSPOSE, new CommandTranspose());```.

## How to add a new package ?

1 - Create a new module in the project, e.g. ```pro```.

2 - Create a namespace , e.g. ```pro```.

3 - Create and implement a class named ```Plugin``` in the package ```pro```.

4 (Optional) - You are encourage to create a factory of command.

5 (Optional) - You are encourage to store all the strings displayed to the use inside of the MuttLabString enumeration.

For more information about 4 and 5 please refer to the core module implementation.

## How to add new type of matrices ?

1 - Create and implement a class that extend the ```Matrix``` class, e.g. ```class SparseMatrix extends Matrix```.
