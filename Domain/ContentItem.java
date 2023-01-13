package Domain;

public interface ContentItem {

    private int ID;
    private String publishDate;
    private String status;
    private String title;
    private String description;

    public ContentItem(int iD, String publishDate, String status, String title, String description) {
        this.ID = iD;
        this.publishDate = publishDate;
        this.status = status;
        this.title = title;
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
