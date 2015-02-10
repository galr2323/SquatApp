package com.sqvat.squat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.sqvat.squat.data.Routine;

import java.util.List;

/**
 * Created by GAL on 1/24/2015.
 */
public class Util {
    private static final String LOG_TAG = "Util";

    public static void hideKeyboard(Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static boolean isNotEmpty(EditText editText){
       return editText.getText().toString().trim().length() > 0;
    }

    public static Routine getUsersRoutine(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        long routineId = sharedPref.getLong("usersRoutineId", -1);
        Log.d(LOG_TAG, "users routine id: " + routineId);

        Routine routine = Routine.load(Routine.class, routineId);
        List<Routine> routines = Routine.getAll();
        Log.d(LOG_TAG, "num of routines: " + routines.size());
        return routine;
    }
}
