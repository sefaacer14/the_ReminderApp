package com.example.reminder_sefa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {

    public List<Reminder> rem_list;

    public class myViewHolder extends RecyclerView.ViewHolder {
        public TextView Rem1;

        public myViewHolder(View itemView) {
            super(itemView);
            Rem1 = (TextView) itemView.findViewById(R.id.Reminder1);
        }
    }

    public myAdapter(List<Reminder> rem_list) {
        this.rem_list = rem_list;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_reminders, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Reminder currentReminder = rem_list.get(position);
        holder.Rem1.setText(currentReminder.getData());
    }

    @Override
    public int getItemCount() {
        return rem_list.size();
    }
}



