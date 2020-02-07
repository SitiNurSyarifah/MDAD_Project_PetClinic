package project.mdad.petclinic;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessBookedActivity extends AppCompatActivity {
    TextView txtDate;
    TextView txtTime;

    private static final String url_display = MainActivity.ipBaseAddress + "/confirm_booking.php";
    private static final String TAG_DATE = "date";
    private static final String TAG_TIME = "time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_booked);
        setContentView(R.layout.activity_book_appointment);
        Log.i("url_display", url_display);

        //txtDate = (TextView) findViewById(R.id.booking_date);
       // txtTime = (TextView) findViewById(R.id.time_slots);

        //String date;
        //String time;
    }
}

