package cra.oodp2nd;

/**
 * Created by wbqd on 15. 5. 16..
 */
public abstract class AbstractJob {
    private String title;
    private int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
