package com.codinwithslinky.terminaltakedown;

import com.codinwithslinky.terminaltakedown.gui.MainInterface;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The {@code App} class serves as the entry point for the Terminal Takedown
 * application. It extends {@code javafx.application.Application} and provides
 * the main setup and launch for the JavaFX application.
 *
 * <p>
 * This class is responsible for initialising the primary stage and setting the
 * initial scene, which in this case is the {@code MainInterface}. The
 * application is launched using the {@code main} method, which calls the
 * {@code launch} method to start the JavaFX application lifecycle.</p>
 *
 * <p>
 * Typical usage involves running the application, which triggers the
 * {@code start} method, creating the main window (stage) and displaying the
 * initial user interface.</p>
 *
 * @author Kheagen Haskins
 */
public class App extends Application {

    /**
     * The entry point for the JavaFX application. This method is called after
     * the application is launched and is responsible for setting up the primary
     * stage and scene.
     *
     * @param stage the primary stage for this application, onto which the
     * application scene can be set.
     */
    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new MainInterface()));
        stage.show();
    }

    /**
     * The main method that serves as the entry point for the application. It
     * calls the {@code launch} method to start the JavaFX application
     * lifecycle.
     *
     * @param args command-line arguments passed to the application (not used in
     * this implementation).
     */
    public static void main(String[] args) {
        launch();
    }

}