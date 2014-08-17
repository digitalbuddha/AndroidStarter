package com.digitalbuddha.daggerdemo.tasks;

import android.content.Context;

import com.digitalbuddha.daggerdemo.dagger.ForActivity;

import javax.inject.Inject;

import co.touchlab.android.threading.tasks.TaskQueue;


public abstract class InjectTask implements TaskQueue.Task {

    @Inject
    @ForActivity
    Context context;



    @Override
    public void run(Context context) throws Exception {
        run();
    }

    protected abstract void run();

    @Override
    public boolean handleError(Exception e) {
        return false;
    }
}
