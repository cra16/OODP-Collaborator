package cra.oodp2nd;

/**
 * Created by wbqd on 15. 5. 16..
 */
public abstract class AbstractJob {
    private int id;
    private String title;

    public AbstractJob(int id, String title) {
        setId(id);
        setTitle(title);
    }

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
