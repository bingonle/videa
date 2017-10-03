package com.sopao.videa;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/10/1 0001.
 */

public class LazyAdapter2 extends BaseAdapter

    {

        private Activity activity;
        private String[] data;
        private String[] name;
        private String[] num;
        private static LayoutInflater inflater=null;
        public ImageLoader imageLoader;

    public LazyAdapter2(Activity a, String[] d, String[] n,String[] l) {
        activity = a;
        data=d;
        name=n;
        num=l;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.listitem, null);
        ImageView image=BaseViewHolder.get(vi, R.id.image);
        imageLoader.DisplayImage(data[position], image);
        TextView text=(TextView)vi.findViewById(R.id.text);
        text.setText(name[position]);

        return vi;
    }
}

