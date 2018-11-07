package muttlab;

import javafx.application.Application;
import javafx.stage.Stage;
import muttlab.ui.HomeView;
import muttlab.ui.TasksManager;

public class Main extends Application {
    /**
     * Start method of the JavaFx project.
     * @param primaryStage: The primary stage of the JavaFx application.
     * @throws Exception if an error occurred.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create the task manager.
        TasksManager.init();
        // Create the graphical user interface, using the MVC design pattern.
        HomeView view = HomeView.get();
        view.show(primaryStage);
    }

    /**
     * Entry point of the MuttLab application.
     * @param args: The application's arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
