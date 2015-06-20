package cra.oodp2nd;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 현우 on 2015-06-07.
 */
public class SubTaskAdapter extends JobAdapter {
    Activity act;

    public SubTaskAdapter(Context context, int textViewResourceId, List<AbstractJob> objects, Activity act) {
        super(context, textViewResourceId, objects);
        this.act = act;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.activity_list_multiple, null);
        }
        SubTaskJob subTaskJob = (SubTaskJob) jobList.get(position);
        if (subTaskJob  != null) {
            CheckedTextView id = (CheckedTextView) v.findViewById(R.id.id_item);

            if (checkDB(jobList.get(position).getId()))
                id.setChecked(true);
            else
                id.setChecked(false);

            if (id != null) {
                id.setText(subTaskJob.getTitle());
            }

        }
        return v;
    }

    public boolean checkDB(int id) {


        SQLiteDatabase DB = TaskUpdateActivity.myDBHelper.getReadableDatabase();
        Cursor result;
        result = DB.query("table_subtask", new String[]{"id", "clear"}, "id = " + id, null, null, null, null);

        result.moveToFirst();

        if (!result.isAfterLast()) {
            if (result.getInt(1) == 1)
                return true;
            else
                return false;
        }
        return false;
    }
}


