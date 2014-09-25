package com.digitalbuddha.daggerdemo;

import com.android.volley.toolbox.RequestFuture;
import com.digitalbuddha.daggerdemo.rest.SavingsTypesRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.util.List;


@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = 18)
public class APITest extends BaseTestCase
{
    @Test
    public void tesGetSavingTypesJob() throws Exception {
        RequestFuture<List> future=RequestFuture.newFuture();
        SavingsTypesRequest savingsTypesRequest= new SavingsTypesRequest(future,future,"google.com");
        savingsTypesRequest.invoke();
        future.get();
        assertNotNull(savingsTypesRequest.savingsTypes);
    }
}
