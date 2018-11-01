package muttlab.ui;

import muttlab.languages.MuttLabDictionary;
import muttlab.languages.MuttLabKeys;

import java.io.InputStream;

public abstract class UserInterface {

    private String prompt = "> ";

    /**
     * Getter method.
     * @return the input stream of the user interface.
     */
    public abstract InputStream getInputStream();

    /**
     * Print out one message on the standard output (without '\n' at the end).
     * @param message : The message to display.
     */
    public abstract void printStd(String message);

    /**
     * Print out one message on the error output (without '\n' at the end).
     * @param message : The message to display.
     */
    public abstract void printErr(String message);

    /**
     * Print out one message on the error output (with '\n' at the end).
     * @param message : The message to display.
     */
    public void printlnErr(String message) { printErr(message + "\n"); }

    /**
     * Print out the array of messages on the error output (with '\n' at the end of each line).
     * @param messages : All the messages to display.
     */
    public void printlnErr(String[] messages) {
        for (String message : messages) { printlnErr(message); }
    }

    /**
     * Print out the array of messages on the standard output (with '\n' at the end of each line).
     * @param messages : All the messages to display.
     */
    public void println(String[] messages) {
        for (String message : messages) { println(message); }
    }

    /**
     * Print out one message on the standard output (with a '\n' at the end).
     * @param message : The message to display.
     */
    public void println(String message) { printStd(message + "\n"); }

    /**
     * Print out the opening message on the standard output.
     */
    public void printWelcome() {
        String welcomeKey = MuttLabKeys.WELCOME_MESSAGE.toString();
        println(MuttLabDictionary.getInstance().getValue(welcomeKey));
    }

    /**
     * Print out the closing message on the standard output.
     */
    public void printGoodbye() {
        String goodByeKey = MuttLabKeys.GOODBYE_MESSAGE.toString();
        println(MuttLabDictionary.getInstance().getValue(goodByeKey));
    }

    /**
     * Print out the prompt on the standard output.
     */
    public void printPrompt() { printStd(getPrompt()); }

    /**
     * Getter method.
     * @return the prompt.
     */
    public String getPrompt() { return this.prompt; }

    /**
     * Setter method.
     * @param n : the prompt.
     */
    public void setPrompt(String n) { this.prompt = n; }
}
