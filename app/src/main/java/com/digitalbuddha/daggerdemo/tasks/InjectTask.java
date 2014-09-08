package com.digitalbuddha.daggerdemo.tasks;

import android.content.Context;

import com.digitalbuddha.daggerdemo.dagger.ForActivity;

import javax.inject.Inject;

import co.touchlab.android.threading.tasks.TaskQueue;
import de.greenrobot.event.EventBus;


public abstract class InjectTask extends TaskQueue.Task {

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

    @Override
    protected void onComplete() {
        super.onComplete();
        EventBus.getDefault().post(this);
    }
}
