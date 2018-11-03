package muttlab;

import muttlab.exceptions.UnknownCommand;
import muttlab.languages.Language;
import muttlab.languages.LanguageObservable;
import muttlab.languages.MuttLabDictionary;
import muttlab.languages.MuttLabKeys;
import muttlab.math.Element;
import muttlab.parsing.Parser;
import muttlab.parsing.SimpleParser;
import muttlab.plugins.Command;
import muttlab.plugins.Plugin;
import muttlab.plugins.PluginsManager;
import muttlab.ui.UserInterface;

import java.util.Iterator;
import java.util.Stack;

public class MuttLab {

    private UserInterface ui;
    private Parser parser;
    private Stack<Element> elements;

    /**
     * Constructor.
     * @param ui: The user interface to use.
     */
    public MuttLab(UserInterface ui) {
        this.ui = ui;
        this.parser = new SimpleParser(ui.getInputStream());
        this.elements = new Stack<>();
    }

    /**
     * This function create the links between observable and observers.
     * Then, it execute the user's commands.
     */
    public void startSession() {
        // Make sure that the PluginsManager and UserInterface display messages in english.
        LanguageObservable.get().addObserver(PluginsManager.get());
        LanguageObservable.get().addObserver(MuttLabDictionary.getInstance());
        LanguageObservable.get().changeLanguage(Language.ENGLISH);
        // Execute the user's command.
        ui.printWelcome();
        MuttLab.executeUserCommands(parser, ui, elements, false);
        ui.printGoodbye();
    }

    /**
     * Execute the user's commands.
     * @param parser : The parser that return the next command to execute.
     * @param ui : The user interface to use for displaying messages to the final user.
     * @param elements : The current stack of elements.
     * @param displayCommand : If true the command will be display before to be executed.
     * @return true if the program must exit and false otherwise.
     */
    public static boolean executeUserCommands(
        Parser parser,
        UserInterface ui,
        Stack<Element> elements,
        boolean displayCommand
    ) {
        boolean finished = false;
        boolean endOfStream = false;
        while (!finished && !endOfStream) {
            ui.printPrompt();
            try {
                Iterator<Plugin> plugins = PluginsManager.get().getAvailablePlugins();
                Command command = parser.getNextCommand(plugins);
                if (displayCommand)
                    ui.println(command.getCommand());
                finished = command.exec(ui, elements);
            } catch (java.util.NoSuchElementException e){
                endOfStream = true;
            } catch (UnknownCommand e) {
                String errorMessage = MuttLabKeys.UNKNOWN_COMMAND_MESSAGE.toString();
                ui.printlnErr(MuttLabDictionary.getInstance().getValue(errorMessage));
            } catch (Exception e) {
                ui.printlnErr(e.getMessage());
            }
        }
        return finished;
    }
}
