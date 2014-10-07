package com.digitalbuddha.daggerdemo.ui;

import android.os.Bundle;
import android.widget.EditText;

import com.digitalbuddha.daggerdemo.activitygraphs.R;
import com.digitalbuddha.daggerdemo.dagger.DemoBaseActivity;
import com.digitalbuddha.daggerdemo.job.GetRedditJob;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


//MMM...Composition over Inheritence
public class RedditSyncActivity extends DemoBaseActivity {

    @InjectView(R.id.editText)
    EditText userName;
    @Inject
    Provider<GetRedditJob> redditJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.inject(this); //view injection entry point

    }

    @OnClick(R.id.button) //cleaner than listeners
    public void getNumberOfReposForUser() {
     jobManager.addJobInBackground(redditJob.get());
    }

    public void  onEventMainThread(GetRedditJob job)
    {
        Map map = job.map;
    }
}
