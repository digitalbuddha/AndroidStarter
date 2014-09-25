package com.digitalbuddha.daggerdemo.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.digitalbuddha.daggerdemo.activitygraphs.R;
import com.digitalbuddha.daggerdemo.model.SavingRecord;
import com.digitalbuddha.daggerdemo.utils.MyVolley;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Created by patriciaestridge on 8/21/14.
 */
public class SavingListAdapter extends RecyclerView.Adapter<SavingListAdapter.ViewHolder> {
    public List<SavingRecord> savings = new ArrayList<SavingRecord>();
    private Context context;
    private int itemLayout;

    public SavingListAdapter(Context context, List<SavingRecord> savings, int itemLayout) {
        super();
        this.context = context;
        this.savings = savings;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final SavingRecord item = savings.get(i);

        viewHolder.text.setText(item.getSavingsType().getTitle());
        viewHolder.image.setImageUrl(item.getSavingsType().getIconUrl(), MyVolley.getImageLoader());

        if (i == 0) {
            viewHolder.nextIcon.setImageUrl(item.getSavingsType().getNext(), MyVolley.getImageLoader());
            TranslateAnimation anim = bounceFromAbove();
            viewHolder.nextIcon.startAnimation(anim);
        } else {
            viewHolder.nextIcon.setImageUrl(item.getSavingsType().getNext(), MyVolley.getImageLoader());;
        }
        nextOnClick(viewHolder);

        if (i == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewHolder.deleteIcon.setImageUrl(item.getSavingsType().getDelete(), MyVolley.getImageLoader());
                    AnimationSet rollingLeft = rollingLeftCall();
                    viewHolder.deleteIcon.startAnimation(rollingLeft);
                }
            }, 1000);
        } else {
            viewHolder.deleteIcon.setImageUrl(item.getSavingsType().getDelete(), MyVolley.getImageLoader());
        }

        viewHolder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(item);
            }
        });

        createColorBackground(viewHolder, item);
        setNumberPickers(viewHolder, item);
        backOnClick(viewHolder);

    }

    private void createColorBackground(ViewHolder viewHolder, SavingRecord item) {
        String colorIdString = item.getSavingsType().getId();
        int colorInt = context.getResources().getIdentifier(colorIdString, "color", context.getPackageName());
        String colorString = context.getResources().getString(colorInt);
        String[] colorStringArray = colorString.split("");
        StringBuilder fiftyOpacity = new StringBuilder();

        //Change hex color string to 50% opacity
        for (int j = 0; j < colorStringArray.length; j++)
        {
            if (j == 2) { colorStringArray[j] = "B"; }
            if (j == 3) { colorStringArray[j] = "3"; }
            if (j > 0) { fiftyOpacity.append(colorStringArray[j]); }
        }
        colorString = fiftyOpacity.toString();
        viewHolder.sliderView.setBackgroundColor(Color.parseColor(colorString));
    }

    private void backOnClick(final ViewHolder viewHolder) {
        viewHolder.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                TranslateAnimation anim = new TranslateAnimation(0, 1200, 0, 0);
                anim.setDuration(1000);
                anim.setInterpolator(new LinearInterpolator());
                anim.setRepeatCount(0);
                anim.setFillAfter(false);

                viewHolder.savingsView.startAnimation(anim);
                viewHolder.text.setVisibility(View.VISIBLE);
                viewHolder.nextIcon.setVisibility(View.VISIBLE);
                viewHolder.deleteIcon.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.savingsView.setVisibility(View.GONE);
                        viewHolder.acceptIcon.setVisibility(View.GONE);
                        viewHolder.backIcon.setVisibility(View.GONE);
                        nextOnClick(viewHolder);
                    }
                }, 1000);
            }
        });
    }

    private void nextOnClick(final ViewHolder viewHolder) {
        viewHolder.nextIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.setVisibility(View.GONE);
                viewHolder.deleteIcon.setVisibility(View.GONE);
                viewHolder.text.setVisibility(View.GONE);
                viewHolder.savingsView.setVisibility(View.VISIBLE);

                TranslateAnimation anim = new TranslateAnimation(1000, 0, 0, 0);
                anim.setDuration(1000);
                anim.setInterpolator(new LinearInterpolator());
                anim.setRepeatCount(0);
                anim.setFillAfter(false);
                viewHolder.savingsView.startAnimation(anim);
                viewHolder.acceptIcon.setVisibility(View.VISIBLE);
                viewHolder.backIcon.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setNumberPickers(ViewHolder viewHolder, SavingRecord item)
    {
        viewHolder.frequency.setMaxValue(25);
        viewHolder.frequency.setMinValue(0);

        Locale locale = context.getResources().getConfiguration().locale;
        Currency localCurrency = Currency.getInstance(locale);
        String currencySymbol = localCurrency.getSymbol(locale);

        String[] savingsAmounts = new String[300];
        for (int i = 0; i < 300; i++) {
            double amount = i * 0.50;
            savingsAmounts[i] = currencySymbol + Double.toString(amount)+"0";
        }
        viewHolder.amountSaved.setMaxValue(savingsAmounts.length-1);
        viewHolder.amountSaved.setMinValue(0);
        viewHolder.amountSaved.setDisplayedValues(savingsAmounts);

        String[] frequencyValuesArray = new String[] {
                "Daily",
                "Weekly",
                "Monthly",
                "Yearly"
        };
        viewHolder.frequencyString.setMaxValue(frequencyValuesArray.length-1);
        viewHolder.frequencyString.setMinValue(0);
        viewHolder.frequencyString.setDisplayedValues(frequencyValuesArray);
    }

    private TranslateAnimation bounceFromAbove() {
        TranslateAnimation bounceAnim = new TranslateAnimation(0, 0, -1000, 0);
        bounceAnim.setDuration(1000);
        bounceAnim.setInterpolator(new BounceInterpolator());
        bounceAnim.setRepeatCount(0);
        bounceAnim.setFillAfter(false);
        return bounceAnim;
    }

    private AnimationSet rollingLeftCall() {
        AnimationSet rollingLeft = new AnimationSet(true);
        rollingLeft.setFillEnabled(true);

        TranslateAnimation movingLeft = new TranslateAnimation(940, 0, 0, 0);
        movingLeft.setFillAfter(true);
        movingLeft.setDuration(2000);
        movingLeft.setRepeatCount(0);

        RotateAnimation rolling = new RotateAnimation(359, 0, Animation.RELATIVE_TO_SELF, 0.5255f,
                Animation.RELATIVE_TO_SELF, 0.5255f);
        rolling.setDuration(400);
        rolling.setInterpolator(new LinearInterpolator());
        rolling.setFillAfter(false);
        rolling.setRepeatCount(3);

        rollingLeft.addAnimation(rolling);
        rollingLeft.addAnimation(movingLeft);
        return rollingLeft;
    }

    @Override
    public int getItemCount() {
        return savings.size();
    }

    public void add(SavingRecord item, int position) {
        savings.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(SavingRecord item) {
        int position = savings.indexOf(item);
        savings.remove(position);
        notifyItemRemoved(position);
    }

    public void dataSetChanged(List<SavingRecord> list)
    {
        this.savings = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public NetworkImageView image;
        public TextView text;
        public NetworkImageView nextIcon;
        public NetworkImageView deleteIcon;
        public RelativeLayout savingsView;
        public NetworkImageView backIcon;
        public NetworkImageView acceptIcon;
        public View sliderView;
        public NumberPicker amountSaved;
        public NumberPicker frequency;
        public NumberPicker frequencyString;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (NetworkImageView) itemView.findViewById(R.id.saving_icon);
            text = (TextView) itemView.findViewById(R.id.saving_title);
            nextIcon = (NetworkImageView) itemView.findViewById(R.id.next_icon);
            deleteIcon = (NetworkImageView) itemView.findViewById(R.id.delete_icon);
            savingsView = (RelativeLayout) itemView.findViewById(R.id.slider_second_page);
            sliderView = itemView.findViewById(R.id.slider_second_page_backdrop);
            backIcon = (NetworkImageView) itemView.findViewById(R.id.back_icon);
            acceptIcon = (NetworkImageView) itemView.findViewById(R.id.accept_icon);
            amountSaved = (NumberPicker) itemView.findViewById(R.id.money_amount);
            frequency = (NumberPicker) itemView.findViewById(R.id.numeric_frequency);
            frequencyString = (NumberPicker) itemView.findViewById(R.id.text_frequency);
        }
    }

    public List<SavingRecord> getSavingsList() {
        return savings;
    }
}
