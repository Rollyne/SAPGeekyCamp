package InMemoryFileSystem.Services;
import InMemoryFileSystem.Context;
import InMemoryFileSystem.Entities.Abstract.FileOpenMode;
import InMemoryFileSystem.Entities.Abstract.IDirectoryNode;
import InMemoryFileSystem.Entities.Directory;
import InMemoryFileSystem.Entities.File;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.FileLockInterruptionException;
import java.nio.file.FileSystemException;
import java.util.Collection;
import java.util.Objects;

public class Service {

    private Context context;

    public Service(Context context){
        this.context = context;
    }

    public void MakeFile(String name) {

        File file = new File();
        file.setName(name);
        context.getCurrentDirectory().addNode(file);
    }

    public void OpenFile(String name, String mode) throws FileLockInterruptionException, FileNotFoundException {
        File file = FindFile(context.getCurrentDirectory().getNodes(), name);
        if(file == null){
            throw new FileNotFoundException();
        }

        file.open(FileOpenMode.valueOf(mode));
    }

    public void CloseFile(String name) throws FileNotFoundException {
        File file = FindFile(context.getCurrentDirectory().getNodes(), name);
        if(file == null){
            throw new FileNotFoundException();
        }

        file.dispose();
    }

    public void WriteToFile(String name, String text) throws FileNotFoundException, FileSystemException {
        File file = FindFile(context.getCurrentDirectory().getNodes(), name);
        if(file == null){
            throw new FileNotFoundException();
        }
        if(!file.isOpened()){
            throw new FileSystemException("This file is not opened.");
        }
        if(file.getOpenMode() != FileOpenMode.rw){
            throw new FileSystemException("This file is not opened in write mode.");
        }

        file.write(text);
    }

    public void WriteLineToFile(String name, String text) throws FileSystemException, FileNotFoundException {
        File file = FindFile(context.getCurrentDirectory().getNodes(), name);
        if(file == null){
            throw new FileNotFoundException();
        }
        if(!file.isOpened()){
            throw new FileSystemException("This file is not opened.");
        }
        if(file.getOpenMode() != FileOpenMode.rw){
            throw new FileSystemException("This file is not opened in write mode.");
        }

        file.writeLine(text);
    }

    public String ReadFile(String name) throws FileSystemException, FileNotFoundException {
        File file = FindFile(context.getCurrentDirectory().getNodes(), name);
        if(file == null){
            throw new FileNotFoundException();
        }
        if(!file.isOpened()){
            throw new FileSystemException("This file is not opened.");
        }

        return file.readToEnd();
    }

    public void RemoveFile(String name) throws FileSystemException, FileNotFoundException {
        File file = FindFile(context.getCurrentDirectory().getNodes(), name);
        if(file == null){
            throw new FileNotFoundException();
        }
        if(file.isOpened()){
            throw new FileSystemException("This file is currently opened and you cannot delete it.");
        }

        this.context.getCurrentDirectory().removeNode(file);
    }



    public void MakeDirectory(String name){
        Directory dir = new Directory();
        dir.setName(name);
        context.getCurrentDirectory().addNode(dir);
    }

    public Collection<IDirectoryNode> GetNodes(){
        return context.getCurrentDirectory().getNodes();
    }

    public boolean ChangeDirectory(String path) throws InvocationTargetException {

        String[] directoryNames = path.trim().split("/");
        boolean isDirectoryChanged = false;
        for (String name : directoryNames) {

            if (Objects.equals(name, "..")) {
                context.stepOutOfDirectory();
                isDirectoryChanged = true;
            } else {
                Directory dir = FindDirectory(context.getCurrentDirectory().getNodes(), name);
                if(dir == null){
                    isDirectoryChanged = false;
                    break;
                }
                context.stepIntoDirectory(dir);
                isDirectoryChanged = true;
            }
        }
        return isDirectoryChanged;

    }

    public void RemoveDirectory(String name) throws FileSystemException, FileNotFoundException {
        Directory dir = FindDirectory(context.getCurrentDirectory().getNodes(), name);
        if(dir == null){
            throw new FileNotFoundException();
        }
        if(dir.getNodes().size() > 0){
            throw new FileSystemException("You cannot delete a non-empty directory.");
        }

        this.context.getCurrentDirectory().removeNode(dir);
    }

    public Directory FindDirectory(Collection<IDirectoryNode> nodes, String searched){
        return (Directory) nodes
                .stream()
                .filter((node) ->
                        Objects.equals(node.getName(), searched) && node instanceof Directory)
                .findFirst()
                .orElse(null);
    }

    public File FindFile(Collection<IDirectoryNode> nodes, String searched){
        return (File) nodes
                .stream()
                .filter((node) ->
                        Objects.equals(node.getName(), searched) && node instanceof File)
                .findFirst()
                .orElse(null);
    }

////It is not working for some reason even though DirectoryNode is a class
//    public <TDirectoryNode extends DirectoryNode> TDirectoryNode FindNode(Collection<IDirectoryNode> nodes, String searched){
//
//        for(IDirectoryNode node : nodes){
//            if(node instanceof TDirectoryNode){
//                if(Objects.equals(node.getName(), searched)){
//                    return (TDirectoryNode)node;
//                }
//            }
//        }
//        return null;
//    }


}
