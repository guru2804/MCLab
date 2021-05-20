package com.example.Covid19Tracker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Covid19Tracker.StateData;
import com.example.Covid19Tracker.Models.StateModel;
import com.example.Covid19Tracker.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.Covid19Tracker.Constants.STATE_ACTIVE;
import static com.example.Covid19Tracker.Constants.STATE_CONFIRMED;
import static com.example.Covid19Tracker.Constants.STATE_DECEASED;
import static com.example.Covid19Tracker.Constants.STATE_NAME;
import static com.example.Covid19Tracker.Constants.STATE_RECOVERED;


public class StateClass extends RecyclerView.Adapter<StateClass.MyViewHolder> {
    private Context mContext;
    private ArrayList<StateModel> stateModelArrayList;
    private String searchText="";
    private SpannableStringBuilder sb;

    public StateClass(Context mContext, ArrayList<StateModel> stateModelArrayList) {
        this.mContext = mContext;
        this.stateModelArrayList = stateModelArrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_state_wise, parent, false);
        return new MyViewHolder(view);
    }

    public void filterList(ArrayList<StateModel> filteredList, String text) {
        stateModelArrayList = filteredList;
        this.searchText = text;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        StateModel currentItem = stateModelArrayList.get(position);
        String stateName = currentItem.getState();
        String stateTotal = currentItem.getConfirmed();
        String stateRank = String.valueOf(position+1);
        int stateTotalInteger = Integer.parseInt(stateTotal);
        Log.d("state rank", stateRank);
        holder.tv_rankTextView.setText(stateRank+".");
        Log.d("state total cases int", String.valueOf(stateTotalInteger));
        holder.tv_stateTotalCases.setText(NumberFormat.getInstance().format(stateTotalInteger));
        holder.tv_stateName.setText(stateName);

        if(searchText.length()>0){
            int index = stateName.indexOf(searchText);
            sb = new SpannableStringBuilder(stateName);
            Pattern word = Pattern.compile(searchText.toLowerCase());
            Matcher match = word.matcher(stateName.toLowerCase());
            while(match.find()){
                ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(52, 195, 235)); //specify color here
                sb.setSpan(fcs, match.start(), match.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);


            }
            holder.tv_stateName.setText(sb);

        }else{
            holder.tv_stateName.setText(stateName);
        }

        holder.lin_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StateModel clickedItem = stateModelArrayList.get(position);
                Intent perState = new Intent(mContext, StateData.class);

                perState.putExtra(STATE_NAME, clickedItem.getState());
                perState.putExtra(STATE_CONFIRMED, clickedItem.getConfirmed());
                perState.putExtra(STATE_ACTIVE, clickedItem.getActive());
                perState.putExtra(STATE_RECOVERED, clickedItem.getRecovered());
                perState.putExtra(STATE_DECEASED, clickedItem.getDeceased());

                mContext.startActivity(perState);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stateModelArrayList ==null || stateModelArrayList.isEmpty() ? 0 : stateModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_stateName, tv_stateTotalCases, tv_rankTextView;
        LinearLayout lin_state;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_stateName = itemView.findViewById(R.id.layout_state_wise_state_name_textview);
            tv_stateTotalCases = itemView.findViewById(R.id.layout_state_wise_confirmed_textview);
            tv_rankTextView = itemView.findViewById(R.id.layout_state_wise_rank);
            lin_state = itemView.findViewById(R.id.layout_state_wise_lin);
        }
    }
}

