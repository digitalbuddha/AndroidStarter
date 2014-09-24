package com.digitalbuddha.daggerdemo.ui;

import android.os.Bundle;
import android.widget.EditText;

import com.digitalbuddha.daggerdemo.activitygraphs.R;
import com.digitalbuddha.daggerdemo.dagger.DemoBaseActivity;
import com.digitalbuddha.daggerdemo.job.GetRedditJob;
import com.digitalbuddha.daggerdemo.tasks.GetPostsTask;
import com.digitalbuddha.daggerdemo.utils.ErrorEvent;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import co.touchlab.android.threading.tasks.TaskQueue;


//MMM...Composition over Inheritence
public class RedditTaskActivity extends DemoBaseActivity {

    @InjectView(R.id.editText)
    EditText userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.inject(this);
        //TODO: create adapter
    }

    @OnClick(R.id.button) //cleaner than listeners
    public void getNumberOfReposForUser() {
        TaskQueue.execute(this,new GetPostsTask());
    }

    public void  onEventMainThread(GetRedditJob job)
    {
        Map map = job.map;
    }

    public void  onEventMainThread(ErrorEvent event)
    {
        //show error
    }
}
