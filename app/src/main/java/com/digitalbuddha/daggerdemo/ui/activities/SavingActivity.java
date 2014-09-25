package com.digitalbuddha.daggerdemo.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.digitalbuddha.daggerdemo.activitygraphs.R;
import com.digitalbuddha.daggerdemo.job.GetSavingTypesJob;
import com.digitalbuddha.daggerdemo.model.SavingRecord;
import com.digitalbuddha.daggerdemo.model.SavingsType;
import com.digitalbuddha.daggerdemo.ui.actionbars.TypefaceActionBar;
import com.digitalbuddha.daggerdemo.ui.adapters.SavingListAdapter;
import com.digitalbuddha.daggerdemo.ui.fragments.SavingTypeFragment;
import com.google.gson.Gson;
import com.path.android.jobqueue.JobManager;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

public class SavingActivity extends FragmentActivity {

    public SavingListAdapter savingListAdapter;
    private static String ARG_NUMBER = "position";
    private static String SAVINGS_TYPE = "savingstype";
    private List<SavingRecord> savingsRecords = new ArrayList<SavingRecord>();
    private SavingPagerAdapter savingPagerAdapter;
    @InjectView(R.id.signup_list)
    RecyclerView recyclerView;
    @InjectView(R.id.savings_pager)
    ViewPager savingViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_phone);
        ButterKnife.inject(this);

        EventBus.getDefault().register(this);

        createRecyclerView();
        createTopPager();

        new TypefaceActionBar(this, "Where Can You Save?");
    }

    private void createRecyclerView() {
        savingListAdapter = new SavingListAdapter(this, savingsRecords, R.layout.savings_recycler_view);
        recyclerView.setAdapter(savingListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
    }

    public void createTopPager() {
        savingPagerAdapter = new SavingPagerAdapter(getSupportFragmentManager());
        savingViewPager.setAdapter(savingPagerAdapter);
        JobManager jobManager = new JobManager(getApplicationContext());
        jobManager.addJobInBackground(new GetSavingTypesJob());
    }

    public class SavingPagerAdapter extends FragmentStatePagerAdapter {
        private List<SavingsType> savingsTypes = new ArrayList<SavingsType>();

        public SavingPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            Fragment fragmentSavingsType = new SavingTypeFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_NUMBER, position);
            Gson gson = new Gson();
            args.putString(SAVINGS_TYPE, gson.toJson(savingsTypes.get(position)));
            fragmentSavingsType.setArguments(args);
            return fragmentSavingsType;
        }

        @Override
        public int getCount() {
            return savingsTypes.size();
        }

        public void dataChanged(List<SavingsType> savingsTypes) {
            this.savingsTypes = savingsTypes;
            getCount();
            notifyDataSetChanged();
        }
    }

    public void onEventMainThread(GetSavingTypesJob savingsTypeJob)
    {
        List<SavingsType> savings = savingsTypeJob.savingsTypes;
        savingPagerAdapter.dataChanged(savings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.saving, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            //TODO: save records here

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
