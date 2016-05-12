package com.kevin.drift.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevin.drift.Entity.DriftMessageInfo;
import com.kevin.drift.R;
import com.kevin.drift.Utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Benson_Tom on 2016/4/28.
 */
public class DriftMessageAdapter extends RecyclerView.Adapter<DriftMessageAdapter.DriftViewHolder>{
    private static final String TAG = "DriftMessageAdapter";
    private static final String mImgUrl="http://pic.mmfile.net/2013/08/131T95F0-2.jpg";
    private List<DriftMessageInfo> mList;
    private Context mContext;


    public DriftMessageAdapter(Context context,List<DriftMessageInfo> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public DriftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.world_recyclerview_item,parent,false);
        DriftViewHolder holder = null;
        if (holder == null){
            holder = new DriftViewHolder(view);
            parent.setTag(holder);
        }else {
            holder = (DriftViewHolder) parent.getTag();
        }
        Picasso.with(mContext).load(mImgUrl).into(holder.driftImg);
        Picasso.with(mContext).load(mImgUrl).placeholder(R.drawable.ic_weixin_login_normal).transform(new CircleTransform()).into(holder.userIcon);
        return holder;
    }

    @Override
    public void onBindViewHolder(DriftViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class DriftViewHolder extends RecyclerView.ViewHolder{
        private ImageView userIcon;
        private TextView userName;
        private TextView userAddress;
        private TextView driftTime;
        private ImageView driftImg;
        private TextView driftContent;

        public DriftViewHolder(View itemView) {
            super(itemView);
            userIcon = (ImageView) itemView.findViewById(R.id.id_drift_userIcon);
            userName = (TextView) itemView.findViewById(R.id.id_drift_userName);
            userAddress = (TextView) itemView.findViewById(R.id.id_drift_address);
            driftTime = (TextView) itemView.findViewById(R.id.id_drift_sendTime);
            driftImg = (ImageView) itemView.findViewById(R.id.id_drift_img);
            driftContent = (TextView) itemView.findViewById(R.id.id_drift_text);
        }
    }
}
