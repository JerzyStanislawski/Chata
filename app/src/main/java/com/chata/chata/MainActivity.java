package com.chata.chata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends BasePageActivity {

    public Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utility = Utility.getObject();
    }

    public void navigateOswietlenieParter(View view) {
        Intent intent = new Intent(this, LightsBasementActivity.class);
        startActivity(intent);
    }

    public void navigateOswietleniePoddasze(View view) {
        Intent intent = new Intent(this, LightsAtticActivity.class);
        startActivity(intent);
    }

    public void navigateRoletyParter(View view) {
        Intent intent = new Intent(this, BlindsBasementActivity.class);
        startActivity(intent);
    }

    public void navigateRoletyPoddaszze(View view) {
        Intent intent = new Intent(this, BlindsAtticActivity.class);
        startActivity(intent);
    }

    public void navigateRoletyParterUp(View view) {
        String host = getString(R.string.attic_host);
        String url = "http://" + host + "/impulsRolety";
        int responseCode = utility.Post(url, "allRoletyUp");
        NotifyOnFailure(responseCode);
    }

    public void navigateRoletyParterDown(View view) {
        String host = getString(R.string.attic_host);
        String url = "http://" + host + "/impulsRolety";
        int responseCode = utility.Post(url, "allRoletyDown");
        NotifyOnFailure(responseCode);
    }

    public void navigateRoletyPoddaszeUp(View view) {
        String host = getString(R.string.basement_host);
        String url = "http://" + host + "/impulsRolety";
        int responseCode = utility.Post(url, "allRoletyUp");
        NotifyOnFailure(responseCode);
    }

    public void navigateRoletyPoddaszeDown(View view) {
        String host = getString(R.string.basement_host);
        String url = "http://" + host + "/impulsRolety";
        int responseCode = utility.Post(url, "allRoletyDown");
        NotifyOnFailure(responseCode);
    }

    public void navigateOswietlenieParterOff(View view) {
        String host = getString(R.string.basement_host);
        String url = "http://" + host + "/impulsOswietlenie";
        int responseCode = utility.Post(url, "allOff=0");
        NotifyOnFailure(responseCode);
    }

    public void navigateOswietleniePoddaszeOff(View view) {
        String host = getString(R.string.attic_host);
        String url = "http://" + host + "/impulsOswietlenie";
        int responseCode = utility.Post(url, "allOff=0");
        NotifyOnFailure(responseCode);
    }

    private void NotifyOnFailure(int responseCode) {
        if (responseCode != 200) {
            Toast.makeText(this.getApplicationContext(), "Odpowiedź z rodzielni: " + Integer.toString(responseCode), Toast.LENGTH_SHORT).show();
        }
    }
}
