package project.mdad.petclinic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessBookedActivity extends AppCompatActivity {
    TextView txtDate;
    TextView txtTime;
    Button btnHome;
    private static final String url_display = MainActivity.ipBaseAddress + "/confirm_booking.php";
    private static final String TAG_DATE = "date";
    private static final String TAG_TIME = "time";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_HOME = "home";
    String date;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_booked);
        Log.i("url_display", url_display);
        Intent i = getIntent();

        txtDate = (TextView) findViewById(R.id.booking_date);
        txtTime = (TextView) findViewById(R.id.time_slots);
        btnHome = (Button) findViewById(R.id.btnHome);
        Bundle bundle = getIntent().getExtras();
        String data = bundle.get("data").toString();
        String date = bundle.get("date").toString();
        txtTime.setText(data);
        txtDate.setText(date);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

        });
    }

}

