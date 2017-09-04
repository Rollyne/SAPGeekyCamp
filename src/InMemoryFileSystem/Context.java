package InMemoryFileSystem;

import InMemoryFileSystem.Entities.Abstract.IDirectoryNode;
import InMemoryFileSystem.Entities.Directory;

import java.util.TreeSet;

public class Context {
    Context(){
        currentDirectory = new Directory(new TreeSet<>(), "root");
    }
    Context(Directory dir){
        this.currentDirectory = dir;
    }
    private String currentDirectoryPath;

    private Directory currentDirectory;

    public String getCurrentDirectoryPath() {
        return currentDirectoryPath;
    }

    public void setCurrentDirectoryPath(String currentDirectoryPath) {
        this.currentDirectoryPath = currentDirectoryPath;
    }


    public Directory getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(Directory currentDirectory) {
        this.currentDirectory = currentDirectory;
    }
}
