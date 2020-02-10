package project.mdad.petclinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //public static String ipBaseAddress = "http://172.30.29.21/petclinic";
    public static String ipBaseAddress = "http://mdadproject.atspace.cc/petClinic";
    private static String url_bill_history = "http://mdadproject.atspace.cc/petClinic/bill_history.php";

    private static final String TAG_PID = "pid";
    private static final String TAG_USERNAME = "username";

    String pid,username;


    ImageButton imgBtnViewPet;
    ImageButton imgBtnAddPet;
    ImageButton imgBtnMedicalRecords;
    ImageButton imgBtnBookAppt;
    ImageButton imgBtnViewBills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Buttons
        imgBtnViewPet = (ImageButton) findViewById(R.id.imgBtnViewPet);
        imgBtnAddPet = (ImageButton) findViewById(R.id.imgBtnAddPet);
        imgBtnMedicalRecords = (ImageButton) findViewById(R.id.imgBtnMedicalRecords);
        imgBtnBookAppt = (ImageButton) findViewById(R.id.imgBtnBookAppt);
        imgBtnViewBills = (ImageButton) findViewById(R.id.imgBtnBills);

        // getting product details from intent
        Intent i = getIntent();
        // getting product id (pid) from intent
        username = i.getStringExtra(TAG_USERNAME);
        Toast.makeText(this, "Record " + username, Toast.LENGTH_SHORT).show();
        // Getting complete product details in background thread
        JSONObject dataJson = new JSONObject();
        try {

            dataJson.put("username", username);
            //     dataJson.put("password", "def");

        } catch (JSONException e) {

        }

        // view pet click event
        imgBtnViewPet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching view pet list Activity
                Intent i = new Intent(getApplicationContext(), ViewPetListActivity.class);
                i.putExtra(TAG_USERNAME,username);
                startActivity(i);
            }
        });

        // add pet click event
        imgBtnAddPet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All add pet Activity
                Intent i = new Intent(getApplicationContext(), AddPetActivity.class);
                startActivity(i);
            }
        });

        // view medical records click event
        imgBtnMedicalRecords.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching medical records Activity
                Intent i = new Intent(getApplicationContext(), MedicalListActivity.class);
                startActivity(i);
            }
        });

        // book appt click event
        imgBtnBookAppt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching book appt Activity
                Intent i = new Intent(getApplicationContext(), BookAppointmentActivity.class);
                startActivity(i);
            }
        });

        // view bills click event
        imgBtnViewBills.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                 //Launching view bills Activity
                Intent i = new Intent(getApplicationContext(), BillsHistoryActivity.class);
                i.putExtra(TAG_USERNAME,username);

                startActivity(i);
            }
        });
    }


}
