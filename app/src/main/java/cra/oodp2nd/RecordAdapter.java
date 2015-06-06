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
public class RecordAdapter extends JobAdapter {
    Activity act;
    public RecordAdapter(Context context, int textViewResourceId, List<AbstractJob> objects,Activity act) {
        super(context, textViewResourceId, objects);
        this.act = act;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            LayoutInflater vi = (LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.record_list_item, null);
        }
        RecordJob recordJob = (RecordJob) jobList.get(position);
        if(recordJob != null) {
            TextView id = (TextView) v.findViewById(R.id.record_list_item_id);
            TextView title = (TextView) v.findViewById(R.id.record_list_item_title);

            if (id != null) {
                id.setText(Integer.toString(recordJob.getId()));
            }
            if (title != null) {
                title.setText(recordJob.getTitle());
            }

        }
        return  v;
    }
}

