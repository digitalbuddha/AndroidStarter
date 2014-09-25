package com.digitalbuddha.daggerdemo.job;

import com.digitalbuddha.daggerdemo.rest.GetSavingsRecordsRequest;
import com.path.android.jobqueue.Params;

import java.util.List;

/**
 * Created by MikeN on 9/8/14.
 */
public class GetSavingsRecordsByUserIdJob extends VolleyJob {
    private int userId;

    //TODO add retry count;
    public GetSavingsRecordsByUserIdJob(int userId) {
//        super(new Params(2)
//                .requireNetwork()
//                .groupBy("Repos"));
        super(new Params(0));
        this.userId = userId;
    }
    @Override
    public void onRun() throws Throwable {
        String url = "Google.com";
        GetSavingsRecordsRequest savingsRecordsRequest = new GetSavingsRecordsRequest(this, this, url);
        savingsRecordsRequest.invoke();
        List savingsTypes = savingsRecordsRequest.savingsTypes;
    }
}
