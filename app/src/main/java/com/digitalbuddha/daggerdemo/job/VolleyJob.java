package com.digitalbuddha.daggerdemo.job;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.digitalbuddha.daggerdemo.utils.ErrorEvent;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import de.greenrobot.event.EventBus;

/**
 * Created by MikeN on 9/24/14.
 */
public abstract class VolleyJob extends Job  implements Response.Listener, Response.ErrorListener {

    //TODO add retry count;
    public VolleyJob(Params params) {

        super(params);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        EventBus.getDefault().post(new ErrorEvent()); //post the job instead of making dummy event pojos

    }

    @Override
    public void onResponse(Object o) {
        EventBus.getDefault().post(this); //post the job instead of making dummy event pojos
    }

    //don't retry on error
    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }

    @Override
    protected void onCancel() {
        EventBus.getDefault().post(new ErrorEvent()); //post the job instead of making dummy event pojos
    }

    @Override
    public void onAdded() {
    }

}
