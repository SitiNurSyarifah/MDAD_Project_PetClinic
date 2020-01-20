package project.mdad.petclinic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class ConfirmBookingActivity extends AppCompatActivity {
    Button slot1;
    Button slot2;
    Button slot3;
    Button slot4;
    Button slot5;
    Button slot6;
    Button slot7;

    String responseServer;

    JSONObject json = null;

    String pid;
    static InputStream is = null;

    private ProgressDialog pDialog;
    private static final String url_booking = MainActivity.ipBaseAddress + "/booking.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_BOOKED = "booked";
    private static final String TAG_PID = "pid";
    private static final String TAG_SLOT1 = "slot1";
    private static final String TAG_SLOT2 = "slot";
    private static final String TAG_SLOT3 = "slot3";
    private static final String TAG_SLOT4 = "slot4";
    private static final String TAG_SLOT5 = "slot5";
    private static final String TAG_SLOT6 = "slot6";
    private static final String TAG_SLOT7 = "slot7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);
        slot1 = (Button) findViewById(R.id.slot1);
        slot1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    slot1.setBackgroundColor(Color.RED);
                } else if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    slot1.setBackgroundColor(Color.BLUE);
                }
                return false;
            }

        });
        slot2 = (Button) findViewById(R.id.slot2);
        slot3 = (Button) findViewById(R.id.slot3);
        slot4 = (Button) findViewById(R.id.slot4);
        slot5 = (Button) findViewById(R.id.slot5);
        slot6 = (Button) findViewById(R.id.slot6);
        slot7 = (Button) findViewById(R.id.slot7);
        Intent i = getIntent();
        pid = i.getStringExtra(TAG_PID);

        JSONObject dataJson = new JSONObject();
        try {
            dataJson.put("pid", pid);
            //     dataJson.put("password", "def");

        } catch (JSONException e) {

        }
        slot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SuccesBookedActivity.class);
                startActivity(intent);
            }
        });
        slot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SuccesBookedActivity.class);
                startActivity(intent);
            }
        });

        slot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SuccesBookedActivity.class);
                startActivity(intent);
            }
        });

        slot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SuccesBookedActivity.class);
                startActivity(intent);
            }
        });

        slot5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SuccesBookedActivity.class);
                startActivity(intent);
            }
        });

        slot6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SuccesBookedActivity.class);
                startActivity(intent);
            }
        });

        slot7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SuccesBookedActivity.class);
                startActivity(intent);
            }
        });

    }
}
