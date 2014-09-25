package com.digitalbuddha.daggerdemo.job;

import com.digitalbuddha.daggerdemo.rest.PostSavingsRecordsRequest;
import com.path.android.jobqueue.Params;

/**
 * Created by MikeN on 9/8/14.
 */
//PostSavingsClass
public class PostSavingsRecordByUserIdJob extends AbstractVolleyJob {
    private int userId;
    private int typeId;

    //PostConstructor
    public PostSavingsRecordByUserIdJob(int userId, int typeId) {
        super(new Params(2)
                .requireNetwork()
                .groupBy("PostSavingsRecord"));
        this.userId = userId;
    }
    //PostSavingsRun
    @Override
    public void onRun() throws Throwable {
        PostSavingsRecordsRequest savingsRecordsRequest = new PostSavingsRecordsRequest(this, this,userId,typeId);
        savingsRecordsRequest.invoke();
    }
}
