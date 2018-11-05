package muttlab.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import muttlab.MuttLab;
import muttlab.languages.MuttLabStrings;

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
        vBox.setOnKeyPressed(x -> {
            if (x.getCode().equals(KeyCode.ESCAPE)) {
                System.exit(0);
            }
        });
        // Create the tab pane.
        TabPane tabs = createTabs(vBox);
        vBox.getChildren().addAll(tabs);
        // Frequently check if we need to run tasks.
        Timeline cron = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.updateTasksQueue();
            }
        }));
        cron.setCycleCount(Timeline.INDEFINITE);
        cron.play();
        return vBox;
    }

    /**
     * Create the tab which display the stack of matrices.
     * @return the tab.
     */
    private Tab createMatricesTab() {
        // Create the list of the stack of matrices.
        VBox vBox = createPrettyListView("Stack", model.getMatrices(), 1080, 720);
        // Create the tab's content.
        VBox wrapper = new VBox();
        wrapper.setAlignment(Pos.CENTER);
        wrapper.getChildren().add(vBox);
        // Create the tab.
        Tab tab = new Tab();
        tab.setClosable(false);
        tab.setText(MuttLabStrings.MATRICES_STACK_TAB_NAME.toString());
        tab.setContent(wrapper);
        return tab;
    }

    /**
     * Create a pretty ListView.
     * @param observableList: The observable list to display.
     * @param <T>: The type of the observable list .
     * @return a wrapper around the list view.
     */
    private <T> VBox createPrettyListView(String title, ObservableList<T> observableList, int width, int height) {
        // Create the title.
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("white-label");
        // Create the list view.
        ListView<T> list = new ListView<>();
        list.setPrefHeight(height - 4);
        list.setPrefWidth(width - 4);
        list.setItems(observableList);
        // Wrap the list inside a scroll pane.
        ScrollPane sp = new ScrollPane();
        sp.setContent(list);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.widthProperty().addListener((ov, o, n) -> list.setPrefWidth(width - 4));
        sp.heightProperty().addListener((ov, o, n) -> list.setPrefHeight(height - 20));
        sp.setContent(list);
        sp.setPrefHeight(height);
        sp.setPrefWidth(width);
        // Wrap the scroll pane inside a VBox.
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(titleLabel, sp);
        vBox.setMaxWidth(width);
        vBox.setMaxHeight(height);
        return vBox;
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
     * @return the console output.
     */
    private VBox createConsoleOutput(int width, int height) {
        // Create the title.
        Label titleLabel = new Label("Console output");
        titleLabel.getStyleClass().add("white-label");
        // Create the scroll pane.
        ScrollPane scroller = new ScrollPane();
        scroller.setPrefHeight(height);
        scroller.setPrefWidth(width);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        // Create the output.
        Label output = new Label("");
        output.textProperty().bind(model.getConsoleOutput());
        output.textProperty().addListener((obv, o, n) -> scroller.setVvalue(1.0));
        output.getStyleClass().add("white-label");
        output.getStyleClass().add("console-output");
        output.setMinHeight(height - 4);
        output.setMinWidth(width - 4);
        output.setAlignment(Pos.CENTER_LEFT);
        output.setPadding(new Insets(20));
        // Wrap the out in a ScrollPane.
        scroller.setContent(output);
        // Create console output.
        VBox consoleOutput = new VBox();
        consoleOutput.setAlignment(Pos.CENTER);
        consoleOutput.getChildren().addAll(titleLabel, scroller);
        return consoleOutput;
    }

    /**
     * Create the command prompt.
     * @return the prompt.
     */
    private TextField createPrompt() {
        // Create the prompt.
        TextField prompt = new TextField();
        prompt.setPrefWidth(500);
        prompt.setMaxWidth(500);
        prompt.setPromptText(MuttLabStrings.COMMAND_PROMPT_HELP_MESSAGE.toString());
        prompt.onKeyPressedProperty().setValue(x -> {
            if (x.getCode().equals(KeyCode.ENTER)) {
                String command = prompt.getText();
                model.appendInConsoleOutput("> " + command + "\n");
                controller.handleNewCommand(command);
                prompt.setText("");
            }
        });
        return prompt;
    }

    /**
     * Create the body content.
     * @return the body content.
     */
    private VBox createBodyContent() {
        // Create the muttlab logo.
        ImageView image = createLogoImage();
        // Create the list of running tasks.
        VBox tasks = createPrettyListView("Running tasks", model.getRunningTasks(), 530, 300);
        // Create the list of tasks history.
        VBox history = createPrettyListView("History", model.getTasksHistory(), 530, 300);
        // Create the console output.
        VBox console = createConsoleOutput(1080, 150);
        // Create horizontal panel.
        HBox hb = new HBox();
        hb.getChildren().addAll(tasks, history);
        hb.setSpacing(20);
        hb.setAlignment(Pos.CENTER);
        // Create the command prompt.
        TextField prompt = createPrompt();
        // Create the tab's content.
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.setMaxWidth(1080);
        vBox.getChildren().addAll(image, hb, console, prompt);
        return vBox;
    }

    /**
     * Create the tab which display the command prompt.
     * @return the tab.
     */
    private Tab createPromptTab() {
        // Create body.
        VBox body = new VBox();
        body.setAlignment(Pos.CENTER);
        body.getChildren().add(createBodyContent());
        // Create the tab.
        Tab tab = new Tab();
        tab.setClosable(false);
        tab.setText(MuttLabStrings.COMMAND_PROMPT_TAB_NAME.toString());
        tab.setContent(body);
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
                createMatricesTab()
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