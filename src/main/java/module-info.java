/**
 * Provides an implementation of the Fallout Hacking Puzzle.
 * This module's functionality is encapsulated in the main class {@code App.java},
 * which serves as the entry point for running the application.
 * <p>
 * The application simulates a grid-based word game, allowing the user to engage 
 * with the game via a graphical user interface. The game mechanics, including 
 * word selection and grid management, are facilitated by key components 
 * such as {@link GameState}, {@link WordSet}, {@link CellManager}, and 
 * {@link MainController}. These components collaboratively handle the game's 
 * logic, word management, and interface interactions.
 * </p>
 */
module com.slinky.hackmaster {
    requires java.sql;
    requires javafx.controls;
    exports com.slinky.hackmaster;

//    Javadoc Exports - Enable when generating javadoc
//    exports com.slinky.hackmaster.model;
//    exports com.slinky.hackmaster.model.cell;
//    exports com.slinky.hackmaster.model.text;
//    exports com.slinky.hackmaster.controller;
//    exports com.slinky.hackmaster.view;
//    exports com.slinky.hackmaster.util;
}
