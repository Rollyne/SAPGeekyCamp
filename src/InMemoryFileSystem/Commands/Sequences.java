package InMemoryFileSystem.Commands;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Sequences {

    //A HashMap of commands identified with a String key.
    //Each command has Overload[] with Overloads containing String[] with parameters.
    private static HashMap<String, Overload[]> commands;

    public static void Configure() {
        commands = new HashMap<>();

        commands.put("mkfile", new Overload[]{new Overload(new String[]{"name"}, "Creates a new file.")});
        commands.put("mkdir", new Overload[]{new Overload(new String[]{"name"}, "Creates a new directory.")});
        commands.put("exit", new Overload[]{new Overload(new String[]{}, "Exits the application.")});
        commands.put("list", new Overload[]{new Overload(new String[]{}, "Lists all files and directories in the current directory.")});
        commands.put("cd", new Overload[]{new Overload(new String[]{"path"}, "Changes the directory. Use '..' for going backwards.")});
        commands.put("openf", new Overload[]{new Overload(new String[]{"name", "mode"}, "Opens a file in read(r)/read&write(rw) mode. Use 'r' for readonly and 'rw' for read and write.")});
        commands.put("closef", new Overload[]{new Overload(new String[]{"name"}, "Closes a file.")});
        commands.put("write", new Overload[]{new Overload(new String[]{"name", "text"}, "Writes plain text to a file. Write a '#' before the text input")});
        commands.put("writeln", new Overload[]{new Overload(new String[]{"name", "text"}, "Writes a new line to a file. Write a '#' before the text input")});
        commands.put("read", new Overload[]{new Overload(new String[]{"name"}, "Reads the whole text from a file.")});
        commands.put("rmf", new Overload[]{new Overload(new String[]{"name"}, "Removes a folder")});
        commands.put("rmdir", new Overload[]{new Overload(new String[]{"name"}, "Removes a directory.")});
        commands.put("rename", new Overload[]{new Overload(new String[]{"name", "newName"}, "Renames a file or a folder.")});
        commands.put("help", new Overload[]{
                new Overload(new String[]{"command"}, "Gives information for certain command and its overloads."),
                new Overload(new String[]{}, "Gives information for commands and their overloads")

        });

    }

    public static HashMap<String, Overload[]> getCommands(){
        return commands;
    }

    public static List<String> SplitCommands(String input){
        //In order to separate text(containing white space) from commands we use special character "#"
        String[] inputSpecialTextSplitted = input.trim().split("#", 2);

        List<String> cmds = new ArrayList<>(Arrays.asList(inputSpecialTextSplitted[0].trim().split(" ")));

        if(inputSpecialTextSplitted.length > 1){
            cmds.add(inputSpecialTextSplitted[1]);
        }

        return cmds;
    }

    public static Command MatchCommand(String input) throws NoSuchMethodException {

        List<String> inputVals = SplitCommands(input);
        Overload[] overloads = commands.get(inputVals.get(0));

        String[] targetOverloadArguments = null;

        if(overloads == null){
            throw new NoSuchMethodException();
        }
        for (Overload overload : overloads) {
            if (overload.countArguments() == inputVals.size() - 1) {
                targetOverloadArguments = overload.getArguments();
                break;
            }
        }

        if(targetOverloadArguments == null){
            throw new NoSuchMethodException("Invalid command.");
        }

        Command command = new Command();
        command.setName(inputVals.get(0));
        for(int i = 0; i < targetOverloadArguments.length; i++){
            //The first input val is the command name so we start from the second one
            command.addArgument(targetOverloadArguments[i], inputVals.get(i+1));
        }

        return command;
    }
}
