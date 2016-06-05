package com.kevin.drift.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevin.drift.Entity.DriftMessageInfo;
import com.kevin.drift.R;
import com.kevin.drift.Utils.CircleTransform;
import com.kevin.drift.Utils.RandomMessage;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Benson_Tom on 2016/4/28.
 * 首页消息的适配器
 * 通过RecyclerView来实现数据的列表形式
 */
public class DriftMessageAdapter extends RecyclerView.Adapter<DriftMessageAdapter.DriftViewHolder>{
    private static final String TAG = "DriftMessageAdapter";
    private static final String mImgUrl="http://pic.mmfile.net/2013/08/131T95F0-2.jpg";
    private List<DriftMessageInfo> mList;
    private Context mContext;


    /**
     * 构造方法，传入上下文对象和装有Message的消息List容器
     * @param context
     * @param list
     */
    public DriftMessageAdapter(Context context,List<DriftMessageInfo> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public DriftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //RecyclerView的item布局关联
        View view = LayoutInflater.from(mContext).inflate(R.layout.world_recyclerview_item,parent,false);
         DriftViewHolder holder = null;
        //性能优化
        if (holder == null){
            holder = new DriftViewHolder(view);
            parent.setTag(holder);
        }else {
            holder = (DriftViewHolder) parent.getTag();
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(DriftViewHolder holder, int position) {
        //为各个控件进行赋值，根据position来获取当前要显示的数据，并显示出来
        holder.driftContent.setText(mList.get(position).getDriftContent());
        holder.userAddress.setText(mList.get(position).getDriftAddress());
        holder.userName.setText(RandomMessage.getUser(mList.get(position).getUserID()).getUsername());
        //通过Picasso图片加载框架来实现用户头像和消息图像的图片的显示
        final String userIcon = (RandomMessage.getUser(mList.get(position).getUserID()).getUserIcon());
        Picasso.with(mContext).load(userIcon).placeholder(R.drawable.ic_weixin_login_normal).transform(new CircleTransform()).into(holder.userIcon);
        Picasso.with(mContext).load(mList.get(position).getDriftImg()).into(holder.driftImg);
        Log.i(TAG,"列表信息："+position+mList.toString());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 使用ViewHolder，可以不用每次适配器getView的时候都创建新的对象，
     * 提高了软件的性能
     * 并且降低了内存消耗
     */
    public static class DriftViewHolder extends RecyclerView.ViewHolder{
        private ImageView userIcon;
        private TextView userName;
        private TextView userAddress;
        private TextView driftTime;
        private ImageView driftImg;
        private TextView driftContent;

        public DriftViewHolder(View itemView) {
            super(itemView);
            //RecyclerView 中Item 的控件查找
            userIcon = (ImageView) itemView.findViewById(R.id.id_drift_userIcon);
            userName = (TextView) itemView.findViewById(R.id.id_drift_userName);
            userAddress = (TextView) itemView.findViewById(R.id.id_drift_address);
            driftTime = (TextView) itemView.findViewById(R.id.id_drift_sendTime);
            driftImg = (ImageView) itemView.findViewById(R.id.id_drift_img);
            driftContent = (TextView) itemView.findViewById(R.id.id_drift_text);
        }
    }
}
