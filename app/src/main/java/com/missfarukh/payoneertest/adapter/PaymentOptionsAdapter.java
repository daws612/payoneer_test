package com.missfarukh.payoneertest.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.missfarukh.payoneertest.R;
import com.missfarukh.payoneertest.model.ApplicableNetwork;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PaymentOptionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String TAG = PaymentOptionsAdapter.class.getSimpleName();

    Activity context;
    List<ApplicableNetwork> applicableNetworkList = new ArrayList<>();

    public PaymentOptionsAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.payment_option_item, parent, false);
        return new PaymentOptionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        try {
            ApplicableNetwork applicableNetwork = applicableNetworkList.get(position);
            PaymentOptionsViewHolder viewHolder = (PaymentOptionsViewHolder) holder;

            viewHolder.txtLabel.setText(applicableNetwork.getLabel());
            Picasso.get().load(applicableNetwork.getLinks().get("logo").toString()).placeholder(R.drawable.ic_launcher_background).into(viewHolder.imgLogo);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return applicableNetworkList == null ? 0 : applicableNetworkList.size();
    }

    class PaymentOptionsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;
        TextView txtLabel;

        public PaymentOptionsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            txtLabel = itemView.findViewById(R.id.txtLabel);
        }
    }

    public void setApplicableNetworkList(List<ApplicableNetwork> applicableNetworkList) {
        this.applicableNetworkList = applicableNetworkList;
        notifyDataSetChanged();
    }
}
