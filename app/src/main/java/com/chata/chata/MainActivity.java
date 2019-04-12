package com.chata.chata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utility = Utility.getObject(this.getApplicationContext());
    }

    public void navigateOswietlenieParter(View view) {
        Intent intent = new Intent(this, OswietlenieParterActivity.class);
        startActivity(intent);
    }

    public void navigateOswietleniePoddasze(View view) {
        Intent intent = new Intent(this, OswietleniePoddaszeActivity.class);
        startActivity(intent);
    }

    public void navigateOswietlenieParterImage(View view) {
        Intent intent = new Intent(this, ParterImageActivity.class);
        startActivity(intent);
    }

    public void navigateRoletyParter(View view) {
        Intent intent = new Intent(this, RoletyParterActivity.class);
        startActivity(intent);
    }

    public void navigateRoletyPoddaszze(View view) {
        Intent intent = new Intent(this, RoletyPoddaszeActivity.class);
        startActivity(intent);
    }

    public void navigateRoletyParterUp(View view) {
        String host = getString(R.string.poddasze_host);
        String url = "http://" + host + "/impulsRolety";
        utility.Post(url, "allRoletyUp");
        Toast.makeText(view.getContext(), url, Toast.LENGTH_SHORT).show();
    }

    public void navigateRoletyParterDown(View view) {
        String host = getString(R.string.poddasze_host);
        String url = "http://" + host + "/impulsRolety";
        utility.Post(url, "allRoletyDown");
        Toast.makeText(view.getContext(), url, Toast.LENGTH_SHORT).show();
    }

    public void navigateRoletyPoddaszeUp(View view) {
        String host = getString(R.string.parter_host);
        String url = "http://" + host + "/impulsRolety";
        utility.Post(url, "allRoletyUp");
        Toast.makeText(view.getContext(), url, Toast.LENGTH_SHORT).show();
    }

    public void navigateRoletyPoddaszeDown(View view) {
        String host = getString(R.string.parter_host);
        String url = "http://" + host + "/impulsRolety";
        utility.Post(url, "allRoletyDown");
        Toast.makeText(view.getContext(), url, Toast.LENGTH_SHORT).show();
    }

    public void navigateOswietlenieParterOff(View view) {
        String host = getString(R.string.parter_host);
        String url = "http://" + host + "/impulsOswietlenie";
        utility.Post(url, "allOff=0");
        Toast.makeText(view.getContext(), url, Toast.LENGTH_SHORT).show();
    }

    public void navigateOswietleniePoddaszeOff(View view) {
        String host = getString(R.string.poddasze_host);
        String url = "http://" + host + "/impulsOswietlenie";
        utility.Post(url, "allOff=0");
        Toast.makeText(view.getContext(), url, Toast.LENGTH_SHORT).show();
    }
}
