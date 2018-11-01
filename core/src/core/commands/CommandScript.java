package core.commands;

import core.languages.CoreDictionary;
import core.languages.CoreKeys;
import muttlab.MuttLab;
import muttlab.math.Element;
import muttlab.parsing.Parser;
import muttlab.parsing.SimpleParser;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Stack;

public class CommandScript extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandScript(String command) {
        setCommand(command);
    }

    /**
     * Execute the script contains in the file passed as (command) parameter.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) {
        String[] parameters = getCommand().split(" ");
        if (parameters.length < 1) {
            String errorMessage = CoreKeys.FILE_NAME_NOT_FOUND_ERROR_MESSAGE.toString();
            ui.printlnErr(CoreDictionary.getInstance().getValue(errorMessage));
            return false;
        }
        boolean finished = false;
        FileInputStream fileReader = null;
        try {
            fileReader = new FileInputStream(new File(parameters[1]));
            Parser parser = new SimpleParser(fileReader);
            finished = MuttLab.executeUserCommands(parser, ui, elements, true);
        } catch (IOException e) {
            String errorMessage = CoreKeys.SAVE_CANT_WRITE_IN_FILE_ERROR_MESSAGE.toString();
            ui.printlnErr(CoreDictionary.getInstance().getValue(errorMessage));
        } finally {
            if (fileReader != null) {
                try { fileReader.close(); } catch (Exception e) {}
            }
        }
        return finished;
    }
}
