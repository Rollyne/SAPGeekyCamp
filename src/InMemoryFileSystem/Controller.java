package InMemoryFileSystem;

import InMemoryFileSystem.Commands.Overload;
import InMemoryFileSystem.Commands.Sequences;
import InMemoryFileSystem.Entities.Abstract.IDirectoryNode;
import InMemoryFileSystem.Services.Service;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.FileLockInterruptionException;
import java.nio.file.FileSystemException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        try{
            this.service.MakeFile(args.get("name"));
        } catch(InvocationTargetException e){
            System.out.println(Messages.DirDoesNotExist());
        }

    }

    public void mkdir(HashMap<String, String> args) throws InvocationTargetException {
        try{
            this.service.MakeDirectory(args.get("name"));
        }catch(InvocationTargetException e){
            System.out.println(Messages.DirDoesNotExist());
        }
    }

    public void list(){
        Collection<IDirectoryNode> nodes = this.service.GetNodes();

        for(IDirectoryNode node:nodes){
            System.out.println(node.getName());
        }
    }

    public void cd(HashMap<String, String> args){
        try{
            if(!this.service.ChangeDirectory(args.get("path"))) {
                System.out.println(Messages.DirDoesNotExist());
            }
        } catch(InvocationTargetException e){
            System.out.println(Messages.DirDoesNotExist());
        }

    }

    public void openf(HashMap<String, String> args){
        try{
            this.service.OpenFile(args.get("name"), args.get("mode"));
        } catch(FileNotFoundException e){
            System.out.println(Messages.FileDoesNotExist());
        } catch(FileLockInterruptionException e){
            System.out.println(Messages.FileAlreadyOpened());
        }catch(InvocationTargetException e){
            System.out.println(Messages.DirDoesNotExist());
        }

    }

    public void closef(HashMap<String,String> args){
        try{
            this.service.CloseFile(args.get("name"));
        } catch(FileNotFoundException e){
            System.out.println(Messages.FileDoesNotExist());
        }catch(InvocationTargetException e){
            System.out.println(Messages.DirDoesNotExist());
        }
    }

    public void write(HashMap<String, String> args) {
        try{
            this.service.WriteToFile(args.get("name"), args.get("text"));
        } catch(FileNotFoundException e){
            System.out.println(Messages.FileDoesNotExist());
        } catch (FileSystemException e){
            System.out.println(e.getMessage());
        }catch(InvocationTargetException e){
            System.out.println(Messages.DirDoesNotExist());
        }
    }

    public void writeln(HashMap<String, String> args) {
        try{
            this.service.WriteLineToFile(args.get("name"), args.get("text"));
        } catch(FileNotFoundException e){
            System.out.println(Messages.FileDoesNotExist());
        } catch (FileSystemException e){
            System.out.println(e.getMessage());
        }catch(InvocationTargetException e){
            System.out.println(Messages.DirDoesNotExist());
        }
    }

    public void read(HashMap<String, String> args){
        try{
            System.out.printf("%s\n###### %s%s %s######\n","\u001B[35m","\u001B[33m", args.get("name"),"\u001B[35m");
            System.out.printf("%s%s\n","\u001B[36m",this.service.ReadFile(args.get("name")));
        } catch(FileNotFoundException e){
            System.out.println(Messages.FileDoesNotExist());
        } catch (FileSystemException e){
            System.out.println(e.getMessage());
        }catch(InvocationTargetException e){
            System.out.println(Messages.DirDoesNotExist());
        }
    }

    public void rmf(HashMap<String, String> args){
        try{
            this.service.RemoveFile(args.get("name"));
        } catch(FileNotFoundException e){
            System.out.println(Messages.FileDoesNotExist());
        } catch (FileSystemException e){
            System.out.println(e.getMessage());
        }catch(InvocationTargetException e){
            System.out.println(Messages.DirDoesNotExist());
        }
    }

    public void rmdir(HashMap<String, String> args){
        try{
            this.service.RemoveDirectory(args.get("name"));
        } catch (FileSystemException e){
            System.out.println(e.getMessage());
        }catch(InvocationTargetException e){
            System.out.println(Messages.DirDoesNotExist());
        }
    }

    public void rename(HashMap<String, String> args){
        try{
            this.service.RenameNode(args.get("name"), args.get("newName"));
        } catch (InvocationTargetException e) {
            System.out.println(Messages.DirDoesNotExist());
        }
    }

    private void printCommand(String commandName, Overload[] overloads){
        System.out.printf("---%s %s :\n","\u001B[35m", commandName );
        for(Overload overload : overloads){
            System.out.printf("%s%s ", "\u001B[36m", commandName);
            for(String arg : overload.getArguments()){
                if(Objects.equals(arg, "text")){
                    System.out.printf("#%s[%s] ","\u001B[33m", arg);
                    continue;
                }
                System.out.printf("%s[%s] ","\u001B[33m", arg);
            }
            System.out.printf("  -%s %s","\u001B[34m", overload.getDescription());
        }
        System.out.println("\n");
    }

    public void help(){
        HashMap<String, Overload[]> commands = Sequences.getCommands();
        for(Map.Entry<String, Overload[]> cmd : commands.entrySet()){
            printCommand(cmd.getKey(), cmd.getValue());
            System.out.println();
        }
    }

    public void help(HashMap<String, String> args){
        Overload[] cmd = Sequences.getCommands().get(args.get("command"));
        printCommand(args.get("command"), cmd);
    }

}
