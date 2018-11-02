package muttlab.ui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import muttlab.config.DefaultConfig;
import muttlab.loggers.Logging;
import muttlab.loggers.LoggingLevel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class GraphicalInterface extends UserInterface {

    private PipedInputStream in;
    private PipedOutputStream out;
    private TextArea console;

    /**
     * Constructor.
     * @param primaryStage the top container of JavaFx.
     */
    public GraphicalInterface(Stage primaryStage) {

        // Input redirection.
        try {
            in = new PipedInputStream();
            out = new PipedOutputStream(in);
        } catch (Exception e) {
            Logging.log(LoggingLevel.ERROR, e.getMessage());
            System.exit(0);
        }

        // Set the application title.
        primaryStage.setTitle(DefaultConfig.getApplicationName());

        // Build the list of consume position.
        List<Function<KeyEvent, Boolean>> cond = new ArrayList<>();
        cond.add(ke -> ke.getCode().equals(KeyCode.HOME) || ke.getCode().equals(KeyCode.PAGE_UP));
        cond.add(ke -> ke.getCode().equals(KeyCode.UP));
        cond.add(ke -> ke.getCode().equals(KeyCode.LEFT) && console.getCaretPosition() <= posOfLastPrompt());
        cond.add(ke -> ke.getCode().equals(KeyCode.BACK_SPACE) && console.getCaretPosition() <= posOfLastPrompt());

        // Create the console.
        console = new TextArea();
        console.setStyle("-fx-text-fill: black;");
        console.onKeyPressedProperty().setValue(x -> {
            // Ensure that the user can't leave the edition area (on key pressed).
            if (
                console.getCaretPosition() < posOfLastPrompt()
                || x.getCode().equals(KeyCode.HOME)
                || x.getCode().equals(KeyCode.PAGE_UP)
            ) {
                console.positionCaret(posOfLastPrompt());
            }
            consumeIf(x, cond);
            // Enter was entered, handle the command.
            if (x.getCode().equals(KeyCode.ENTER)) {
                try {
                    String lastLine = console.getText().substring(posOfLastPrompt());
                    out.write(lastLine.getBytes());
                    out.write('\n');
                } catch (Exception io) {
                    // Do nothing.
                }
            }
        });
        // Ensure that the user can't leave the edition area (on click).
        console.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                console.positionCaret(console.getLength());
            }
        });

        // Create the main layout and show the window.
        StackPane root = new StackPane();
        root.setAlignment(Pos.BOTTOM_CENTER);
        root.getChildren().add(console);
        primaryStage.setScene(new Scene(root, 1080, 720));
        primaryStage.show();
    }

    /**
     * Consume the event if at least one of the condition is true.
     * @param ke: the event to consume.
     * @param cond: the list of conditions.
     */
    private void consumeIf(KeyEvent ke, List<Function<KeyEvent, Boolean>> cond) {
        for (Function<KeyEvent, Boolean> c : cond) {
            if (c.apply(ke)) {
                ke.consume();
                break;
            }
        }
    }

    /**
     * Getter method.
     * @return the input stream of the user interface.
     */
    public InputStream getInputStream() {
        return in;
    }

    /**
     * Return the position of the last prompt.
     * @return the position of the last prompt.
     */
    private int posOfLastPrompt() {
        String[] lines = console.getText().split("\n");
        if (lines.length == 0)
            return 0;
        int length = console.getText().length();
        String lastLine = lines[lines.length - 1];
        return length - (lastLine.length() - getPrompt().length());
    }

    /**
     * Print the message in the console.
     * @param message: the message to print.
     */
    private void printInConsole(String message) {
        console.setText(console.getText() + message);
        console.positionCaret(console.getLength());
    }

    /**
     * Print out one message on the standard output (without '\n' at the end).
     * @param message : The message to display.
     */
    public void printStd(String message) {
        printInConsole(message);
    }

    /**
     * Print out one message on the error output (without '\n' at the end).
     * @param message : The message to display.
     */
    public void printErr(String message) {
        printInConsole(message);
    }
}