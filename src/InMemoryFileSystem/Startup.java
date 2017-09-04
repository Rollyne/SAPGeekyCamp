package InMemoryFileSystem;

import InMemoryFileSystem.Commands.Command;
import InMemoryFileSystem.Commands.Sequences;
import InMemoryFileSystem.Entities.Directory;
import InMemoryFileSystem.Entities.Abstract.IDirectoryNode;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Scanner;

public class Startup {
    public static void main(String[] args) throws InvocationTargetException {

        Scanner s = new Scanner(System.in);
        Context context = new Context();
        Sequences.Configure();

        while(true)
        {
            String input = s.nextLine();
            try{
                Command command = Sequences.MatchCommand(input);
                if(Objects.equals(command.getName(), "exit"))
                    break;
                Router.Route(command, context);
            } catch(NoSuchMethodException e){
                System.out.println("Invalid command!");
                continue;
            } catch(IllegalAccessException e){
                System.out.println("You do not have access to this method.");
                continue;
            }

        }

        for (IDirectoryNode dn:context.getCurrentDirectory().getNodes()) {
            System.out.println("File " + dn.getName());
        }
    }
}
