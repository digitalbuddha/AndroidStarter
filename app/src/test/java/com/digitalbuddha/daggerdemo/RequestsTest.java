package com.digitalbuddha.daggerdemo;

import com.android.volley.toolbox.RequestFuture;
import com.digitalbuddha.daggerdemo.model.SavingsType;
import com.digitalbuddha.daggerdemo.rest.SavingsTypesRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.util.List;

//Roboelectric Test
@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = 18)
public class RequestsTest extends BaseTestCase
{
    //TypesTest
    @Test
    public void tesGetSavingTypesJob() throws Exception {
        RequestFuture<List<SavingsType>> future=RequestFuture.newFuture();
        SavingsTypesRequest savingsTypesRequest= new SavingsTypesRequest(future,future,"google.com");
        savingsTypesRequest.invoke();
        List<SavingsType> savingsTypes1 = future.get();
    }
}
