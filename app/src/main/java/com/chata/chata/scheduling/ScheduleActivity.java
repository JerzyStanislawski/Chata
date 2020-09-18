package com.chata.chata.scheduling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.chata.chata.BasePageActivity;
import com.chata.chata.R;

import java.util.ArrayList;

public class ScheduleActivity extends BasePageActivity {

    ArrayList<ScheduleEntity> entities;
    ScheduleAdapter adapter;
    private int currentId;

    public static final int REQUEST_CODE_ADD_ENTITY = 0;
    public static final int REQUEST_CODE_EDIT_ENTITY = 1;

    public static final int RESULT_CODE_OK = 0;
    public static final int RESULT_CODE_BACK = 1;
    public static final int RESULT_CODE_REMOVE = 2;

    ScheduleCommunication transferingHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        transferingHandler = new ScheduleCommunication(this.getApplicationContext());
        retrieveData();

        ListView list = findViewById(R.id.scheduleList);
        list.setAdapter(adapter);

        list.setClickable(true);
        list.setOnItemClickListener((adapterView, childView, position, id) -> {
            ScheduleEntity entity = (ScheduleEntity) adapterView.getItemAtPosition(position);

            Intent intent = new Intent(ScheduleActivity.this, ScheduleEntityActivity.class);
            ScheduleEntityHelpers.fillIntent(entity, intent);
            startActivityForResult(intent, REQUEST_CODE_EDIT_ENTITY);
        });
    }

    private void retrieveData() {
        entities = transferingHandler.retrieveData();
        adapter = new ScheduleAdapter(this, R.layout.schedule_row, entities);

        currentId = entities.size();
    }

    public void navigateOK(View view) {
        if (!transferingHandler.sendData(entities))
            Toast.makeText(this.getApplicationContext(), "Sending schedule data failed.", Toast.LENGTH_SHORT);
        else
            finish();
    }

    public void navigateAdd(View view) {
        Intent intent = new Intent(ScheduleActivity.this, ScheduleEntityActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_ENTITY);
    }

    public void navigateWyczysc(View view) {
        entities.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE_BACK)
            return;

        if (resultCode == RESULT_CODE_REMOVE) {
            int id = data.getIntExtra("id", 0);

            ScheduleEntity entity = entities.stream().filter(x -> x.getId() == id).findFirst().get();
            entities.remove(entity);

            adapter.notifyDataSetChanged();
            return;
        }

        if (resultCode == RESULT_CODE_OK) {
            if (requestCode == REQUEST_CODE_ADD_ENTITY) {
                data.putExtra("id", currentId++);
            }

            ScheduleEntity entity = ScheduleEntityHelpers.createFromIntent(data);

            if (requestCode == REQUEST_CODE_ADD_ENTITY) {
                entities.add(entity);
            } else if (requestCode == REQUEST_CODE_EDIT_ENTITY) {
                ScheduleEntity entityToReplace = entities.stream().filter(x -> x.getId() == entity.getId()).findFirst().get();
                int index = entities.indexOf(entityToReplace);
                entities.set(index, entity);
            }

            adapter.notifyDataSetChanged();
        }
    }

}
