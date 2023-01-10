package Domain;

public class Webcast extends ContentItem {

    private String speaker;
    private String organisation;
    private String URL;

    public Webcast(int iD, String publishDate, Domain.status status, String title, String description, String uRL,
            String speaker,
            String organisation) {
        super(iD, publishDate, status, title, description);
        this.speaker = speaker;
        this.URL = URL;

        this.organisation = organisation;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getOrganisation() {
        return organisation;
    }
}
