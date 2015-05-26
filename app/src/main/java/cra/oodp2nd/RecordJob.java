package cra.oodp2nd;

/**
 * Created by wbqd on 15. 5. 16..
 */
public class RecordJob extends AbstractJob {
    private String name;
    private String date;
    public RecordJob(int id, String title, String date) {
        super(id, title);
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


}
