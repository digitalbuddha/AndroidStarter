package com.digitalbuddha.gdg.job;

import com.android.volley.Response;
import com.digitalbuddha.gdg.model.SavingRecord;
import com.digitalbuddha.gdg.rest.GetSavingsRecordsRequest;
import com.digitalbuddha.gdg.rest.PostSavingsRecordsRequest;
import com.digitalbuddha.gdg.utils.AbstractVolleyJob;
import com.digitalbuddha.gdg.utils.MyVolley;
import com.path.android.jobqueue.Params;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by MikeN on 9/8/14.
 */
public class PostSavingsRecordByUserIdJob extends AbstractVolleyJob implements Response.Listener<List<SavingRecord>>{
    private int userId;
    private SavingRecord record;

    //PostConstructor
    public PostSavingsRecordByUserIdJob(int userId, SavingRecord record) {
        super(new Params(2)
                .requireNetwork() //don't run job until app has connectivity
                .persist() //past application close
                .groupBy("PostSavingsRecord"));
        this.record = record;
        this.userId=userId;
    }
    //PostSavingsRun
    @Override
    public void onRun() throws Throwable {
        PostSavingsRecordsRequest savingsRecordsRequest = new PostSavingsRecordsRequest(this, this,userId,record);
        savingsRecordsRequest.invoke();
        EventBus.getDefault().post(this);
    }
    //PostRecordsResponse
    @Override
    public void onResponse(List<SavingRecord> response) {
        MyVolley.getRequestQueue().getCache().remove(GetSavingsRecordsRequest.RECORDS_URL);
    }
}
