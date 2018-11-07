package muttlab;

import javafx.application.Application;
import javafx.stage.Stage;
import muttlab.ui.HomeController;
import muttlab.ui.HomeModel;
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
        // Create the graphical user interface, using the MVC design pattern.
        HomeModel model = new HomeModel();
        HomeController controller = new HomeController(model);
        HomeView view = HomeView.get(controller, model);
        // Create the task manager.
        TasksManager.init(controller);
        // Display the application.
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
