package com.digitalbuddha.daggerdemo.ui;

import android.os.Bundle;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.digitalbuddha.daggerdemo.activitygraphs.R;
import com.digitalbuddha.daggerdemo.dagger.DemoBaseActivity;
import com.digitalbuddha.daggerdemo.utils.CustomVolley;
import com.digitalbuddha.daggerdemo.utils.GsonRequest;
import com.digitalbuddha.daggerdemo.utils.JsonParser;

import java.util.Map;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


//MMM...Composition over Inheritence
public class RedditAsyncActivity extends DemoBaseActivity implements Response.Listener<Map>,
        Response.ErrorListener {

    @InjectView(R.id.editText)
    EditText userName;
    @Inject
    CustomVolley volley;
    @Inject
    JsonParser jsonParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.inject(this); //view injection entry point

    }

    @OnClick(R.id.button) //cleaner than listeners
    public void getNumberOfReposForUser() {
        String url = "http://www.reddit.com/r/aww.json?limit=5";
        GsonRequest request = new GsonRequest<Map>(Request.Method.GET, url, Map.class, this, this);

        request.setRetryPolicy(new DefaultRetryPolicy(30000, 1, 1));
        // request.setShouldCache(false);
        request.setTag("Demo");
        volley.getRequestQueue().add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }


    @Override
    public void onResponse(Map response) {

    }
}
