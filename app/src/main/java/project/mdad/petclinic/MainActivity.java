package project.mdad.petclinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//    public static String ipBaseAddress = "http://172.30.31.51/petclinic";
    public static String ipBaseAddress = "http://mdadproject.atspace.cc/petClinic";

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

        // view pet click event
        imgBtnViewPet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching view pet list Activity
                Intent i = new Intent(getApplicationContext(), ViewPetListActivity.class);
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
                // Launching view bills Activity
                Intent i = new Intent(getApplicationContext(), ViewBillsActivity.class);
                startActivity(i);
            }
        });
    }
}
