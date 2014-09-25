package com.digitalbuddha.daggerdemo.job;

import com.digitalbuddha.daggerdemo.rest.PostSavingsRecordsRequest;
import com.path.android.jobqueue.Params;

import java.util.List;

/**
 * Created by MikeN on 9/8/14.
 */
public class PostSavingsRecordByUserIdJob extends VolleyJob {
    private int userId;

    //TODO add retry count;
    public PostSavingsRecordByUserIdJob(int userId) {
//        super(new Params(2)
//                .requireNetwork()
//                .groupBy("PostSavingsRecord"));
        super(new Params(0));
        this.userId = userId;
    }
    @Override
    public void onRun() throws Throwable {
        PostSavingsRecordsRequest savingsRecordsRequest = new PostSavingsRecordsRequest(this, this);
        savingsRecordsRequest.invoke();
        List savingsTypes = savingsRecordsRequest.savingsTypes;
    }
}
