package com.digitalbuddha.daggerdemo;

import com.android.volley.toolbox.RequestFuture;
import com.digitalbuddha.daggerdemo.model.SavingsType;
import com.digitalbuddha.daggerdemo.rest.GetSavingsTypesRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.util.List;

//RoboelectricTest
@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = 18)
public class APITest extends BaseTestCase
{
    //TypesTest
    @Test
    public void tesGetSavingTypesJob() throws Exception {
        RequestFuture<List<SavingsType>> future=RequestFuture.newFuture();
        GetSavingsTypesRequest savingsTypesRequest= new GetSavingsTypesRequest(future,future);
        savingsTypesRequest.invoke();
        List<SavingsType> savingsTypes1 = future.get();
    }
}
