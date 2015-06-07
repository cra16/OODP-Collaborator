package cra.oodp2nd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by 현우 on 2015-06-07.
 */
public class TaskButton extends MainButtonState {

    @Override
    void doAction(final ButtonContext context, Button button,  final Activity act, final String id,  final Class ActivityClass) {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act.getApplicationContext(), ActivityClass);
                intent.putExtra("userId", id);
                act.startActivity(intent);
            }
        });
    }
}
