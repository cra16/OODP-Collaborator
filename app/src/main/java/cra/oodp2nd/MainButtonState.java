package cra.oodp2nd;

import android.app.Activity;
import android.content.Context;
import android.widget.Button;

/**
 * Created by 현우 on 2015-06-07.
 */
public abstract class MainButtonState extends MainActivity {

    abstract void doAction(final ButtonContext context, Button button, final Activity actt, final String id,final Class ActivityClass);



}

