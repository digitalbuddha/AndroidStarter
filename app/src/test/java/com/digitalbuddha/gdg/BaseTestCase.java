package com.digitalbuddha.gdg;

import android.content.Context;
import android.test.AndroidTestCase;

import com.android.volley.ExecutorDelivery;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.ResponseDelivery;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.digitalbuddha.gdg.utils.MyVolley;
import com.squareup.okhttp.OkHttpClient;

import org.junit.Before;
import org.robolectric.Robolectric;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;

/**
 * Created by Nakhimovich on 6/13/14.
 */
public class BaseTestCase extends AndroidTestCase
{
    public Context context;

    @Override
    @Before
    public void setUp() throws Exception
    {
        super.setUp();
        context = Robolectric.application;
        MyVolley.setRequestQueue(newRequestQueueForTest(context));
    }



    public static RequestQueue newRequestQueueForTest(final Context context)
    {
        final File cacheDir = new File(context.getCacheDir(), "volley");

        final Network network = new BasicNetwork(new OkHttpStack());

        final ResponseDelivery responseDelivery = new ExecutorDelivery(Executors.newSingleThreadExecutor());

        final RequestQueue queue =
                new RequestQueue(
                        new DiskBasedCache(cacheDir),
                        network,
                        4,
                        responseDelivery);

        queue.start();

        return queue;
    }

    public Context getContext()
    {
        return context;
    }


    public static class OkHttpStack extends HurlStack
    {
        private final OkHttpClient client;
        public OkHttpStack()
        {
            this(new OkHttpClient());
        }
        public OkHttpStack(OkHttpClient client)
        {
            if (client == null)
            {
                throw new NullPointerException("Client must not be null.");
            }
            this.client = client;
        }

        @Override
        protected HttpURLConnection createConnection(URL url) throws IOException
        {
            return client.open(url);
        }
    }
}
