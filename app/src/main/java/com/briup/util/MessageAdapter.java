package com.briup.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.briup.bean.Message;
import com.briup.myaccountapp.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<Message> list;

    public MessageAdapter(Context context, List<Message> list) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        // TODO Auto-generated method stub
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // TODO Auto-generated method stub
        if (holder instanceof viewHolder) {
            ((viewHolder) holder).tv_nnme.setText(list.get(position).getName());
            ((viewHolder) holder).tv_content.setText(list.get(position).getContent());
            ((viewHolder) holder).tv_time.setText(list.get(position).getDate());
            ((viewHolder) holder).rl_parent.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
//                    BaobiaoFragment.pieChart.highlightValue(position, 0);
                }
            });
        }

    }

    class viewHolder extends ViewHolder {

        private RelativeLayout rl_parent;
        private TextView tv_nnme;
        private TextView tv_content;
        private TextView tv_time;

        public viewHolder(View itemView) {
            super(itemView);
            rl_parent = (RelativeLayout) itemView.findViewById(R.id.rl_parent);
            tv_nnme = (TextView) itemView.findViewById(R.id.tv_nnme);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }

    }
}
