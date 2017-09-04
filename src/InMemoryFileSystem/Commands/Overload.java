package InMemoryFileSystem.Commands;

public class Overload {
    private String[] arguments;

    Overload(String[] arguments){
        setArguments(arguments);
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
}
