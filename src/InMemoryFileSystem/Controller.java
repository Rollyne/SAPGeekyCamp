package InMemoryFileSystem;

import InMemoryFileSystem.Entities.Abstract.IDirectoryNode;
import InMemoryFileSystem.Services.Service;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.FileLockInterruptionException;
import java.nio.file.FileSystemException;
import java.util.Collection;
import java.util.HashMap;

public class Controller {

    private Service service;
    private Context context;
    Controller(Context context){
        this.service = new Service(context);
        this.context = context;
    }
    Controller(Service service, Context context){
        this.service = service;
        this.context = context;
    }

    //MakeFile
    public void mkfile(HashMap<String, String> args){
         this.service.MakeFile(args.get("name"));
    }

    public void mkdir(HashMap<String, String> args){
        this.service.MakeDirectory(args.get("name"));
    }

    public void list(){
        Collection<IDirectoryNode> nodes = this.service.GetNodes();

        for(IDirectoryNode node:nodes){
            System.out.println(node.getInitials() + " | " + node.getName());
        }
    }

    public void cd(HashMap<String, String> args){
        try{
            if(!this.service.ChangeDirectory(args.get("path"))) {
                System.out.println("There is no such directory.");
            }
        } catch(InvocationTargetException e){
            System.out.println("There is no such directory.");
        }

    }

    public void openf(HashMap<String, String> args){
        try{
            this.service.OpenFile(args.get("name"), args.get("mode"));
        } catch(FileNotFoundException e){
            System.out.println("The file was not found.");
        } catch(FileLockInterruptionException e){
            System.out.println("This file is already opened");
        }

    }

    public void closef(HashMap<String,String> args){
        try{
            this.service.CloseFile(args.get("name"));
        } catch(FileNotFoundException e){
            System.out.println("The file was not found.");
        }
    }

    public void write(HashMap<String, String> args) {
        try{
            this.service.WriteToFile(args.get("name"), args.get("text"));
        } catch(FileNotFoundException e){
            System.out.println("The file was not found.");
        } catch (FileSystemException e){
            System.out.println(e.getMessage());
        }
    }

    public void writeln(HashMap<String, String> args) {
        try{
            this.service.WriteLineToFile(args.get("name"), args.get("text"));
        } catch(FileNotFoundException e){
            System.out.println("The file was not found.");
        } catch (FileSystemException e){
            System.out.println(e.getMessage());
        }
    }

    public void read(HashMap<String, String> args){
        try{
            System.out.printf("\n\n###### %s ######\n", args.get("name"));
            System.out.println(this.service.ReadFile(args.get("name")));
        } catch(FileNotFoundException e){
            System.out.println("The file was not found.");
        } catch (FileSystemException e){
            System.out.println(e.getMessage());
        }
    }

    public void rmf(HashMap<String, String> args){
        try{
            this.service.RemoveFile(args.get("name"));
        } catch(FileNotFoundException e){
            System.out.println("The file was not found.");
        } catch (FileSystemException e){
            System.out.println(e.getMessage());
        }
    }

    public void rmdir(HashMap<String, String> args){
        try{
            this.service.RemoveDirectory(args.get("name"));
        } catch(FileNotFoundException e){
            System.out.println("The directory was not found.");
        } catch (FileSystemException e){
            System.out.println(e.getMessage());
        }
    }

}
