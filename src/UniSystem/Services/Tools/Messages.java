package UniSystem.Services.Tools;

public class Messages {

    //ERROR MESSAGES
    public static String InternalServerError(){
        return "Internal server error! Please report your steps.";
    }
    public static String NotFound(){
        return "The item you are looking for was not found.";
    }

    //INFO MESSAGES
    public static String SuccessfullyAdded(){
        return "The item was successfully added.";
    }
    public static String SuccessfullyFound(){
        return "The item(s) was successfully found.";
    }
    public static String SuccessfullyUpdated(){
        return "The item was successfully updated.";
    }
    public static String SuccessfullyDeleted(){
        return "The item was successfully deleted.";
    }
    public static String SuccessfullyEnrolled() { return "The student was successfully enrolled to the course";}
    public static String AllreadyEnrolledOrNoCourse(){return "The student is already enrolled to this course or the course does not exist.";}
}
