package Domain;

public class Module extends ContentItem {

    private CourseDomain course;
    private String version;
    private String contact;
    private String contactEmail;
    private int id;

    public Module(int ContentItemiD, int id, String publishDate, String status, String title, String description,
            String version, String contact, String contactEmail) {
        super(ContentItemiD, publishDate, status, title, description);
        this.course = course;
        this.version = version;
        this.contact = contact;
        this.contactEmail = contactEmail;
        this.id = id;

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

    public int getModuleId() {
        return id;
    }

    @Override
    public String toString() {
        return super.getTitle();
    }

}
