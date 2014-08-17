package com.digitalbuddha.daggerdemo.ui;

import android.os.Bundle;
import android.widget.EditText;

import com.digitalbuddha.daggerdemo.activitygraphs.R;
import com.digitalbuddha.daggerdemo.dagger.DemoBaseActivity;
import com.digitalbuddha.daggerdemo.tasks.GetReposTask;
import com.digitalbuddha.daggerdemo.utils.JsonParser;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import co.touchlab.android.threading.tasks.TaskQueue;


//Composition over Inheritence
public class GithubActivity extends DemoBaseActivity {
    @Inject
    Provider<GetReposTask> repos; //Provider will give new instane everytime .get() is called
    @InjectView(R.id.editText)
    EditText userName;
    @Inject ActivityTitleController activityTitleController;  //Helper class for activities, for example to init action bar.
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
        GetReposTask getReposTask = repos.get(); //since we can inject activity context directly into task, constructor to pass context is no longer needed
        getReposTask.userName = userName.getText().toString(); //Set fields on task directly from screen, less copying values=less mistakes
        TaskQueue.execute(this, getReposTask); //same as it ever was.
    }


    public void onEventMainThread(GetReposTask task) {
        String repos = jsonParser.convertObjectToJSON(task.repos);
        userName.setText(repos);
    }


}
