package InMemoryFileSystem;

import InMemoryFileSystem.Entities.Directory;

import java.util.LinkedList;
import java.util.TreeSet;

public class Context {

    private LinkedList<Directory> path;

    Context(){
        this.path = new LinkedList<>();
        stepIntoDirectory(new Directory(new TreeSet<>(), "root"));
    }
    Context(Directory dir){
        this.path = new LinkedList<>();
        stepIntoDirectory(dir);
    }

    public String getCurrentPath(){
        StringBuilder sb = new StringBuilder();
        for(Directory dir : path){
            sb.append(dir.getName()).append("/");
        }

        return sb.toString();
    }

    public Directory getCurrentDirectory() {
        return path.getLast();
    }

    public void stepIntoDirectory(Directory directory) {

        this.path.add(directory);
    }

    public void stepOutOfDirectory(){
        this.path.removeLast();
    }
}
