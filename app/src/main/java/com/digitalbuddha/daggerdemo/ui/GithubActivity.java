package com.digitalbuddha.daggerdemo.ui;

import android.os.Bundle;
import android.widget.EditText;

import com.digitalbuddha.daggerdemo.activitygraphs.R;
import com.digitalbuddha.daggerdemo.dagger.DemoBaseActivity;
import com.digitalbuddha.daggerdemo.job.GetReposJob;
import com.digitalbuddha.daggerdemo.utils.JsonParser;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


//MMM...Composition over Inheritence
public class GithubActivity extends DemoBaseActivity {
    @Inject
    Provider<GetReposJob> repoJob; //Provider will give new instance everytime .get() is called

    @InjectView(R.id.editText)
    EditText userName;
    @Inject
    ActivityTitleController activityTitleController;  //Helper class for activities, for example to init action bar.
    @Inject
    JsonParser jsonParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.inject(this); //view injection entry point

        activityTitleController.setTitle("Github");
    }

    @OnClick(R.id.button) //cleaner than listeners
    public void getNumberOfReposForUser() {
        GetReposJob job = repoJob.get();
        job.userName = userName.getText().toString(); //Set fields on job directly from screen, less copying values=less mistakes, maybe move to view models at some point
        jobManager.addJobInBackground(job);

    }

    public void onEventMainThread(GetReposJob job) {
        String repos = jsonParser.convertObjectToJSON(job.repos);
        userName.setText(repos);
    }
}
