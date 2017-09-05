package InMemoryFileSystem.Services;
import InMemoryFileSystem.Context;
import InMemoryFileSystem.Entities.Abstract.DirectoryNode;
import InMemoryFileSystem.Entities.Abstract.FileOpenMode;
import InMemoryFileSystem.Entities.Abstract.IDirectoryNode;
import InMemoryFileSystem.Entities.Directory;
import InMemoryFileSystem.Entities.File;
import InMemoryFileSystem.Messages;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.FileLockInterruptionException;
import java.nio.file.FileSystemException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

public class Service {

    private Context context;

    public Service(Context context){
        this.context = context;
    }

    private boolean isNameInDirectory(String name){
        return name.lastIndexOf("/") > 0;
    }

    private String processName(String name) throws InvocationTargetException {
        int lastSlash = name.lastIndexOf("/");
        String actualName = name.substring(lastSlash+1);
        this.ChangeDirectory(name.substring(0, lastSlash));
        return actualName;
    }

    private RemoteNodeAccess loadAccessArgs(String name) throws InvocationTargetException {
        RemoteNodeAccess args = new RemoteNodeAccess();
        args.targetDirectory = context.getCurrentDirectory();
        args.actualName = name;
        if(isNameInDirectory(name)){
            LinkedList<Directory> initialDir = new LinkedList<>(context.getRawPath());

            args.actualName = processName(name);
            args.targetDirectory = context.getCurrentDirectory();

            context.setPath(initialDir);
        }
        return args;
    }

    public void MakeFile(String name) throws InvocationTargetException {

        RemoteNodeAccess args = loadAccessArgs(name);

        File file = new File();
        file.setName(args.actualName);
        args.targetDirectory.addNode(file);
    }

    public void OpenFile(String name, String mode) throws FileLockInterruptionException, FileNotFoundException, InvocationTargetException {

        RemoteNodeAccess args = loadAccessArgs(name);

        File file = FindFile(args.targetDirectory.getNodes(), args.actualName);
        if(file == null){
            throw new FileNotFoundException();
        }

        file.open(FileOpenMode.valueOf(mode));
    }

    public void CloseFile(String name) throws FileNotFoundException, InvocationTargetException {
        RemoteNodeAccess args = loadAccessArgs(name);

        File file = FindFile(args.targetDirectory.getNodes(), args.actualName);
        if(file == null){
            throw new FileNotFoundException();
        }

        file.dispose();
    }

    public void WriteToFile(String name, String text) throws FileNotFoundException, FileSystemException, InvocationTargetException {
        RemoteNodeAccess args = loadAccessArgs(name);

        File file = FindFile(args.targetDirectory.getNodes(), args.actualName);
        if(file == null){
            throw new FileNotFoundException();
        }
        if(!file.isOpened()){
            throw new FileSystemException(Messages.FileNotOpened());
        }
        if(file.getOpenMode() != FileOpenMode.rw){
            throw new FileSystemException(Messages.FileNotOpenedInWriteMode());
        }

        file.write(text);
    }

    public void WriteLineToFile(String name, String text) throws FileSystemException, FileNotFoundException, InvocationTargetException {
        RemoteNodeAccess args = loadAccessArgs(name);

        File file = FindFile(args.targetDirectory.getNodes(), args.actualName);
        if(file == null){
            throw new FileNotFoundException();
        }
        if(!file.isOpened()){
            throw new FileSystemException(Messages.FileNotOpened());
        }
        if(file.getOpenMode() != FileOpenMode.rw){
            throw new FileSystemException(Messages.FileNotOpenedInWriteMode());
        }

        file.writeLine(text);
    }

    public String ReadFile(String name) throws FileSystemException, FileNotFoundException, InvocationTargetException {
        RemoteNodeAccess args = loadAccessArgs(name);

        File file = FindFile(args.targetDirectory.getNodes(), args.actualName);
        if(file == null){
            throw new FileNotFoundException();
        }
        if(!file.isOpened()){
            throw new FileSystemException(Messages.FileNotOpened());
        }

        return file.readToEnd();
    }

    public void RemoveFile(String name) throws FileSystemException, FileNotFoundException, InvocationTargetException {
        RemoteNodeAccess args = loadAccessArgs(name);

        File file = FindFile(args.targetDirectory.getNodes(), args.actualName);
        if(file == null){
            throw new FileNotFoundException();
        }
        if(file.isOpened()){
            throw new FileSystemException(Messages.CannotDeleteFileOpened());
        }

        args.targetDirectory.removeNode(file);
    }



    public void MakeDirectory(String name) throws InvocationTargetException {
        RemoteNodeAccess args = loadAccessArgs(name);

        Directory dir = new Directory();
        dir.setName(args.actualName);
        args.targetDirectory.addNode(dir);
    }

    public Collection<IDirectoryNode> GetNodes(){
        return context.getCurrentDirectory().getNodes();
    }

    public boolean ChangeDirectory(String path) throws InvocationTargetException {

        String[] directoryNames = path.trim().split("/");
        boolean isDirectoryChanged = false;
        int i = 0;
        if(directoryNames[0].equals("root")){
            int dirsToRoot = this.context.getRawPath().size() -1;
            for(int k = 0; k < dirsToRoot; k++){
                this.context.stepOutOfDirectory();
            }
            i++;
        }
        for (; i < directoryNames.length; i++) {

            if (Objects.equals(directoryNames[i], "..")) {
                if(context.getRawPath().size() > 1){
                    context.stepOutOfDirectory();
                    isDirectoryChanged = true;
                }else{
                    throw new NullPointerException();
                }
            } else {
                Directory dir = FindDirectory(context.getCurrentDirectory().getNodes(), directoryNames[i]);
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

    public void RemoveDirectory(String name) throws FileSystemException, InvocationTargetException {
        RemoteNodeAccess args = loadAccessArgs(name);

        Directory dir = FindDirectory(args.targetDirectory.getNodes(), args.actualName);
        if(dir == null){
            throw new FileSystemException(Messages.DirDoesNotExist());
        }
        if(dir.getNodes().size() > 0){
            throw new FileSystemException(Messages.CannotDeleteNonEmptyDir());
        }

        args.targetDirectory.removeNode(dir);
    }

    public void RenameNode(String name, String newName) throws InvocationTargetException {
        RemoteNodeAccess args = loadAccessArgs(name);

        IDirectoryNode node = FindNode(args.targetDirectory.getNodes(), args.actualName);
        node.setName(newName);
    }

    //Sorry for the last 3 functions. I cannot come up with another idea except generic which is not working.

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

    public IDirectoryNode FindNode(Collection<IDirectoryNode> nodes, String searched){
        return (DirectoryNode) nodes
                .stream()
                .filter((node) ->
                        Objects.equals(node.getName(), searched) && node instanceof DirectoryNode)
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


