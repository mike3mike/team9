package Domain;

public class Webcast extends ContentItem {

    private String speaker;
    private String organisation;
    private String URL;
    private int id;


    public Webcast(int contentItemId, int webcastID, String contentItemPublishDate, String contentItemStatus, String contentItemTitle, String contentItemDescription,
            String URL,
            String speaker,
            String organisation) {
        super(contentItemId, contentItemPublishDate, contentItemStatus, contentItemTitle, contentItemDescription);
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
