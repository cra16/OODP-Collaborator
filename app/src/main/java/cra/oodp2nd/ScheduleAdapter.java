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
public class ScheduleAdapter extends JobAdapter {
    Activity act;
    public ScheduleAdapter(Context context, int textViewResourceId, List<AbstractJob> objects,Activity act) {
        super(context, textViewResourceId, objects);
        this.act =act;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            LayoutInflater vi = (LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.schedule_list_item, null);
        }
        ScheduleJob scheduleJob = (ScheduleJob) jobList.get(position);
        if(scheduleJob != null) {
            TextView id = (TextView) v.findViewById(R.id.schedule_list_item_id);
            TextView title = (TextView) v.findViewById(R.id.schedule_list_item_title);
            if (id != null) {
                id.setText(Integer.toString(scheduleJob.getId()));
            }
            if (title != null) {
                title.setText(scheduleJob.getTitle());
            }
        }
        return  v;
    }
}
