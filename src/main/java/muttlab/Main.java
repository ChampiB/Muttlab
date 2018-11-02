package muttlab;

import javafx.application.Application;
import javafx.stage.Stage;
import muttlab.ui.GraphicalInterface;
import muttlab.ui.UserInterface;

public class Main extends Application {

    /**
     * Entry point of the MuttLab program.
     * @param args : The program's parameters.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Javafx entry point of the MuttLab program.
     * @param primaryStage the top container of javafx.
     */
    @Override
    public void start(Stage primaryStage) {
        UserInterface ui = new GraphicalInterface(primaryStage);
        Thread t = new Thread(() -> {
            new MuttLab(ui).startSession();
            System.exit(0);
        });
        t.setDaemon(true);
        t.start();
    }
}
