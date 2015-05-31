package cra.oodp2nd;

import android.database.Cursor;

/**
 * Created by wbqd on 15. 5. 16..
 */
public abstract class AbstractJob {
    private int id;
    private String title;

    public AbstractJob(Cursor result) {
        setId(Integer.valueOf(result.getString(0)));
        setTitle(result.getString(2));
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
