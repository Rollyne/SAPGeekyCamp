package InMemoryFileSystem;

public class Messages {
    public static String DirDoesNotExist(){
        return "This directory does not exist.";
    }
    public static String FileDoesNotExist() {
        return "This file does not exist.";
    }
    public static String FileAlreadyOpened(){
        return "This file is already opened.";
    }
    public static String FileNotOpened(){
        return "This file is not opened.";
    }
    public static String FileNotOpenedInWriteMode(){
        return "This file is not opened in write mode.";
    }
    public static String CannotDeleteFileOpened(){
        return "This file is opened and you cannot delete it.";
    }
    public static String CannotDeleteNonEmptyDir(){
        return "You cannot delete a non-empty directory.";
    }
    public static String InvalidCommand(){
        return "Invalid command. Use 'help' for information.";
    }
    public static String NoAccessToCommand(){
        return "You do not have access to this command.";
    }
}
