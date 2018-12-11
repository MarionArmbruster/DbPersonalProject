# DbPersonalProject

The DbPersonalProject is my personal program that incorporates a derby database, IntellJ IDEA IDE and my own creativity, in addition to
many, many sources and outside assistance. It uses Java and JavaFX which is a facet of Java that uses fxml files for formatting a gui
and controller files to determine what objects in the gui do, such as actions that occur due to user input. This program was an 
individual assignment for my COP 3003 class during the Fall 2018 semester and it displays a gui that This program uses the Google Java
Style guide and adheres to GUI Design Principles.

# Demonstration
// TO DO: Add an animated gif. You can use ShareX, GIPHY Capture or another tool. https://blog.github.com/2018-06-29-GIF-that-keeps-on-GIFing/ Note for school project: This is a great way for friends and family to easily see your project in action.

# Getting Started
To run the program, download the package "DbPersonalProject" and open the .java files in an IDE of your choosing. To interact with the
program, run the Main class contained in the package and follow the below instructions:
- When the window pops up, clicking the "Begin Tracking" button with connect you to the database, as displayed in the console and will
transition to the next scene

-On the second scene, you can hit any of the buttons to either log an event in, log an event out, or go to the third scene.
   
   -Logging an event in will cause the system date and time to be inserted into the database, along with the "in" event tag and a unique
  key identifier
   
   -Logging an event out will also cause the system date and time to be inserted into the database, along with the "out" event tag and 
  another unique key identifier
   
   -You can also enjoy the short animation displayed on the screen
   
   -Clicking the "Search" button will transition to the third and final scene

-On the third scene is a "Go back" button so that you can log more events if you wish, as well as several other button and fields.
  - The Month, and Date fields allow you to type into the field to seach the database, or alternatively, you can also use the Type
    drop-down field as a search term
  - The "Show-All" button will display anything and everything that is currently in the database, including the new events you may 
    have just added
  - The "Delete" button can be used just like the "Search" button, but instead and searching, you can delete a record in the 
    database, and then use the "Show-All" button to display what is left in the database
  - The "Log off" button in the corner, which is present on all scenes, is used to exit the program, if you don't want to use the
    big red "X"  
  
# Built With
DbPersonalProject program was built in the Intellij IDEA integrated development environment. It was made using the Java ver. 1.8.0_181.
Resources used: [All about SQL](https://www.w3schools.com/sql/)
                [Regular Expression](https://stackoverflow.com/questions/3148240/why-doesnt-01-12-range-work-as-expected)
                [More Regex](https://www.regular-expressions.info/numericranges.html)  
                [Even more Regex](https://www.oreilly.com/library/view/regular-expressions-cookbook/9781449327453/ch06s07.html)    
                [All about Databases and how to do stuff](https://www.swtestacademy.com/database-operations-javafx/)
                [Changing Scenes](https://stackoverflow.com/questions/16176701/switch-between-panes-in-javafx) as well as Damian Morgan
                [Animation](https://examples.javacodegeeks.com/desktop-java/javafx/javafx-animation-example/)
                [More Animation](https://www.youtube.com/watch?v=VicKcKBso6o)
                [A Transition](here: https://stackoverflow.com/questions/30543619/how-to-use-pausetransition-method-in-javafx)
                And many others that may not have gotten on this list.
# Author
- Developed by Marion Armbruster

# License
MIT License

Copyright (c) 2018 Marion Armbruster. See License.

# Acknowledgments
Web sites utilized: 

Ackowledgements are also due to Professor Vanselow and Damian Morgan for assisting in various instances with the program as well as the
shenanigans introduced by both githib and IntelliJ. 

# Key Programming Concepts Utilized

Key concepts: JavaFX: always has a start method to initialize the first stage and the scene within it, and a Main that launches it. Uses
Controllers and fxml files for the bulk of the program and can use css to separate the style and looks from the rest of the scene. JavaFX
can utilize a Model-view-controller (MVC) design principle and design where the model of the program is spearate from both the view (fxml
file) and the control (controller file) of the program.

JavaFX is much easier to use than Java Swing for some cases. And for this projract, it was much easier than in Swing, as well as being
exciting to watch it happen.
