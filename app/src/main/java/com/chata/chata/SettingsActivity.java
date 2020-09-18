package com.chata.chata;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SettingsActivity extends BasePageActivity {

    ArduinoResponseParser responseParser = new ArduinoResponseParser();

    class UIControls {
        private final int idPing;
        private final int idTimeText;
        private final int idLayout;
        private final int idHolidayMode;
        private final int idTwilightMode;
        private final int idMorningMode;
        private final int idMorningText;

        UIControls(int idPing, int idTimeText, int idLayout, int idHolidayMode, int idTwilightMode, int idMorningMode, int idMorningText) {
            this.idPing = idPing;
            this.idTimeText = idTimeText;
            this.idLayout = idLayout;
            this.idHolidayMode = idHolidayMode;
            this.idTwilightMode = idTwilightMode;
            this.idMorningMode = idMorningMode;
            this.idMorningText = idMorningText;
        }

        public int getIdPing() {
            return idPing;
        }

        public int getIdTimeText() {
            return idTimeText;
        }

        public int getIdLayout() {
            return idLayout;
        }

        public int getIdHolidayMode() {
            return idHolidayMode;
        }

        public int getIdTwilightMode() {
            return idTwilightMode;
        }

        public int getIdMorningMode() {
            return idMorningMode;
        }

        public int getIdMorningText() {
            return idMorningText;
        }
    }

    HashMap<Integer, UIControls> uiControlsMap =  new HashMap<Integer, UIControls>() {{
        put(R.string.basement_host, new UIControls(R.id.arduino1_ping, R.id.arduino1_time, R.id.arduino1_data, R.id.arduino1_holiday_mode, R.id.arduino1_twilight_mode, R.id.arduino1_morning_mode, R.id.arduino1_morning_time));
        put(R.string.attic_host, new UIControls(R.id.arduino2_ping, R.id.arduino2_time, R.id.arduino2_data, R.id.arduino2_holiday_mode, R.id.arduino2_twilight_mode, R.id.arduino2_morning_mode, R.id.arduino2_morning_time));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView textArduino1 = findViewById(R.id.arduino1_caption);
        textArduino1.setText("Arduino 1 - " + getString(R.string.basement_host));
        textArduino1.setPaintFlags(textArduino1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        TextView textArduino2 = findViewById(R.id.arduino2_caption);
        textArduino2.setText("Arduino 2 - " + getString(R.string.attic_host));

        retrieveData(R.string.basement_host);
        retrieveData(R.string.attic_host);
    }

    void displayError(String response, int host) {
        UIControls controls = uiControlsMap.get(host);

        LinearLayout layout = findViewById(controls.getIdLayout());
        layout.setVisibility(View.INVISIBLE);

        TextView pingText = findViewById(controls.getIdPing());
        pingText.setText(response);
    }

    private void UpdateData(Settings settings, long duration, Integer host) {
        UIControls controls = uiControlsMap.get(host);

        LinearLayout layout = findViewById(controls.getIdLayout());
        layout.setVisibility(View.VISIBLE);

        TextView pingText = findViewById(controls.getIdPing());
        pingText.setText("Ping: " + duration + " ms");

        TextView timeView = findViewById(controls.getIdTimeText());
        timeView.setText(settings.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        setSwitch(controls.getIdHolidayMode(), settings.isHolidayMode());
        setSwitch(controls.getIdTwilightMode(), settings.isTwilightMode());
        setSwitch(controls.getIdMorningMode(), settings.isMorningMode());

        if (settings.isMorningMode()) {
            TextView morningText = findViewById(controls.getIdMorningText());
            setMorningText(morningText, settings.getMorningTime(), WeekDaysHelper.weekDaysFromMask(settings.getMorningDays()));
            morningText.setVisibility(View.VISIBLE);
        }
    }

    private void setMorningText(TextView morningText, LocalTime morningTime, ArrayList<DayOfWeek> morningDays) {
        String time = morningTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        time += ": ";

        for (DayOfWeek weekDay : morningDays) {
            switch (weekDay) {
                case MONDAY:
                    time += "Pon, ";
                    break;
                case TUESDAY:
                    time += "Wt, ";
                    break;
                case WEDNESDAY:
                    time += "Śr, ";
                    break;
                case THURSDAY:
                    time += "Czw, ";
                    break;
                case FRIDAY:
                    time += "Pt, ";
                    break;
                case SATURDAY:
                    time += "Sob, ";
                    break;
                case SUNDAY:
                    time += "Nd, ";
                    break;
            }
        }

        time = time.substring(0, time.length() - 2);
        morningText.setText(time);
    }

    private void setSwitch(int switchId, boolean value) {
        Switch switchView = findViewById(switchId);
        switchView.setChecked(value);
    }

    private void retrieveData(Integer host) {
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, Long> asyncTask = new AsyncTask<Void, Void, Long>() {
            String response;

            @Override
            protected Long doInBackground(Void... voids) {
                long start = System.currentTimeMillis();

                response = utility.Get("http://" + getString(host) + "/getAllSettings");

                long finish = System.currentTimeMillis();
                return finish - start;
            }

            @Override
            protected void onPostExecute(Long duration) {
                if (response.startsWith("ERROR")) {
                    displayError(response, host);
                }
                else {
                    Settings settings = responseParser.parseAllSettingsResponse(response);
                    UpdateData(settings, duration, host);
                }
            }
        };

        try {
            asyncTask.execute().get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e){
            displayError(e.getMessage(), host);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_settings, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.submit_item:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        retrieveData(R.string.basement_host);
        retrieveData(R.string.attic_host);
    }

    public void navigateSwitchTwilightMode(View view) {
        String host = view.getId() == R.id.arduino1_twilight_mode ? getString(R.string.basement_host) : getString(R.string.attic_host);
        Switch switchView = (Switch)view;
        if (switchView.isChecked()) {
            if (utility.Post("http://" + host + "/enableTwilightMode", "") != 200) {
                switchView.setChecked(false);
                displayFailureMessage();
            }
        } else {
            if (utility.Post("http://" + host + "/disableTwilightMode", "") != 200) {
                switchView.setChecked(true);
                displayFailureMessage();
            }
        }
    }

    public void navigateSwitchHolidayMode(View view) {
        String host = view.getId() == R.id.arduino1_holiday_mode ? getString(R.string.basement_host) : getString(R.string.attic_host);
        Switch switchView = (Switch)view;
        if (switchView.isChecked()) {
            if (utility.Post("http://" + host + "/enableHolidayMode", "") != 200) {
                switchView.setChecked(false);
                displayFailureMessage();
            }
        } else {
            if (utility.Post("http://" + host + "/disableHolidayMode", "") != 200) {
                switchView.setChecked(true);
                displayFailureMessage();
            }
        }
    }

    public void navigateUpdateTime(View view) {
        int host = view.getId() == R.id.arduino1_buttonUpdateTime ? R.string.basement_host : R.string.attic_host;

        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        String params = "time=" + now.format(DateTimeFormatter.ofPattern("HH:mm:ss_dd-MM-yyyy"));
        String url = "http://" + getString(host) + "/setTime";
        if (utility.Post(url, params) == 200) {
            TextView timeView = findViewById(uiControlsMap.get(host).getIdTimeText());
            timeView.setText(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        else
            displayFailureMessage();
    }

    public void navigateTimePopup(View view) {
        Switch morningMode = (Switch)view;
        TextView morningText = view.getId() == R.id.arduino1_morning_mode ? findViewById(R.id.arduino1_morning_time) : findViewById(R.id.arduino2_morning_time);

        int host = view.getId() == R.id.arduino1_morning_mode ? R.string.basement_host : R.string.attic_host;
        String url = "http://" + getString(host) + "/setMorningMode";

        if (!morningMode.isChecked()) {
            if (utility.Post(url, "false") != 200) {
                morningMode.setChecked(false);
                displayFailureMessage();
            }
            else {
                morningText.setVisibility(View.GONE);
            }
            return;
        }

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.time_popup, null);

        TimePopupWindow timePopupWindow = new TimePopupWindow(popupView);
        timePopupWindow.init();

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, false);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button okButton = popupView.findViewById(R.id.time_popup_ok);
        okButton.setOnClickListener((View v) -> {
            String params = String.format("true;days=%3d;time=%s", WeekDaysHelper.getDaysMask(timePopupWindow.getSelectedDays()),
                    timePopupWindow.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            if (utility.Post(url, params) != 200) {
                morningMode.setChecked(false);
                displayFailureMessage();
            } else {
                setMorningText(morningText, timePopupWindow.getTime(), timePopupWindow.getSelectedDays());
                morningText.setVisibility(View.VISIBLE);
            }

            popupWindow.dismiss();
        });
    }

    private void displayFailureMessage() {
        Toast.makeText(this.getApplicationContext(), "Nie udało się nawiązać połączenia", Toast.LENGTH_LONG);
    }
}
