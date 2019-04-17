package com.example.henry.marathon.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.henry.marathon.R;
import com.example.henry.marathon.javabean.Obj;

import java.util.List;
public class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder> {
    private List<Obj> itemList;
    public Myadapter(List<Obj> itemList){
        this.itemList = itemList;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView ojbname;
        TextView person_name;
        TextView person_tel;
        TextView time;
        TextView describe;
        public ViewHolder(View view){
            super(view);
            ojbname = view.findViewById(R.id.obj_name);
            person_name = view.findViewById(R.id.person_name);
            person_tel = view.findViewById(R.id.person_tel);
            time = view.findViewById(R.id.obj_date);
            describe = view.findViewById(R.id.describe);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.obj_item,viewGroup,false);
         return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Obj temp = itemList.get(position);
        int year = temp.getDate().intValue()/10000;
        int month = temp.getDate().intValue()%10000/100;
        int day = temp.getDate().intValue()%100;
        viewHolder.ojbname.setText(temp.getName());
        viewHolder.person_name.setText(temp.getPerson_name());
        viewHolder.person_tel.setText(temp.getPerson_tel());
        viewHolder.time.setText(String.valueOf(year)+"/"+month+"/"+day);
        viewHolder.describe.setText(temp.getDescribe()+","+temp.getLocationdesc());
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

