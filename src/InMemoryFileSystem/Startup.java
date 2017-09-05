package InMemoryFileSystem;

import InMemoryFileSystem.Commands.Command;
import InMemoryFileSystem.Commands.Sequences;
import InMemoryFileSystem.Entities.Abstract.IDirectoryNode;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Scanner;

public class Startup {
    public static void main(String[] args) throws InvocationTargetException {

        Scanner s = new Scanner(System.in);
        Context context = new Context();
        Sequences.Configure();

        System.out.printf("%s|\\| In-Memory File System |\\|%s\n","\u001B[32m", "\u001B[0m");
        System.out.println("Use 'help' for commands.");

        while(true)
        {
            System.out.printf("%s %s %s>> %s","\u001B[35m",context.getCurrentPath(), "\u001B[36m", "\u001B[31m");
            String input = s.nextLine();
            try{
                Command command = Sequences.MatchCommand(input);
                if(Objects.equals(command.getName(), "exit"))
                    break;
                Router.Route(command, context);
            } catch(NoSuchMethodException e){
                System.out.println(Messages.InvalidCommand());
            } catch(IllegalAccessException e){
                System.out.println(Messages.NoAccessToCommand());
            } catch(InvocationTargetException | NullPointerException e){
                System.out.println(Messages.DirDoesNotExist());
            }

        }
    }
}
