/**
 * An implementation of the Fallout 3 / 4 Hacking Mini Game.
 */
module com.slinky.hackmaster {
    requires javafx.controls;
    exports com.slinky.hackmaster;
    exports com.slinky.hackmaster.controller;
    exports com.slinky.hackmaster.model.cell;
    exports com.slinky.hackmaster.model.text;
    exports com.slinky.hackmaster.view;
    exports com.slinky.hackmaster.util;
}