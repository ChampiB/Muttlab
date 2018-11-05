package muttlab.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import muttlab.MuttLab;
import muttlab.commands.CommandTask;
import muttlab.languages.MuttLabStrings;
import muttlab.math.Matrix;

import java.io.File;

public class HomeView {

    private Parent root;
    private HomeController controller;
    private HomeModel model;

    /**
     * Create the home view.
     * @param controller: The home controller.
     * @param model: The home model.
     */
    public HomeView(HomeController controller, HomeModel model) {
        this.controller = controller;
        this.model = model;
        this.root = createView();
    }

    /**
     * Create the view.
     * @return The root node of the home view.
     */
    private Parent createView() {
        // Create the main vertical layout.
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        // Create the tab pane.
        TabPane tabs = createTabs(vBox);
        vBox.getChildren().addAll(tabs);
        vBox.setOnKeyPressed(x -> {
            if (x.getCode().equals(KeyCode.ESCAPE)) {
                System.exit(0);
            }
        });
        return vBox;
    }

    /**
     * Create the tab which display the stack of matrices.
     * @return the tab.
     */
    private Tab createMatricesTab() {
        // Create the list of running tasks.
        ListView<Matrix> list = new ListView<>();
        list.setItems(model.getMatrices());
        // Create the tab.
        Tab tab = new Tab();
        tab.setClosable(false);
        tab.setText(MuttLabStrings.MATRICES_STACK_TAB_NAME.toString());
        tab.setContent(list);
        return tab;
    }

    /**
     * Create the tab which display the running tasks.
     * @return the tab.
     */
    private Tab createTasksTab() {
        // Create the list of running tasks.
        ListView<CommandTask> list = new ListView<>();
        list.setItems(model.getRunningTasks());
        // Create the tab.
        Tab tab = new Tab();
        tab.setClosable(false);
        tab.setText(MuttLabStrings.RUNNING_TASKS_TAB_NAME.toString());
        tab.setContent(list);
        return tab;
    }

    /**
     * Create the tab which display the history of executed tasks.
     * @return the tab.
     */
    private Tab createHistoryTab() {
        // Create the list of running tasks.
        ListView<CommandTask> list = new ListView<>();
        list.setItems(model.getTasksHistory());
        // Create the tab.
        Tab tab = new Tab();
        tab.setClosable(false);
        tab.setText(MuttLabStrings.HISTORY_TAB_NAME.toString());
        tab.setContent(list);
        return tab;
    }

    /**
     * Load the muttlab logo.
     * @return the image containing the logo.
     */
    private ImageView createLogoImage() {
        File file = new File("./src/muttlab/ui/img/muttlab-logo.png");
        Image img = new Image(file.toURI().toString());
        return new ImageView(img);
    }

    /**
     * Create the console output.
     * @param prompt: The command prompt.
     * @return the console output.
     */
    private ScrollPane createConsoleOutput(TextField prompt) {
        // Create the scroll pane.
        ScrollPane scroller = new ScrollPane();
        scroller.setPrefHeight(100);
        scroller.prefWidthProperty().bind(prompt.widthProperty());
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        // Create the output.
        Label output = new Label("");
        output.textProperty().bind(model.getConsoleOutput());
        output.textProperty().addListener((obv, o, n) -> scroller.setVvalue(1.0));
        output.getStyleClass().add("white-label");
        output.getStyleClass().add("console-output");
        output.minWidthProperty().bind(scroller.widthProperty());
        output.minHeightProperty().bind(scroller.heightProperty());
        output.setAlignment(Pos.CENTER_LEFT);
        output.setPadding(new Insets(20));
        // Wrap the out in a ScrollPane.
        scroller.setContent(output);
        return scroller;
    }

    /**
     * Create the user console.
     * @return the console.
     */
    private VBox createConsole() {
        // Create the prompt.
        TextField prompt = new TextField();
        prompt.setPrefWidth(500);
        prompt.setPromptText(MuttLabStrings.COMMAND_PROMPT_HELP_MESSAGE.toString());
        prompt.onKeyPressedProperty().setValue(x -> {
            if (x.getCode().equals(KeyCode.ENTER)) {
                String command = prompt.getText();
                controller.executeCommand(command);
                prompt.setText("");
            }
        });
        // Create the console output.
        ScrollPane output = createConsoleOutput(prompt);
        // Create the console.
        VBox console = new VBox();
        console.getChildren().addAll(output, prompt);
        console.setMaxWidth(1000);
        console.setAlignment(Pos.CENTER);
        console.setSpacing(10);
        return console;
    }

    /**
     * Create the tab which display the command prompt.
     * @return the tab.
     */
    private Tab createPromptTab() {
        // Create the tab.
        Tab tab = new Tab();
        tab.setClosable(false);
        tab.setText(MuttLabStrings.COMMAND_PROMPT_TAB_NAME.toString());
        // Create the muttlab logo.
        ImageView image = createLogoImage();
        // Create the console.
        VBox console = createConsole();
        // Create the tab's content.
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(20);
        content.getChildren().addAll(image, console);
        tab.setContent(content);
        return tab;
    }

    /**
     * Create the tab pane.
     * @return The tab pane containing all the application's tabs.
     */
    private TabPane createTabs(VBox parent) {
        TabPane pane = new TabPane();
        pane.getTabs().addAll(
                createPromptTab(),
                createMatricesTab(),
                createTasksTab(),
                createHistoryTab()
        );
        pane.prefHeightProperty().bind(parent.heightProperty());
        return pane;
    }

    /**
     * Show the home view.
     * @param primaryStage: The primary stage of the JavaFx application.
     */
    public void show(Stage primaryStage) {
        // Build the scene.
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screen.getWidth(), screen.getHeight());
        scene.getStylesheets().add("muttlab/ui/css/style.css");
        // Build the primary stage.
        primaryStage.setScene(scene);
        primaryStage.setTitle(MuttLab.getAppName() + " " + MuttLab.getVersion());
        File file = new File("./src/muttlab/ui/img/muttlab-app-logo.png");
        primaryStage.getIcons().add(new Image(file.toURI().toString()));
        primaryStage.show();
    }
}