package com.chata.chata.scheduling;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.chata.chata.Area;
import com.chata.chata.BasePageActivity;
import com.chata.chata.Blind;
import com.chata.chata.Data;
import com.chata.chata.Light;
import com.chata.chata.R;
import com.chata.chata.WeekDaysHelper;
import com.chata.chata.WeekDaysView;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class ScheduleEntityActivity extends BasePageActivity {

    ArrayAdapter<Object> spinnerAdapter;
    Object[] spinnerArray;
    ScheduleEntity originalEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_entity);

        TimePicker timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        ((RadioGroup) findViewById(R.id.toggleGroupArea)).setOnCheckedChangeListener(ToggleListener);
        ((RadioGroup) findViewById(R.id.toggleGroupType)).setOnCheckedChangeListener(ToggleListener);
        ((RadioGroup) findViewById(R.id.toggleGroupBlinds)).setOnCheckedChangeListener(ToggleListener);

        Intent intent = getIntent();
        if (intent.getExtras() != null && intent.getExtras().size() > 0)
            LoadViewFromIntent(intent);
        else
            setSpinnerData();
    }

    private void LoadViewFromIntent(Intent data) {
        originalEntity = ScheduleEntityHelpers.createFromIntent(data);

        if (originalEntity.getType() == ScheduleType.LIGHTS) {
            this.onToggleType(findViewById(R.id.btn_lights));
        } else {
            this.onToggleType(findViewById(R.id.btn_blinds));
        }

        if (originalEntity.getArea() == Area.GROUNDFLOOR) {
            this.onToggleArea(findViewById(R.id.btn_ground_floor));
        } else {
            this.onToggleArea(findViewById(R.id.btn_attic));
        }

        int position;
        if (originalEntity.getType() == ScheduleType.LIGHTS) {
            Light lightToSelect = Arrays.stream(spinnerArray)
                    .map(obj -> (Light) obj)
                    .filter(light -> light.getName().equals(originalEntity.getRoom()))
                    .findFirst().get();
            position = spinnerAdapter.getPosition(lightToSelect);
        } else {
            Blind blindToSelect = Arrays.stream(spinnerArray)
                    .map(obj -> (Blind) obj)
                    .filter(blind -> stripBlindName(blind.getName()).equals(stripBlindName(originalEntity.getRoom())))
                    .findFirst().get();
            position = spinnerAdapter.getPosition(blindToSelect);
        }
        Spinner roomSpinner = findViewById(R.id.room_spinner);
        roomSpinner.setSelection(position);

        TimePicker timePicker = findViewById(R.id.timePicker);
        timePicker.setHour(originalEntity.getTime().getHour());
        timePicker.setMinute(originalEntity.getTime().getMinute());

        if (originalEntity.getType() == ScheduleType.LIGHTS) {
            ((Switch)findViewById(R.id.lights_switch)).setChecked(originalEntity.getOnOrUp());
        } else {
            ((ToggleButton) findViewById(R.id.blinds_up)).setChecked(originalEntity.getOnOrUp());
            ((ToggleButton) findViewById(R.id.blinds_down)).setChecked(!originalEntity.getOnOrUp());
        }

        WeekDaysView weekDaysView = new WeekDaysView(findViewById(R.id.schedule_week_days));
        weekDaysView.setDays(WeekDaysHelper.weekDaysFromMask(originalEntity.getDaysMask()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_schedule_entity, menu);

        Intent intent = getIntent();
        if (intent.getExtras() == null || intent.getExtras().size() == 0) {
            MenuItem item = menu.findItem(R.id.delete_item);
            item.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.submit_item:
                Intent intent = createIntent();
                setResult(ScheduleActivity.RESULT_CODE_OK, intent);
                finish();
                return true;
            case R.id.delete_item:
                Intent removeIntent = new Intent();
                removeIntent.putExtra("id", originalEntity.getId());
                setResult(ScheduleActivity.RESULT_CODE_REMOVE, removeIntent);
                finish();
                return true;
            case android.R.id.home:
                setResult(ScheduleActivity.RESULT_CODE_BACK);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String stripBlindName(String blindName) {
        return blindName.substring(0, blindName.lastIndexOf('_'));
    }

    private Intent createIntent() {
        ToggleButton lightsButton = findViewById(R.id.btn_lights);
        ScheduleType type = lightsButton.isChecked() ? ScheduleType.LIGHTS : ScheduleType.BLINDS;

        ToggleButton groundFloorButton = findViewById(R.id.btn_ground_floor);
        Area area = groundFloorButton.isChecked() ? Area.GROUNDFLOOR : Area.ATTIC;

        TimePicker timePicker = findViewById(R.id.timePicker);
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        boolean onOrUp = type == ScheduleType.LIGHTS
                ? ((Switch)findViewById(R.id.lights_switch)).isChecked()
                : ((ToggleButton)findViewById(R.id.blinds_up)).isChecked();

        String room = "";
        String roomNiceName = "";
        Spinner roomSpinner = findViewById(R.id.room_spinner);
        if (type == ScheduleType.LIGHTS) {
            Light light = (Light) roomSpinner.getSelectedItem();
            room = light.getName();
            roomNiceName = light.getNiceName();
        } else if (type == ScheduleType.BLINDS) {
            Blind blind = (Blind)roomSpinner.getSelectedItem();
            room = stripBlindName(blind.getName());
            room += onOrUp ? "_up" : "_down";
            roomNiceName = blind.getNiceName();
        }

        WeekDaysView weekDaysView = new WeekDaysView(findViewById(R.id.schedule_week_days));
        int daysMask = WeekDaysHelper.getDaysMask(weekDaysView.getSelectedDays());

        ScheduleEntity entity = new ScheduleEntity(originalEntity != null ? originalEntity.getId() : null, type, room, roomNiceName, LocalTime.of(hour, minute), daysMask, onOrUp, area);
        Intent intent = new Intent();
        ScheduleEntityHelpers.fillIntent(entity, intent);

        return intent;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private void setSpinnerData() {
        ToggleButton lightsButton = findViewById(R.id.btn_lights);
        ScheduleType type = lightsButton.isChecked() ? ScheduleType.LIGHTS : ScheduleType.BLINDS;

        ToggleButton groundFloorButton = findViewById(R.id.btn_ground_floor);
        Area area = groundFloorButton.isChecked() ? Area.GROUNDFLOOR : Area.ATTIC;

        if (type == ScheduleType.LIGHTS && area == Area.GROUNDFLOOR) {
            spinnerArray = Data.getBasementLightsData().values().stream().sorted(Comparator.comparing(Light::getNiceName)).toArray();
        } else if (type == ScheduleType.LIGHTS && area == Area.ATTIC) {
            spinnerArray = Data.getAtticLightsData().values().stream().sorted(Comparator.comparing(Light::getNiceName)).toArray();
        } else if (type == ScheduleType.BLINDS && area == Area.GROUNDFLOOR) {
            spinnerArray = Data.getBasementBlindsData().values().stream().sorted(Comparator.comparing(Blind::getNiceName)).filter(distinctByKey(Blind::getNiceName)).toArray();
        } else if (type == ScheduleType.BLINDS && area == Area.ATTIC) {
            spinnerArray = Data.getAtticBlindsData().values().stream().sorted(Comparator.comparing(Blind::getNiceName)).filter(distinctByKey(Blind::getNiceName)).toArray();
        }

        Spinner spinner = findViewById(R.id.room_spinner);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    static final RadioGroup.OnCheckedChangeListener ToggleListener = (radioGroup, i) -> {
        for (int j = 0; j < radioGroup.getChildCount(); j++) {
            final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
            view.setChecked(view.getId() == i);
        }
    };

    private void HandleToggling(View view) {
        ((RadioGroup)view.getParent()).clearCheck();
        ((RadioGroup)view.getParent()).check(view.getId());
    }

    public void onToggleType(View view) {
        HandleToggling(view);

        LinearLayout lightsOptions = findViewById(R.id.ligts_options);
        LinearLayout blindsOptions = findViewById(R.id.blinds_options);
        if (view.getId() == R.id.btn_lights) {
            lightsOptions.setVisibility(View.VISIBLE);
            blindsOptions.setVisibility(View.GONE);
        } else if (view.getId() == R.id.btn_blinds) {
            lightsOptions.setVisibility(View.GONE);
            blindsOptions.setVisibility(View.VISIBLE);
        }

        setSpinnerData();
    }

    public void onToggleArea(View view) {
        HandleToggling(view);
        setSpinnerData();
    }

    public void onToggleBlinds(View view) {
        HandleToggling(view);
    }
}
