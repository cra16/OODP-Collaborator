package cra.oodp2nd;

import android.database.Cursor;

/**
 * Created by wbqd on 15. 5. 16..
 */
public class RecordJob extends AbstractJob {
    private String name;
    private String date;
    private String location;
    public RecordJob(Cursor result) {
        super(result);

        name = result.getString(2);
        date = result.getString(3);
        location = result.getString(4);
    }


    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return this.name;
    }

    public void setDate(String date){ this.date = date;}
    public String getDate(){return this.date;}

    public void setLocation(String location){this.location = location;}
    public String  getLocation(){return this.location;}


}
