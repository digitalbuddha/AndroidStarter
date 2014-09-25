package com.digitalbuddha.daggerdemo.job;

import com.digitalbuddha.daggerdemo.rest.GetSavingsRecordsRequest;
import com.path.android.jobqueue.Params;

import java.util.List;

/**
 * Created by MikeN on 9/8/14.
 */
//GetSavingsRecordJob
public class GetSavingsRecordsByUserIdJob extends AbstractVolleyJob {
    private int userId;
    public List savingsTypes;

    //constructor
    public GetSavingsRecordsByUserIdJob(int userId) {
        super(new Params(0));
        this.userId = userId;
    }
    //Get Savings Records using volley
    @Override
    public void onRun() throws Throwable {
        GetSavingsRecordsRequest savingsRecordsRequest = new GetSavingsRecordsRequest(this, this);
        savingsRecordsRequest.invoke();
        savingsTypes = savingsRecordsRequest.savingsTypes;
    }
}
