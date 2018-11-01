# MuttLab

This directory contains the Muttlab project (after refactoring).

The program's entry point is in the Muttlab class: "muttlab.MuttLab".

## How to compile it ?

Execute the shell script: ```./build.sh```.

## How to run it ?

Execute the shell script: ```./run.sh```.

## How to add new commands in the core plugin ?

1 - Add the command name in all the dictionaries ```./dictionaries/plugin-core-*.txt```, e.g. ```TRANSPOSE=transpose```.

2 - Add the key in the ```CoreKeys``` enumeration, e.g. ```TRANSPOSE```.

3 - Add a new entry in the map of the command factory, e.g. ```commands.put(CoreKeys.TRANSPOSE, new CommandTranspose());```.

4 - Implement the command's class and place it in the ```commands``` package, e.g. ```public class CommandTranspose() extends Command { ... }```.

## How to add a new package ?

1 - Create a new module in the project, e.g. ```pro```.

2 - Create a namespace , e.g. ```pro```.

3 - Create and implement a class named ```Plugin``` in the package ```pro```.

4 (Optional) - You are encourage to create a factory of command.

5 (Optional) - You are encourage to abstract the abstract language using dictionary.

For more information about 4 and 5 please refer to the core module implementation.

## How to change the text interface into a graphical interface ?

1 - Create and implement a class that extend the ```UserInterface``` class, e.g. ```class GraphicalInterface extends UserInterface```.

## How to add new type of matrices ?

1 - Create and implement a class that extend the ```Matrix``` class, e.g. ```class SparseMatrix extends Matrix```.
