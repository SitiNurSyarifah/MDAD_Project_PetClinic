package project.mdad.petclinic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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
    Button btnchangedate;
    boolean check = false;

    String responseServer;

    JSONObject json = null;

    String pid;
    static InputStream is = null;

    private ProgressDialog pDialog;
    private static final String url_confirm_booking = MainActivity.ipBaseAddress + "/confirm_booking.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_CONFIRM_BOOKING = "confirm_booking";
    private static final String TAG_PID = "pid";
    private static final String TAG_SLOT1 = "slot1";
    private static final String TAG_SLOT2 = "slot";
    private static final String TAG_SLOT3 = "slot3";
    private static final String TAG_SLOT4 = "slot4";
    private static final String TAG_SLOT5 = "slot5";
    private static final String TAG_SLOT6 = "slot6";
    private static final String TAG_SLOT7 = "slot7";


    private static String SLOT1 = "";
    private static String SLOT2 = "";
    private static String SLOT3 = "";
    private static String SLOT4 = "";
    private static String SLOT5 = "";
    private static String SLOT6 = "";
    private static String SLOT7 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);
        Intent i = getIntent();

        // getting product id (pid) from intent
        pid = i.getStringExtra(TAG_PID);
        Log.i("url_confirm_booking", url_confirm_booking);

        slot1 = (Button) findViewById(R.id.slot1);
        slot2 = (Button) findViewById(R.id.slot2);
        slot3 = (Button) findViewById(R.id.slot3);
        slot4 = (Button) findViewById(R.id.slot4);
        slot5 = (Button) findViewById(R.id.slot5);
        slot6 = (Button) findViewById(R.id.slot6);
        slot7 = (Button) findViewById(R.id.slot7);

        slot1.setOnClickListener(new View.OnClickListener() {
            int count = 0;

            @Override
            public void onClick(View arg0) {
                //
                if (count == 0) {
                    slot1.isEnabled();
                    count++;
                }
                if (count >= 1)
                    slot1.setBackgroundColor(Color.DKGRAY);
                slot1.setEnabled(false);

                JSONObject dataJson = new JSONObject();
                try {
                    dataJson.put(TAG_SLOT1, slot1);


                } catch (JSONException e) {

                }
                postData(url_confirm_booking, dataJson, 1);
                Intent intent = new Intent(getApplicationContext(), SuccessBookedActivity.class);
                startActivity(intent);
            }
        });

        slot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SuccessBookedActivity.class);
                startActivity(intent);
            }
        });

        slot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SuccessBookedActivity.class);
                startActivity(intent);
            }
        });

        slot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SuccessBookedActivity.class);
                startActivity(intent);
            }
        });

        slot5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SuccessBookedActivity.class);
                startActivity(intent);
            }
        });

        slot6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SuccessBookedActivity.class);
                startActivity(intent);
            }
        });

        slot7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SuccessBookedActivity.class);
                startActivity(intent);
            }
        });


    }

    private void postData(String url_confirm_booking, JSONObject dataJson, final int option) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest json_obj_req = new JsonObjectRequest(
                Request.Method.POST, url_confirm_booking, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                switch (option) {
                    case 1:
                        checkResponseConfirm_Booking(response);
                        break;

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });
        requestQueue.add(json_obj_req);

    }

    public void checkResponseConfirm_Booking(JSONObject response) {
        Log.i("----Response", response + " ");
        try {
            if (response.getInt(TAG_SUCCESS) == 1) {

                finish();
                Intent i = new Intent(this, ConfirmBookingActivity.class);
                startActivity(i);

                // dismiss the dialog once product uupdated
                pDialog.dismiss();

            } else {
                // product with pid not found
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}
    


