package ui.com.coolweather.Note;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import ui.com.coolweather.R;

/**
 * Created by asus on 2018/6/13.
 */

public class MyAdapter extends BaseAdapter {
    Context context;
    List<Map<String, Object>> datelist;
    private ViewHolder viewHolder;
    public Context getContext(){
        return context.getApplicationContext();
    }
    public MyAdapter(Context context, List<Map<String, Object>> datelist){
        this.context=context;
        this.datelist=datelist;
    }
    @Override
    public int getCount() {
        int count=0;
        if(datelist!=null){
            return datelist.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return datelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=View.inflate(getContext(), R.layout.item,null);

            viewHolder=new ViewHolder();
            viewHolder.tv_body= (TextView) convertView.findViewById(R.id.nei);
            viewHolder.tv_time= (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_body.setText("便签内容："+datelist.get(position).get("tv_content").toString());
        viewHolder.tv_time.setText(datelist.get(position).get("tv_date").toString());



        return convertView;
    }
    class ViewHolder{
        TextView tv_body,tv_time;
    }
}
