package Domain;

public class Webcast extends ContentItem {

    private String speaker;
    private String organisation;
    private String URL;
    private int id;

    public Webcast(int iD, int webcastID, String publishDate, String status, String title, String description,
            String URL,
            String speaker,
            String organisation) {
        super(iD, publishDate, status, title, description);
        this.speaker = speaker;
        this.organisation = organisation;
        this.URL = URL;
        this.id = webcastID;

        this.organisation = organisation;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getOrganisation() {
        return organisation;
    }

    public String getURL() {
        return URL;
    }

    public int getWebcastId() {
        return id;
    }
}
