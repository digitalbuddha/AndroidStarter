package com.digitalbuddha.daggerdemo.job;

import com.android.volley.Response;
import com.digitalbuddha.daggerdemo.model.SavingRecord;
import com.digitalbuddha.daggerdemo.rest.GetSavingsRecordsRequest;
import com.digitalbuddha.daggerdemo.utils.AbstractVolleyJob;
import com.path.android.jobqueue.Params;

import java.util.List;

/**
 * Created by MikeN on 9/8/14.
 */
    //GetSavingsRecordJob
public class GetSavingsRecordsByUserIdJob extends AbstractVolleyJob implements Response.Listener<List<SavingRecord>>, Response.ErrorListener {
    private int userId;
    public List savingsTypes;

    //GetRecordsConstructor
    public GetSavingsRecordsByUserIdJob(int userId) {
        super(new Params(0));
        this.userId = userId;
    }
    //GetRecordsRun
    @Override
    public void onRun() throws Throwable {
        GetSavingsRecordsRequest savingsRecordsRequest = new GetSavingsRecordsRequest(this, this);
        savingsRecordsRequest.invoke();
        savingsTypes = savingsRecordsRequest.savingsTypes;
    }
    //GetRecordsResponse
    @Override
    public void onResponse(List<SavingRecord> response) {
    }
}
