package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Main extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("/spinker1/soilmoisture");
    DatabaseReference mygrass = database.getReference("/grass/hight");
    DatabaseReference myseleect = database.getReference("/select");
    DatabaseReference mynamebord = database.getReference("/spinker1/name");
    DatabaseReference myonoff = database.getReference("/spinker1/status");

    private BottomNavigationView btnselectNavBottom;
    TextView textViewgrass, grass, namebord;
    Switch switch1;
    AppCompatImageView btnshowtime, btngeat;
    EditText editText;
    Button imgon,imgoff;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textViewgrass = findViewById(R.id.grass);
        grass = findViewById(R.id.grass2);
        switch1 = findViewById(R.id.switch1);
        btnshowtime = findViewById(R.id.buttonshowtime);
        namebord = findViewById(R.id.namebord);
        btngeat = findViewById(R.id.btngeat);
        editText = findViewById(R.id.nameedit);
        imgon = findViewById(R.id.on);
        imgoff = findViewById(R.id.off);



        // เช็คการเลือก ออโค้ หรือ แมนนวล
        myseleect.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String checkSeleter =  dataSnapshot.getValue(String.class);
                final TextView textViewshowtime = (TextView) findViewById(R.id.textsowtime);
                final TextView textViewshowbtnoffon = (TextView) findViewById(R.id.textViewshowbtnoffon);
                final TextView settime = (TextView) findViewById(R.id.settime);

                if (checkSeleter.equals("Auto")){
                    switch1.setChecked(true);
                    btnshowtime.setVisibility(View.GONE); // ปุมหาย
                    textViewshowtime.setVisibility(View.GONE);
                    imgon.setVisibility(View.GONE);
                    imgoff.setVisibility(View.GONE);
                    textViewshowbtnoffon.setVisibility(View.GONE);
                    settime.setVisibility(View.GONE);
                    // ปุ่ม auto
                    switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            if (isChecked) {
                                myseleect.setValue("Auto");
                                btnshowtime.setVisibility(View.GONE); // ปุมหาย
                                textViewshowtime.setVisibility(View.GONE);
                                imgon.setVisibility(View.GONE);
                                imgoff.setVisibility(View.GONE);
                                textViewshowbtnoffon.setVisibility(View.GONE);
                                settime.setVisibility(View.GONE);
                            } else {
                                myseleect.setValue("Manual");
                                btnshowtime.setVisibility(View.VISIBLE); // แสดงปุ่ม
                                textViewshowtime.setVisibility(View.VISIBLE);
                                imgon.setVisibility(View.VISIBLE);
                                imgoff.setVisibility(View.VISIBLE);
                                textViewshowbtnoffon.setVisibility(View.VISIBLE);
                                settime.setVisibility(View.VISIBLE);
                            }

                        }
                    });
                }else{
                    switch1.setChecked(false);
                    btnshowtime.setVisibility(View.VISIBLE); // แสดงปุ่ม
                    textViewshowtime.setVisibility(View.VISIBLE);
                    imgon.setVisibility(View.VISIBLE);
                    imgoff.setVisibility(View.VISIBLE);
                    textViewshowbtnoffon.setVisibility(View.VISIBLE);
                    settime.setVisibility(View.VISIBLE);

                    switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            if (isChecked) {
                                myseleect.setValue("Auto");
                                btnshowtime.setVisibility(View.GONE); // ปุมหาย
                                textViewshowtime.setVisibility(View.GONE);
                                imgon.setVisibility(View.GONE);
                                imgoff.setVisibility(View.GONE);
                                textViewshowbtnoffon.setVisibility(View.GONE);
                                settime.setVisibility(View.GONE);
                            } else {
                                myseleect.setValue("Manual");
                                btnshowtime.setVisibility(View.VISIBLE); // แสดงปุ่ม
                                textViewshowtime.setVisibility(View.VISIBLE);
                                imgon.setVisibility(View.VISIBLE);
                                imgoff.setVisibility(View.VISIBLE);
                                textViewshowbtnoffon.setVisibility(View.VISIBLE);
                                settime.setVisibility(View.VISIBLE);
                            }

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Main.this,"Error !! is "+databaseError,Toast.LENGTH_LONG).show();
            }
        });



        imgon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myonoff.setValue(1);
            }
        });

        imgoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myonoff.setValue(0);
            }
        });



        btnshowtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        //bottom nav
        buttonselect ();
        // edit name bord
        btngeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successDialog();
            }
        });

        mynamebord.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                namebord.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //-------------------  ดึงค่าจากไฟลเบส  ---------------------
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textViewgrass.setText(dataSnapshot.getValue(Integer.class).toString() + " %"); // ดึงค่าจากไฟลเบส
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Main.this, "Error !!" + databaseError, Toast.LENGTH_LONG).show();
            }
        });
        //-------------------  จบดึงค่าจากไฟลเบส  ---------------------


        //-------------------  จบดึงค่าจากไฟลเบส  ---------------------
        mygrass.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                double result = 30 - dataSnapshot.getValue(Double.class);

                String str =  String.format("%.02f", result);
                grass.setText(str + " cm");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Main.this, "Error !!" + databaseError, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myhour = database.getReference("/time/hour");
        DatabaseReference myminute = database.getReference("/time/minute");
        TextView textViewshowtime = (TextView) findViewById(R.id.textsowtime);
//        Toast.makeText(Main.this, "Hour:" + hourOfDay + " Minute" + minute, Toast.LENGTH_LONG).show();
        textViewshowtime.setText(hourOfDay + ":" + minute+"น.");

        myhour.setValue(hourOfDay);
        myminute.setValue(minute);
    }


    private void successDialog() {

        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xmcontentl that we created
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.mydialog, viewGroup, false);
        //Now we need an AlertDialog.Builder object
        final EditText editTextname = (EditText) dialogView.findViewById(R.id.nameedit);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        mynamebord.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                editTextname.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        Button buttonShowProfile = dialogView.findViewById(R.id.buttonOk);
        Button buttonShowCancel = dialogView.findViewById(R.id.cancel);
        final AlertDialog dialog = builder.create();
        dialog.show();
        buttonShowProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mynamebord.setValue(editTextname.getText().toString());
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                    }
                }, 1000);

            }
        });
        buttonShowCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }


   public void buttonselect (){

       btnselectNavBottom = (BottomNavigationView) findViewById(R.id.buttomNav);
       btnselectNavBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           //Selected icon(item) - changes to the appropriate view
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()) {
                   //Contacts
                   case R.id.item_recent:
                       Toast.makeText(Main.this, "HOME", Toast.LENGTH_LONG).show();
                       break;
                   case R.id.call:

                       break;
                   case R.id.about:
                       Toast.makeText(Main.this, "ABOUTUS", Toast.LENGTH_LONG).show();
                       break;
                   //Calendar
                   case R.id.item_end:
                       AlertDialog.Builder dialog = new AlertDialog.Builder(Main.this);
                       dialog.setIcon(R.drawable.logout);
                       dialog.setTitle("ออกจากระบบ");
                       dialog.setCancelable(false);
                       dialog.setMessage("คุณแน่ใจ ว่าจะออกจากระบบ?");
                       dialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.cancel();
                           }
                       });
                       dialog.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               FirebaseAuth.getInstance().signOut();
                               startActivity(new Intent(Main.this, LoginActivity.class));
                               finish();
                           }
                       });
                       AlertDialog alert = dialog.create();
                       alert.show();

                       break;
               }
               return true;
           }
       });
   }
}


