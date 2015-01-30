package com.sqvat.fit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;

import com.sqvat.fit.data.Workout;

/**
 * Created by GAL on 11/6/2014.
 */
public class PromptDialog extends DialogFragment {
    @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final EditText input = new EditText(getActivity());

            builder
                    .setTitle("Workout name")
                    .setView(input)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Editable value = input.getText();
                            Workout workout = new Workout();
                            workout.name = String.valueOf(value);
                            workout.order = Workout.getAll().size();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Do nothing.
                }
            });
            // Create the AlertDialog object and return it
            return builder.create();
        }
}
