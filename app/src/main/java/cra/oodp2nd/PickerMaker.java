package cra.oodp2nd;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by 현우 on 2015-06-08.
 */
public class PickerMaker {
    private PickerFacade DPicker;
    private PickerFacade TPicker;

    public PickerMaker()
    {

        DPicker = new DatePickerFacade();
        TPicker = new TimePickerFacade();
    }

    public void setDPicker(Activity act, EditText Layout)
    {
        DPicker.setPicker(act, Layout);
    }
    public void setTPicker(Activity act, EditText Layout)
    {
        TPicker.setPicker(act, Layout);
    }
}
