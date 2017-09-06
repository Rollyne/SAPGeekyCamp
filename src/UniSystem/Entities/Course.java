package UniSystem.Entities;

public class Course implements Identificatable {

    private int id;
    private String name;
    private String description;
    private int credits;

    public void setId(int id) {
        this.id = id;
    }
    public int getId(){
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
