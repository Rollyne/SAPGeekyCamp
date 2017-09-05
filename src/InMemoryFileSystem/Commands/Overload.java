package InMemoryFileSystem.Commands;

public class Overload {
    private String[] arguments;
    private String description;

    Overload(String[] arguments, String description){
        setArguments(arguments);setDescription(description);
    }
    Overload(){}

    public int countArguments(){
        return arguments.length;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
