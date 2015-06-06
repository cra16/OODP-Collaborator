package cra.oodp2nd;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by 현우 on 2015-06-01.
 */
public abstract class JobAdapter extends ArrayAdapter<AbstractJob> {
    protected List<AbstractJob> jobList;

    public JobAdapter(Context context, int textViewResourceId, List<AbstractJob> objects) {
        super(context, textViewResourceId, objects);
        jobList = objects;
    }
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
