package cra.oodp2nd;

import android.database.Cursor;

/**
 * Created by 현우 on 2015-05-28.
 */
public class JobFactory {

    public AbstractJob create(Cursor cursor, String ProductName)
    {
        if(ProductName.equals("Record"))
            return new RecordJob(cursor);
        else if(ProductName.equals("Task"))
            return new TaskJob(cursor);
        else if(ProductName.equals("Schedule"))
            return new ScheduleJob(cursor);
        else if(ProductName.equals("Sub"))
            return new SubTaskJob(cursor);

        return null;
    }

}
