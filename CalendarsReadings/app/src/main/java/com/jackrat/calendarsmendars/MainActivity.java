package com.jackrat.calendarsmendars;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ListView lv1;
    ListView lv2;
    ArrayList<CalendarItem> allCalendars;
    ArrayList<EventItem> eventsList;
    SimpleDateFormat sdfDayMonth = new SimpleDateFormat("dd MMM", new Locale("pl", "PL"));
    SimpleDateFormat sdfDayWeek = new SimpleDateFormat("E", new Locale("pl", "PL"));
    SimpleDateFormat sdfMonthYear = new SimpleDateFormat("LLLL\nyyyy", new Locale("pl", "PL"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);
        try {
            readCalendars();
        }
        catch (Exception exc)
        {
            (new AlertDialog.Builder(this))
                    .setTitle("Bład")
                    .setMessage(exc.getMessage())
                    .show();
        }
    }

    public void readCalendars() {
        Uri calUri = CalendarContract.Calendars.CONTENT_URI;
        Cursor cursor = getContentResolver().query(calUri,
                new String[]{
                        CalendarContract.Calendars._ID,
                        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                        CalendarContract.Calendars.VISIBLE,
                        CalendarContract.Calendars.CALENDAR_COLOR
                },
                null,
                null,
                null
        );
        allCalendars = new ArrayList<CalendarItem>();
        if (cursor.moveToFirst()) {
            do {
                allCalendars.add(new CalendarItem(cursor));
            } while (cursor.moveToNext());
        }
        lv1.setAdapter(getAdapterForCalendars());
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                eventsList = readEventsFromCalendar(position);
                lv2.setAdapter(getAdapterForEvents());
            }
        });
    }

    private BaseAdapter getAdapterForCalendars() {
        return new BaseAdapter() {
            @Override
            public int getCount() {
                return allCalendars.size();
            }

            @Override
            public Object getItem(int position) {
                return allCalendars.get(position);
            }

            @Override
            public long getItemId(int position) {
                return allCalendars.get(position).ID;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                CalendarItem curr = allCalendars.get(position);
                CalendarItemView tag;
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.cal_item, null);
                    tag = new CalendarItemView();
                    convertView.setTag(tag);
                    tag.tvID = convertView.findViewById(R.id.tv1);
                    tag.tvDN = convertView.findViewById(R.id.tv2);
                    tag.tvVis = convertView.findViewById(R.id.tv3);
                    tag.tvCol = convertView.findViewById(R.id.tv4);
                } else
                    tag = (CalendarItemView) convertView.getTag();

                tag.tvID.setText(String.format("ID: %2d", curr.ID));
                tag.tvDN.setText(curr.DisplayName + " ");
                tag.tvVis.setText(curr.Visible ? "+" : "-");
                tag.tvCol.setTextColor(curr.Color);

                return convertView;
            }
        };
    }

    private BaseAdapter getAdapterForEvents() {
        return new BaseAdapter() {
            @Override
            public int getCount() {
                return eventsList.size();
            }

            @Override
            public Object getItem(int position) {
                return eventsList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return eventsList.get(position).ID;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                EventItem ei = eventsList.get(position);
                EventItemView tag;
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.event_item, null);
                    tag = new EventItemView();
                    convertView.setTag(tag);
                    tag.tvDayMonth = convertView.findViewById(R.id.tvDayMonth);
                    tag.tvWeekDay = convertView.findViewById(R.id.tvWeekDay);
                    tag.tvTitle = convertView.findViewById(R.id.tvTitle);
                    tag.tvMonthYear = convertView.findViewById(R.id.tvMonthYear);
                } else
                    tag = (EventItemView) convertView.getTag();

                tag.tvDayMonth.setText(sdfDayMonth.format(ei.DTSTART));
                String dw = sdfDayWeek.format(ei.DTSTART);
                tag.tvWeekDay.setText(dw.substring(0, 1).toUpperCase() + dw.substring(1, dw.length() - 1));
                tag.tvTitle.setText(ei.TITLE);
                tag.tvTitle.setBackgroundColor(ei.Color);
                tag.tvMonthYear.setText(sdfMonthYear.format(ei.DTSTART));

                int col = tag.tvMonthYear.getCurrentTextColor();
                if (position == 0 || !(sdfMonthYear.format(ei.DTSTART).equals(sdfMonthYear.format(eventsList.get(position - 1).DTSTART))))
                    tag.tvMonthYear.setTextColor((col & 0x00FFFFFF) | 0xA0000000);
                else
                    tag.tvMonthYear.setTextColor((col & 0x00FFFFFF) | 0x20000000);
                return convertView;
            }
        };
    }


    private ArrayList<EventItem> readEventsFromCalendar(long position) {
        ArrayList<EventItem> readedEvents = new ArrayList<>();
        CalendarItem currCal = allCalendars.get((int) position);
        ArrayList<String> lines = new ArrayList<>();
        Uri uri = CalendarContract.Events.CONTENT_URI;
        Cursor eventCursor = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            eventCursor = getContentResolver().query(
                    uri,
                    new String[]{
                            CalendarContract.Events._ID,
                            CalendarContract.Events.DTSTART,
                            CalendarContract.Events.DTEND,
                            CalendarContract.Events.ALL_DAY,
                            CalendarContract.Events.TITLE,
                            CalendarContract.Events.EVENT_LOCATION,
                            CalendarContract.Events.DISPLAY_COLOR
                    },
                    CalendarContract.Events.CALENDAR_ID + " = ?",
                    new String[]{String.valueOf(currCal.ID)},
                    CalendarContract.Events.DTSTART);
        }
        while (eventCursor.moveToNext()) {
            EventItem ei = new EventItem(eventCursor);
            readedEvents.add(ei);
        }

        return readedEvents;
    }



    class CalendarItem {
        public CalendarItem(Cursor cursor) {
            ID = cursor.getInt(0);
            DisplayName = cursor.getString(1);
            Visible = cursor.getInt(2) == 1;
            Color = cursor.getInt(3);
        }

        public int ID;
        public String DisplayName;
        public boolean Visible;
        public int Color;
    }

    class CalendarItemView {
        public TextView tvID;
        public TextView tvDN;
        public TextView tvVis;
        public TextView tvCol;
    }

    class EventItem {
        public int ID;
        public Date DTSTART;
        public Date DTEND;
        public boolean ALL_DAY;
        public String TITLE;
        public String EVENT_LOCATION;
        public int Color;

        public EventItem(Cursor cursor) {
            ID = cursor.getInt(cursor.getColumnIndex(CalendarContract.Events._ID));
            DTSTART = new Date(cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART)));
            DTEND = new Date(cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTEND)));
            ALL_DAY = "1" == cursor.getString(cursor.getColumnIndex(CalendarContract.Events.ALL_DAY));
            TITLE = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
            EVENT_LOCATION = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION));
            Color = cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.DISPLAY_COLOR));
        }
    }

    class EventItemView {
        public TextView tvDayMonth;
        public TextView tvWeekDay;
        public TextView tvTitle;
        public TextView tvMonthYear;
    }
}
