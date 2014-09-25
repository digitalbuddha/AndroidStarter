package com.digitalbuddha.daggerdemo.job;

import com.digitalbuddha.daggerdemo.model.SavingsType;
import com.digitalbuddha.daggerdemo.rest.SavingsTypesRequest;
import com.path.android.jobqueue.Params;

import java.util.List;

/**
 * Created by MikeN on 9/8/14.
 */
//GetTypesJob
public class GetSavingTypesJob extends AbstractVolleyJob {
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
        String url = "Google.com";
        SavingsTypesRequest savingsTypesRequest = new SavingsTypesRequest(this, this, url);
        savingsTypesRequest.invoke();
        savingsTypes = savingsTypesRequest.savingsTypes;
    }
}
