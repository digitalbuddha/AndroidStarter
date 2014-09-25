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
public abstract class AbstractVolleyJob extends Job implements  Response.ErrorListener {

    public AbstractVolleyJob(Params params) {super(params);}

    //Job override methods
    @Override
    public void onAdded() {}
    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {return false;}
    @Override
    protected void onCancel() { EventBus.getDefault().post(new ErrorEvent());}

    @Override
    public void onErrorResponse(VolleyError volleyError) {EventBus.getDefault().post(new ErrorEvent());}
}
