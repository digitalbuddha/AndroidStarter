package com.digitalbuddha.daggerdemo.ui.actionbars;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import com.digitalbuddha.daggerdemo.activitygraphs.R;

public class TypefaceActionBar
{
    private Activity activity;
    private String titleString;

    public TypefaceActionBar(Activity activity, String title)
    {
        this.activity = activity;
        this.titleString = title;
        initActionBar();
    }

    private void initActionBar() {
        ActionBar actionBar = activity.getActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(R.color.theme_blue));

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.action_bar);
        TextView title = (TextView) actionBar.getCustomView();
        title.setText(titleString);
    }
}