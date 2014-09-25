package com.digitalbuddha.gdg.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.digitalbuddha.gdg.activitygraphs.R;
import com.digitalbuddha.gdg.ui.activities.SavingActivity;
import com.digitalbuddha.gdg.utils.MyVolley;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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
    private SavingsType savingsType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.savings_pager, container, false);
        ButterKnife.inject(this, rootView);

        Bundle args = getArguments();
        Gson gson = new Gson();
        savingsType = gson.fromJson(args.getString(SAVINGS_TYPE), SavingsType.class);

        savingsTitle.setText(savingsType.getTitle());
        savingsTypeIcon.setImageUrl(savingsType.getIconUrl(), MyVolley.getImageLoader());
        savingsAddIcon.setImageUrl(savingsType.getAdd(), MyVolley.getImageLoader());
        return rootView;
    }

    @OnClick(R.id.add_icon)
    public void addSavingsRecord()
    {
        SavingRecord savingRecord = new SavingRecord();
        savingRecord.setSavingsType(savingsType);
        savingRecord.setId(savingsType.getTitle());
        ((SavingActivity)getActivity()).savingListAdapter.add(savingRecord, 0);
        ((SavingActivity)getActivity()).recyclerView.scrollToPosition(0);
    }
}
