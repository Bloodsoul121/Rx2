package com.blood.rx2.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blood.rx2.R;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private final Context mContext;
    private List<String> mDatas;
    private CallBack mCallBack;

    MainAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<String> list) {
        this.mDatas = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_sub_function, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        final String str = mDatas.get(i);
        viewHolder.mTv.setText(str);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null) {
                    mCallBack.onClick(str, i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.tv);
        }
    }

    public interface CallBack {
        void onClick(String str, int position);
    }

    void setCallBack(CallBack callBack) {
        this.mCallBack = callBack;
    }

}
