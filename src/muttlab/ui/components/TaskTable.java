package muttlab.ui.components;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import muttlab.commands.CommandTask;

import java.util.List;

public class TaskTable {

    private ObservableList<CommandTask> content;
    private List<TableColumn> columns;
    private int width;
    private int height;
    private String title;
    private String placeholder;

    public TaskTable() {}

    public TaskTable setContent(ObservableList<CommandTask> content) {
        this.content = content;
        return this;
    }

    public TaskTable setColumns(List<TableColumn> columns) {
        this.columns = columns;
        return this;
    }

    public TaskTable setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public TaskTable setTitle(String title) {
        this.title = title;
        return this;
    }

    public TaskTable setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public VBox build() {
        // Create the title.
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("white-label");
        // Create the list view.
        TableView<CommandTask> table = new TableView<>();
        Label label = new Label(placeholder);
        label.getStyleClass().add("white-label");
        table.setPlaceholder(label);
        table.setPrefHeight(height - 4);
        table.setPrefWidth(width - 4);
        table.setItems(content);
        for (TableColumn col: columns) {
            table.getColumns().add(col);
        }
        // Wrap the list inside a scroll pane.
        ScrollPane sp = new ScrollPane();
        sp.setContent(table);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.widthProperty().addListener((ov, o, n) -> table.setPrefWidth(width - 4));
        sp.heightProperty().addListener((ov, o, n) -> table.setPrefHeight(height - 20));
        sp.setContent(table);
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
}