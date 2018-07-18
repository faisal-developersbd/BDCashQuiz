package net.smactechnology.medha;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    private List<gridItem> itemList;
    private Context context;
    public GridAdapter() {
    }

    public GridAdapter(Context context,List<gridItem> itemList) {
        this.context = context;
        this.itemList=itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.winnerchild,null);
        }
        ImageView imageView=convertView.findViewById(R.id.winnerImage);
        TextView nameText=convertView.findViewById(R.id.name);
        TextView balance=convertView.findViewById(R.id.balance);
        Log.d("distroyCall","Curser is here");
        Glide.with(context).load(itemList.get(position).getPhotUrl()).fitCenter().error(R.color.red_error).into(imageView);
        nameText.setText(itemList.get(position).getNameText());
        balance.setText("৳ "+itemList.get(position).getBalanceText());
        return convertView;
    }
}
