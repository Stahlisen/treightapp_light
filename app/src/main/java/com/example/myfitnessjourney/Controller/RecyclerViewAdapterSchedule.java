package com.example.myfitnessjourney.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Model.Alarm;

/**
 * Created by fredrikstahl on 15-12-13.
 */
public class RecyclerViewAdapterSchedule extends RecyclerView.Adapter<RecyclerViewAdapterSchedule.AlarmViewHolder> {
        Context mContext;
        List<Alarm> mAlarms;

        RecyclerViewAdapterSchedule(List<Alarm> alarms, Context context){
            mContext = context;
            mAlarms = alarms;
        }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_card_fragment, parent, false);
        AlarmViewHolder avh = new AlarmViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterSchedule.AlarmViewHolder holder, int position) {
        Alarm alarm = mAlarms.get(position);
        holder.mTextView_alarmName.setText(alarm.getName());
        holder.mTextView_alarmDay.setText(getWeekdayFromInt(alarm.getWeekday()));
        String minuteString = String.format("%02d", alarm.getMinutes());
        holder.mTextView_alarmTime.setText(alarm.getHour() + ":" + minuteString);
        holder.mAlarmSwitch.setChecked(alarm.getActivated());

        holder.mRemoveAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setMessage(R.string.message_removeAlarm)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //TODO: Delete alarm from realm
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

            }
        });

        holder.mAlarmSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(mContext)
                        .setMessage(R.string.message_reschduleAlarm)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //TODO: Unschedule/Reschedule alarm
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();


            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recView) {
        super.onAttachedToRecyclerView(recView);
    }

    @Override
    public int getItemCount() {

        return mAlarms.size();
    }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mTextView_alarmName;
        TextView mTextView_alarmTime;
        TextView mTextView_alarmDay;
        SwitchCompat mAlarmSwitch;
        ImageView mRemoveAlarm;

        AlarmViewHolder(View itemView) {
            super(itemView);

            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mTextView_alarmName = (TextView) itemView.findViewById(R.id.alarmName);
            mTextView_alarmTime = (TextView) itemView.findViewById(R.id.alarmTime);
            mTextView_alarmDay = (TextView) itemView.findViewById(R.id.alarmDay);
            mAlarmSwitch = (SwitchCompat) itemView.findViewById(R.id.alarmSwitch);
            mRemoveAlarm = (ImageView) itemView.findViewById(R.id.alarmRemove);

        }

    }

    public String getWeekdayFromInt (int dayOfWeek) {
        String weekday = "";

        switch(dayOfWeek) {
            case 1:
                weekday = "Monday";
                break;
            case 2:
                weekday = "Tuesday";
                break;
            case 3:
                weekday = "Wednesday";
                break;
            case 4:
                weekday = "Thursday";
                break;
            case 5:
                weekday = "Friday";
                break;
            case 6:
                weekday = "Saturday";
                break;
            case 7:
                weekday = "Sunday";
                break;
        }

        return weekday;
    }



}
