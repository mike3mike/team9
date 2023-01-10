package Domain;

public class Module extends ContentItem {

    private CourseDomain course;
    private String version;
    private String contact;
    private String contactEmail;
    private int number;

    public Module(int iD, String publishDate, Domain.status status, String title, String description,
            CourseDomain course, String version, String contact, String contactEmail, int number) {
        super(iD, publishDate, status, title, description);
        this.course = course;
        this.version = version;
        this.contact = contact;
        this.contactEmail = contactEmail;
        this.number = number;
    }

    public CourseDomain getCourse() {
        return course;
    }

    public String getVersion() {
        return version;
    }

    public String getContact() {
        return contact;
    }

    public String getContactEmail() {
        return contactEmail;
    }

}
