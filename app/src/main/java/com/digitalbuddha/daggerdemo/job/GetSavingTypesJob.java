package com.digitalbuddha.daggerdemo.job;

import com.android.volley.Response;
import com.digitalbuddha.daggerdemo.model.SavingsType;
import com.digitalbuddha.daggerdemo.rest.SavingsTypesRequest;
import com.path.android.jobqueue.Params;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by MikeN on 9/8/14.
 */
//GetTypesJob
public class GetSavingTypesJob extends AbstractVolleyJob implements Response.Listener<List<SavingsType>>{
    //TODO add retry count;
    public List<SavingsType> savingsTypes;

    //GetTypesConstructor
    public GetSavingTypesJob() {
//        super(new Params(2)
//                .requireNetwork()
//                .groupBy("Repos"));
        super(new Params(0));
    }

    //GetTypesRun
    @Override
    public void onRun() throws Throwable {
        SavingsTypesRequest savingsTypesRequest = new SavingsTypesRequest(this, this);
        savingsTypesRequest.invoke();

    }
    //volley callbacks
    @Override
    public void onResponse(List<SavingsType> typeResponse) {
        savingsTypes= typeResponse;
        EventBus.getDefault().post(this);
    }
}
