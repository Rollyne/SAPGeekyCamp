package InMemoryFileSystem.Commands;

import java.util.HashMap;

public class Sequences {

    //A HashMap of commands identified with a String key.
    //Each command has Overload[] with Overloads containing String[] with parameters.
    private static HashMap<String, Overload[]> commands;

    public static void Configure() {
        commands = new HashMap<>();

        commands.put("mkfile", new Overload[]{new Overload(new String[]{"name"})});
        commands.put("mkdir", new Overload[]{new Overload(new String[]{"name"})});
        commands.put("exit", new Overload[]{new Overload(new String[]{})});
        commands.put("list", new Overload[]{new Overload(new String[]{})});
        commands.put("cd", new Overload[]{new Overload(new String[]{"path"})});
    }

    public static String[] SplitCommands(String input){
        return input.trim().split(" ");
    }

    public static Command MatchCommand(String input) throws NoSuchMethodException {

        String[] inputVals = SplitCommands(input);
        Overload[] overloads = commands.get(inputVals[0]);

        String[] targetOverloadArguments = null;

        for (Overload overload : overloads) {
            if (overload.countArguments() == inputVals.length - 1) {
                targetOverloadArguments = overload.getArguments();
                break;
            }
        }

        if(targetOverloadArguments == null){
            throw new NoSuchMethodException("Invalid command.");
        }

        Command command = new Command();
        command.setName(inputVals[0]);
        for(int i = 0; i < targetOverloadArguments.length; i++){
            //The first input val is the command name so we start from the second one
            command.addArgument(targetOverloadArguments[i], inputVals[i+1]);
        }

        return command;
    }
}
