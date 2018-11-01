import muttlab.MuttLab;
import muttlab.math.Element;
import muttlab.parsing.Parser;
import muttlab.parsing.SimpleParser;
import muttlab.ui.TextInterface;
import muttlab.ui.UserInterface;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GlobalScriptTest {

    private List<String> getFoldersNameForFolder(final File folder) {
        List<String> foldersName = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                foldersName.add(fileEntry.getName());
            }
        }
        return foldersName;
    }

    private void executeScript(File scriptFile) throws Exception {
        Parser parser = new SimpleParser(new FileInputStream(scriptFile));
        UserInterface ui = new TextInterface();
        Stack<Element> elements = new Stack<>();
        MuttLab.executeUserCommands(parser, ui, elements, true);
    }

    private void checkOutput(File outputFile, File resultFile) throws Exception {
        BufferedReader output = new BufferedReader(new FileReader(outputFile));
        BufferedReader result = new BufferedReader(new FileReader(resultFile));
        String outputLine = "";
        String resultLine = "";
        while (outputLine != null || resultLine != null) {
            outputLine = output.readLine();
            resultLine = result.readLine();
            Assert.assertEquals(outputLine, resultLine);
        }
    }

    @Test
    public void runResourcesTests() {
        String resourcesPath = "./resources";
        final File folder = new File(resourcesPath);
        List<String> foldersName = getFoldersNameForFolder(folder);
        for (String folderName: foldersName) {
            File scriptFile = new File(resourcesPath + "/" + folderName + "/script.txt");
            File outputFile = new File(resourcesPath + "/" + folderName + "/output.txt");
            File resultFile = new File("./result.txt");
            try {
                executeScript(scriptFile);
                checkOutput(outputFile, resultFile);
            } catch (Exception e) {
                System.err.println(scriptFile);
                System.err.println(e.getMessage());
                Assert.fail();
            } finally {
                resultFile.delete();
            }
        }
    }
}
