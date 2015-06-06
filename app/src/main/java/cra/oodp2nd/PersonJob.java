package cra.oodp2nd;

import android.database.Cursor;

/**
 * Created by 현우 on 2015-06-04.
 */
public class PersonJob{
    int id;
    String userId;
    public PersonJob(Cursor result)
    {

        this.id = result.getInt(0);
        this.userId = result.getString(1);
    }

    public void setId(int id)
    {
        this.id = id;
    }
    public int getId()
    {
        return id;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;

    }
    public String getUserId()
    {
        return userId;
    }
}
