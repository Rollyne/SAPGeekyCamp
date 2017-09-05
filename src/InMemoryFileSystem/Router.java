package InMemoryFileSystem;

import InMemoryFileSystem.Commands.Command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class Router {
    public static void Route(Command command, Context context) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Controller controller = new Controller(context);
        Method method = null;
        if (command.getArguments().size() > 0) {
            method = controller.getClass().getDeclaredMethod(command.getName(), HashMap.class);
            method.invoke(controller, command.getArguments());
        } else {
            method = controller.getClass().getDeclaredMethod(command.getName());
            method.invoke(controller);
        }
    }
}
