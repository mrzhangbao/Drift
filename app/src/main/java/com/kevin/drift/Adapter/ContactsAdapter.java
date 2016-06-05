package com.kevin.drift.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.kevin.drift.Entity.MobileContacts;
import com.kevin.drift.R;

import java.util.List;
import java.util.Random;

/**
 * Created by Benson_Tom on 2016/4/3.
 * 手机联系人的适配器
 * 实现SectionIndexer的接口有两个方法
 * 1、存放索引提示信息，程序根据通讯录动态生成
 *2、存放sectionContent的开始的位置
 */
public class ContactsAdapter extends BaseAdapter implements SectionIndexer {

    private List<MobileContacts> mList;
    private Context mContext;


    /**
     * 构造方法，传入联系人数据和上下文对象
     * @param m
     * @param context
     */
    public ContactsAdapter(List<MobileContacts> m, Context context) {
        mList = m;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        final MobileContacts mContent = mList.get(i);
        //内存优化
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.datesort_item, null);
            viewHolder.mTitle = (TextView) view.findViewById(R.id.id_sortTitle_tv);
            viewHolder.mSortData = (TextView) view.findViewById(R.id.id_sortContent_tv);
            viewHolder.mUserIcon = (TextView) view.findViewById(R.id.id_userIcon_tv);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (i == 0){

        }
        //根据position获取分类的守字母的char ascii值
        int section = getSectionForPosition(i);

        //如果当前位置等于该分类首字母的char的位置，则认为第一次出现
        if (i == getPositionForSection(section)) {
            viewHolder.mTitle.setVisibility(View.VISIBLE);
            viewHolder.mTitle.setText(mContent.getUserFirstLetter());
            viewHolder.mTitle.setBackgroundColor(Color.parseColor("#efefef"));
        } else {
            viewHolder.mTitle.setVisibility(View.GONE);
        }
        Log.i("TAG", "首字符：" + mList.get(i).getName().substring(0, 1));
        viewHolder.mUserIcon.setText(mList.get(i).getName().substring(0, 1));
        //设定用户头像
        final Drawable originDrawable = viewHolder.mUserIcon.getBackground();
        viewHolder.mUserIcon.setBackground(tintDrawable(originDrawable, ColorStateList.valueOf(getRandomColor())));

        //设定联系人Display_name
        viewHolder.mSortData.setText(this.mList.get(i).getName());

        //通过匿名内部类的方式实现监听事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,mList.get(i).getName(),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    /**
     * 当ListView中的数据发生变化的时候，调用该方法更新
     *
     * @param list
     */
    public void updateListView(List<MobileContacts> list) {
        mList = list;
        notifyDataSetChanged();

    }

    @Override
    public Object[] getSections() {
        return null;
    }

    /**
     * 根据分类的首字母的Char ascii 值获取其第一次出现该首字母的位置
     *
     * @param position
     * @return
     */
    @Override
    public int getPositionForSection(int position) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mList.get(i).getUserFirstLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == position) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     *
     * @param position
     * @return
     */
    @Override
    public int getSectionForPosition(int position) {
        return mList.get(position).getUserFirstLetter().charAt(0);
    }


    /**
     * 通过Tint的方式对头像进行着色
     * @param drawable
     * @param colors
     * @return
     */
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    public int getRandomColor() {
        Random rand = new Random();
        int randNum = rand.nextInt(6);
        if (randNum == 1) {
            return Color.parseColor("#00EE76");
        }
        if (randNum == 2) {
            return Color.parseColor("#FF00FF");
        }
        if (randNum == 3) {
            return Color.parseColor("#436EEE");
        }
        if (randNum == 4) {
            return Color.parseColor("#FFA500");
        }
        if (randNum == 5) {
            return Color.parseColor("#00FF7F");
        }
        if (randNum == 6) {
            return Color.parseColor("#00FF7F");
        }

        return Color.parseColor("#00BFFF");
    }


    final class ViewHolder {
        public TextView mTitle;
        public TextView mSortData;
        public TextView mUserIcon;

    }
}
