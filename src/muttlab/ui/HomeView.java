package muttlab.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import muttlab.MuttLab;
import muttlab.commands.Command;
import muttlab.commands.CommandTask;
import muttlab.languages.MuttLabStrings;
import muttlab.ui.components.TaskTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeView {

    private Parent root;
    private HomeController controller;
    private HomeModel model;
    private TextField prompt;
    private Stage primaryStage;

    /**
     * Singleton design pattern.
     */
    private static HomeView instance = null;

    public static HomeView get() { return get(null, null); }
    public static HomeView get(HomeController controller, HomeModel model) {
        if (instance == null && controller != null && model != null)
            instance = new HomeView(controller, model);
        return instance;
    }

    private HomeView(HomeController controller, HomeModel model) {
        this.controller = controller;
        this.model = model;
        this.root = createView();
    }

    /**
     * Ask the user to choose a file an return it's name.
     * @return the file name of the file choose by the user.
     */
    public String pickFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(MuttLabStrings.CHOOSE_FILE.toString());
        File file = fileChooser.showOpenDialog(primaryStage);
        return (file == null) ? "" : file.getPath();
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
        return vBox;
    }

    /**
     * Create the tab which display the stack of matrices.
     * @return the tab.
     */
    private Tab createMatricesTab() {
        // Create the list of the stack of matrices.
        VBox vBox = createPrettyStack(MuttLabStrings.STACK.toString(), model.getMatrices(), 1080, 720);
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
     * Create a pretty stack.
     * @param observableList: The observable list to display.
     * @param <T>: The type of the observable list .
     * @return a wrapper around the list view.
     */
    private <T> VBox createPrettyStack(String title, ObservableList<T> observableList, int width, int height) {
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
        Label titleLabel = new Label(MuttLabStrings.CONSOLE_OUTPUT.toString());
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
        prompt = new TextField();
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
     * Getter.
     * @param width: The column's width.
     * @return the name column.
     */
    private TableColumn getNameColumn(int width) {
        // Create the name column.
        TableColumn nameCol = new TableColumn<Command, String>(MuttLabStrings.NAME.toString());
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        nameCol.getStyleClass().add("center-left-align");
        nameCol.setPrefWidth(width);
        return nameCol;
    }

    /**
     * Getter.
     * @param width: The column's width.
     * @return the name column.
     */
    private TableColumn getStatusColumn(int width) {
        // Create the status column.
        TableColumn statusCol = new TableColumn<CommandTask, Image>(MuttLabStrings.STATUS.toString());
        statusCol.setCellValueFactory(new PropertyValueFactory("status"));
        statusCol.getStyleClass().add("center-align");
        statusCol.setPrefWidth(width);
        return statusCol;
    }

    /**
     * Getter.
     * @param width: The column's width.
     * @return the name column.
     */
    TableColumn getOutputColumn(int width) {
        // Create the output column.
        TableColumn outputCol = new TableColumn<CommandTask, Image>(MuttLabStrings.OUTPUT.toString());
        outputCol.setCellValueFactory(new PropertyValueFactory("output"));
        outputCol.getStyleClass().add("center-left-align");
        outputCol.setMinWidth(width);
        return outputCol;
    }

    /**
     * Getter.
     * @param width: The column's width.
     * @return the column that contains button to start task.
     */
    TableColumn getRunButtonColumn(int width) {
        // Create the output column.
        TableColumn runButtonCol = new TableColumn<CommandTask, Button>(MuttLabStrings.RUN.toString());
        runButtonCol.setCellValueFactory(new PropertyValueFactory("runButton"));
        runButtonCol.getStyleClass().add("center-align");
        runButtonCol.setMinWidth(width);
        return runButtonCol;
    }

    /**
     * Getter.
     * @param width: The width of the table.
     * @return the columns of the history table.
     */
    private List<TableColumn> getColumnsOfHistoryTable(int width) {
        // Create the columns.
        List<TableColumn> columns = new ArrayList<>();
        columns.add(getNameColumn(150));
        columns.add(getStatusColumn(100));
        columns.add(getOutputColumn(width - 256));
        return columns;
    }

    /**
     * Create a pretty task table.
     * @param width: The table's width.
     * @param height: The table's height.
     * @return a wrapper around the list view.
     */
    private VBox createHistoryTaskTable(int width, int height) {
        // Get the table's column.
        List<TableColumn> columns = getColumnsOfHistoryTable(width);
        // Build the table.
        TaskTable builder = new TaskTable()
                .setTitle(MuttLabStrings.HISTORY.toString())
                .setPlaceholder(MuttLabStrings.TABLE_IS_EMPTY.toString())
                .setSize(width, height)
                .setContent(model.getTasksHistory())
                .setColumns(columns);
        return builder.build();
    }

    /**
     * Getter.
     * @param width: The width of the table.
     * @return the columns of the history table.
     */
    private List<TableColumn> getColumnsOfRunningTable(int width) {
        // Create the columns.
        List<TableColumn> columns = new ArrayList<>();
        columns.add(getNameColumn(width - 206));
        columns.add(getStatusColumn(100));
        columns.add(getRunButtonColumn(100));
        return columns;
    }

    /**
     * Create a pretty task table.
     * @param width: The table's width.
     * @param height: The table's height.
     * @return a wrapper around the list view.
     */
    private VBox createRunningTaskTable(int width, int height) {
        // Get the table's column.
        List<TableColumn> columns = getColumnsOfRunningTable(width);
        // Build the table.
        TaskTable builder = new TaskTable()
                .setTitle(MuttLabStrings.RUNNING_TASKS.toString())
                .setPlaceholder(MuttLabStrings.TABLE_IS_EMPTY.toString())
                .setSize(width, height)
                .setContent(model.getRunningTasks())
                .setColumns(columns);
        return builder.build();
    }

    /**
     * Create the body content.
     * @return the body content.
     */
    private VBox createBodyContent() {
        // Tables' size.
        int width = 530;
        int height = 300;
        // Create components.
        ImageView image = createLogoImage();
        VBox tasks = createRunningTaskTable(width, height);
        VBox history = createHistoryTaskTable(width, height);
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
        this.primaryStage = primaryStage;
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
        Platform.runLater(() -> prompt.requestFocus());
    }
}