package com.sqvat.fit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by GAL on 1/29/2015.
 */
public class RoutineEmptyView extends FrameLayout {

    public RoutineEmptyView(Context context) {
        super(context);

        View view = inflate(context, R.layout.view_routine_empty, null);
        addView(view);
    }

}
