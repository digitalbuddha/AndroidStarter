package com.digitalbuddha.daggerdemo.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.digitalbuddha.daggerdemo.activitygraphs.R;
import com.digitalbuddha.daggerdemo.model.SavingsType;
import com.digitalbuddha.daggerdemo.utils.MyVolley;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by patriciaestridge on 8/29/14.
 */
public class SavingTypeFragment extends Fragment {
    private static String SAVINGS_TYPE = "savingstype";
    @InjectView(R.id.saving_icon)
    NetworkImageView savingsTypeIcon;
    @InjectView(R.id.add_icon)
    NetworkImageView savingsAddIcon;
    @InjectView(R.id.saving_title)
    TextView savingsTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.savings_pager, container, false);
        ButterKnife.inject(this, rootView);

        Bundle args = getArguments();
        Gson gson = new Gson();
        SavingsType savingsType = gson.fromJson(args.getString(SAVINGS_TYPE), SavingsType.class);

        savingsTitle.setText(savingsType.getTitle());
        savingsTypeIcon.setImageUrl(savingsType.getIconUrl(), MyVolley.getImageLoader());
        savingsAddIcon.setImageUrl(savingsType.getAdd(), MyVolley.getImageLoader());

        return rootView;
    }
}
