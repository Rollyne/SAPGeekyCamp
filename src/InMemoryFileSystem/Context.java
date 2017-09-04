package InMemoryFileSystem;

import InMemoryFileSystem.Entities.Directory;

import java.util.Stack;
import java.util.TreeSet;

public class Context {
    Context(){

        setCurrentDirectory(new Directory(new TreeSet<>(), "root"));

    }
    Context(Directory dir){
        setCurrentDirectory(dir);
    }
    private Stack<String> path;

    private Directory currentDirectory;

    public Directory getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(Directory currentDirectory) {

        this.currentDirectory = currentDirectory;
    }
}
