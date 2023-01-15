package Domain;

import javafx.scene.control.TreeItem;

public class Progress {

    private Cursist cursist;
    private Webcast webcast;
    private Module module;
    private int progress;
    private int id;

    public Progress(Cursist cursist, Module module, int progress, int id) {
        this.cursist = cursist;
        this.module = module;
        this.progress = progress;
        this.id = id;

    }

    public Progress(Cursist cursist, Webcast webcast, int progress, int id) {
        this.cursist = cursist;
        this.webcast = webcast;
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

    public Webcast getWebcast() {
        return webcast;
    }

    public Module getModule() {
        return module;
    }

    public int getProgress() {
        return progress;
    }

    @Override
    public String toString() {
        if (module.equals(null)) {
            return webcast + " progressie: " + progress + "%";

        } else {
            return module + " progressie: " + progress + "%";

        }
    }

}
