package InMemoryFileSystem;

import InMemoryFileSystem.Commands.Command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class Router {
    public static void Route(Command command, Context context) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Controller controller = new Controller(context);
        Method method = null;
        try{
            method = controller.getClass().getDeclaredMethod(command.getName());
            method.invoke(controller);
            return;
        } catch(NoSuchMethodException e){
            method = controller.getClass().getDeclaredMethod(command.getName(), HashMap.class);
        }

        if(method == null){
            throw new NoSuchMethodException();
        }

        method.invoke(controller, command.getArguments());
    }
}
