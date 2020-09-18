package com.chata.chata.scheduling;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chata.chata.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ScheduleAdapter extends ArrayAdapter<ScheduleEntity> {
    private ArrayList<ScheduleEntity> items;

    public ScheduleAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ScheduleEntity> objects) {
        super(context, resource, objects);
        this.items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.schedule_row, null);
        }
        ScheduleEntity entity = items.get(position);
        if (entity != null) {
            TextView roomTextView = v.findViewById(R.id.rowRoom);
            TextView timeTextView = v.findViewById(R.id.rowTime);
            if (roomTextView != null) {
                roomTextView.setText(entity.getRoomNiceName());                            }
            if(timeTextView != null){
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, entity.getTime().getHour());
                cal.set(Calendar.MINUTE, entity.getTime().getMinute());
                timeTextView.setText(new SimpleDateFormat("HH:mm").format(cal.getTime()));
            }

            ImageView icon = v.findViewById(R.id.rowIcon);
            if (entity.getType() == ScheduleType.BLINDS) {
                if (entity.getOnOrUp())
                    icon.setImageResource(R.drawable.blinds_open);
                else
                    icon.setImageResource(R.drawable.blinds_closed);
            }
            else {
                if (entity.getOnOrUp())
                    icon.setImageResource(R.drawable.light_on);
                else
                    icon.setImageResource(R.drawable.light_off);
            }

        }
        return v;
    }
}
