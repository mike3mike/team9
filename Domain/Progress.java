package Domain;

public class Progress {

    private Cursist cursist;
    private int progress;
    private ContentItem contentItem;
    private int id;

    public Progress(Cursist cursist, ContentItem contentItem, int progress, int id) {
        this.cursist = cursist;
        this.contentItem = contentItem;
        this.progress = progress;
        this.id = id;
    }

    public Progress(Cursist cursist, int progress, int id) {
        this.cursist = cursist;
        this.progress = progress;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setProgress(int Progress) {
        this.progress = Progress;
        System.out.println(progress);
    }

    public Cursist getCursist() {
        return cursist;
    }

    public ContentItem getContentItem() {
        return contentItem;
    }

    public int getProgress() {
        return progress;
    }

    @Override
    public String toString() {
        return contentItem.getTitle() + " progressie: " + progress + "%";
    }

}
