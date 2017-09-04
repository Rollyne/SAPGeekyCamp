package InMemoryFileSystem.Commands;

import java.util.HashMap;

public class Command{
    private String name;
    private HashMap<String, String> arguments;

    Command(){
        arguments = new HashMap<>();
    }
    Command(HashMap<String, String> arguments){
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addArgument(String key, String value){
        arguments.put(key, value);
    }

    public HashMap<String, String> getArguments() {
        return arguments;
    }

    public void setArguments(HashMap<String, String> arguments) {
        this.arguments = arguments;
    }
}
