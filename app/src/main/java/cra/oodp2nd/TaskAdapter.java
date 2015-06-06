package cra.oodp2nd;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 현우 on 2015-06-01.
 */
public class TaskAdapter extends JobAdapter  {
    Activity act;
    public TaskAdapter(Context context, int textViewResourceId, List<AbstractJob> objects,Activity act) {
        super(context, textViewResourceId, objects);
        this.act =act;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            LayoutInflater vi = (LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.task_list_item, null);
        }
        TaskJob taskJob = (TaskJob) jobList.get(position);
        if(taskJob != null) {
            TextView id = (TextView) v.findViewById(R.id.task_list_item_id);
            TextView title = (TextView) v.findViewById(R.id.task_list_item_title);
            if (id != null) {
//                    Log.i("i", taskJob.getId() + "");
                id.setText(Integer.toString(taskJob.getId()));
            }
            if (title != null) {
                title.setText(taskJob.getTitle());
            }
        }
        return  v;
    }

}
