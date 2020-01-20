package project.mdad.petclinic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class BookAppointmentActivity extends AppCompatActivity {
    Button btnchangedate;
    Button btnConfirm;
    CalendarView calendarView;
    TextView txtDate;
    JSONObject json = null;
    String pid;

    static InputStream is = null;
    // Progress Dialog
    private ProgressDialog pDialog;
    private static final String url_booking = MainActivity.ipBaseAddress + "/booking.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_BOOKING = "booking";
    private static final String TAG_PID = "pid";
    private static final String TAG_BOOKING_DATE = "booking_date";

    private static String booking_date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        Log.i("url_booking", url_booking);
        // save button

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        txtDate = (TextView) findViewById(R.id.txtDate);
        btnchangedate = (Button) findViewById(R.id.btnchangedate);
        Intent i = getIntent();
        pid = i.getStringExtra(TAG_PID);
        JSONObject dataJson = new JSONObject();
        try {
            dataJson.put("pid", pid);
            //     dataJson.put("password", "def");

        } catch (JSONException e) {

        }

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                txtDate.setText(date);

            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ConfirmBookingActivity.class);
                startActivity(intent);
            }
        });

    }
}
