# RestaurantManager

A Restaurant Manager Project for University

# Environmental Requirements

* Java version 1.8 or higher

# Install

Run the file [compile.bat](https://github.com/SDiCesare/RestaurantManager/blob/master/compile.bat) in the directory.

The file should compile all the .java file in the src/ folder, and store them in the directory "bin", and create the
file "RestaurantManager.jar"

### In Details

The bat file:

* Deletes the bin directory if presents
* Copy The assets files from src/assets to bin/assets
* Compiles all the .java files from src/*, and saves them in bin/*
* Via the file [MANIFEST.MF](https://github.com/SDiCesare/RestaurantManager/blob/master/src/META-INF/MANIFEST.MF)
  creates the RestaurantManager.jar, using the .class files in the bin directory

# Start

Then, for running the generated jar you could run the
file [run.bat](https://github.com/SDiCesare/RestaurantManager/blob/master/run.bat) in the directory.
In alternative, the program can be opened via cmd running the command:

```bash
java -cp RestaurantManager.jar main.Main [OPTIONS]
```

# OPTIONS

    -m, -menu MENUFILE              Choose from which file the Menu is read.

# Bug

* If you try to load a different menu file when starting the program, while having active orders, the program may
  be launch a NullPointerException. This happens because if the menu loaded previously has more MenuScopes
  than the second Menu, the program maybe try to load a MenuScopes with index higher than the length of the second menu.
  For this reason, It is no longer possible to open the CookPanel/CashierPanel after this, unless the old menu is loaded
  or the orders are cancelled.

* If a MenuScope is deleted from the Menu, while having active orders, the program may be launch a NUllPointerException.
  This happens because if the MenuScope deleted was present in an order, the program will try to load a MenuScope with
  an ID that no longer exists, so it will launch a NullPointerException.
  For this reason, It is no longer possible to open the CookPanel/CashierPanel, unless all the orders are cancelled.

For Other Bug encountered, please write an Issues. It will be solved as soon as possible.
