package com.digitalbuddha.gdg.job;

import com.android.volley.Response;
import com.digitalbuddha.gdg.model.SavingsType;
import com.digitalbuddha.gdg.rest.GetSavingsTypesRequest;
import com.digitalbuddha.gdg.utils.AbstractVolleyJob;
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
        super(new Params(0));
    }

    //GetTypesRun
    @Override
    public void onRun() throws Throwable {
        GetSavingsTypesRequest savingsTypesRequest = new GetSavingsTypesRequest(this, this);
        savingsTypesRequest.invoke();

    }
    //getTypesResponse
    @Override
    public void onResponse(List<SavingsType> typeResponse) {
        savingsTypes= typeResponse;
        EventBus.getDefault().post(this);
    }
}
