package Domain;

public class CourseDomain {
    private String name;
    private String subject;
    private String description;
    private String difficulty;
    private int id;

    public CourseDomain(String name, String subject, String description, String difficulty, int id) {
        this.name = name;
        this.subject = subject;
        this.description = description;
        this.difficulty = difficulty;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

}
