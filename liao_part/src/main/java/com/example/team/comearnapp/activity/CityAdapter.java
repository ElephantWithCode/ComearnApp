package com.example.team.comearnapp.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team.comearnapp.R;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by dongjunkun on 2015/7/4.
 */
public class CityAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private List<City> cities;
    private Context context;
    private  boolean select=false;
    public void setSelect(boolean select) {
        this.select = select;
    }

    private HashMap<String, Boolean> isSelected;
    public  HashMap<String, Boolean> getIsSelected() {
        return isSelected;
    }
    public void setSelectAll(List<City> cities){
        for (int i = 0; i < cities.size(); i++) {
            isSelected.put(cities.get(i).getCity(), true);
        }
    }
    public void setSelectReverse(List<City> cities){
        for (int i = 0; i < cities.size(); i++) {
            isSelected.put(cities.get(i).getCity(), !isSelected.get(cities.get(i).getCity()));
        }
    }
    public void initIsSelected(List<City> cities){
        for (int i = 0; i < cities.size(); i++) {
            isSelected.put(cities.get(i).getCity(), false);
        }
    }

    public CityAdapter(List<City> cities,Context context) {
        this.cities = cities;
        this.context=context;
        isSelected = new HashMap<String, Boolean>();
    }


    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeadViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (HeadViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_layout, null);
            viewHolder = new HeadViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        //必须加""连接，否则会出现空指针异常，原因是setText方法传入整型参数会被解析成资源类型
        viewHolder.mHeadText.setText(""+cities.get(position).getSortLetter().charAt(0));
        return convertView;
    }

    /**
     * 更新列表
     * @param cities
     */
    public void updateList(List<City> cities){
        this.cities = cities;
        notifyDataSetChanged();
    }

    @Override
    public long getHeaderId(int i) {
        return cities.get(i).getSortLetter().charAt(0);
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        final String mName=cities.get(position).getCity();
        viewHolder.mCity.setText(mName);

        viewHolder.mCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+mName, Toast.LENGTH_SHORT).show();
            }
        });

        if(select) {
            viewHolder.mCheckbox.setVisibility(View.VISIBLE);
            if (isSelected.get(cities.get(position).getCity())) {
                viewHolder.mCheckbox.setChecked(true);
            } else {
                viewHolder.mCheckbox.setChecked(false);
            }
        }else{
            viewHolder.mCheckbox.setVisibility(View.INVISIBLE);
        }


        viewHolder.mCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(select) {
                    if(viewHolder.mCheckbox.isChecked()){
                        isSelected.put(mName, true);
                    }else{
                        isSelected.put(mName, false);
                    }
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView mCity;
        CheckBox mCheckbox;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            mCity = view.findViewById(R.id.city);
            mCheckbox = view.findViewById(R.id.item_cb);
        }
    }

    static class HeadViewHolder {
        TextView mHeadText;

        HeadViewHolder(View view) {
            ButterKnife.bind(this, view);
            mHeadText = view.findViewById(R.id.headText);
        }
    }

    //根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = cities.get(i).getSortLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

}
