package cra.oodp2nd;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 현우 on 2015-06-01.
 */
public class PersonAdapter extends ArrayAdapter<PersonJob> {

    ArrayList<PersonJob> list;
    Activity act;
    public PersonAdapter(Context context, int resource, ArrayList<PersonJob> objects,Activity act) {
        super(context, resource, objects);
        this.list = objects;
        this.act = act;
    }

    public View getView(int position, View contentView, ViewGroup parent) {
        if (contentView == null) {
            LayoutInflater vi = (LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            contentView = vi.inflate(R.layout.activity_list_multiple, null);
        }

        PersonJob Person = (PersonJob) list.get(position);

        if (Person != null) {
            TextView id = (TextView) contentView.findViewById(R.id.id_item);

            if (id != null) {
                id.setText(Person.getUserId());
            }
        }

        return contentView;
    }
}
