package com.digitalbuddha.daggerdemo.tasks;

import android.content.Context;

import co.touchlab.android.threading.tasks.TaskQueue;


public abstract class InjectableTask extends TaskQueue.Task {

    @Override
    public void run(Context context) throws Exception {
        run();
    }

    protected abstract void run() throws Exception;

    @Override
    public boolean handleError(Exception e) {
        return false;
    }

}
