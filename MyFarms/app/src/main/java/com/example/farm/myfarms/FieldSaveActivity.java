package com.example.farm.myfarms;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farm.myfarms.Database.DatabaseHelper;
import com.example.farm.myfarms.Model.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FieldSaveActivity extends AppCompatActivity {

    EditText editTextName;
    Spinner spinnerSowing;
    Spinner spinnerStatus;
    Spinner spinnerclass;
    EditText editTextLocality;
    EditText editTextProvince;
    EditText editTextArea;
    ImageButton iBtnArea;
    ImageButton iBtnLocality;
    ImageButton iBtnProv;
    TextView textView9;


    Button btnSave;
    FirebaseFirestore db;
    private ArrayList<Product> productList;

    DatabaseHelper mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_save);
        db = FirebaseFirestore.getInstance();
        btnSave = (Button) findViewById(R.id.btnSave);
        textView9 = (TextView ) findViewById(R.id.textView9);
        editTextName = (EditText) findViewById(R.id.editName);
        spinnerSowing = (Spinner) findViewById(R.id.spinnerSowing);
        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
        editTextLocality = (EditText) findViewById(R.id.editLocaliti);
        editTextProvince = (EditText) findViewById(R.id.editProvince);
        editTextArea = (EditText) findViewById(R.id.editArea);
        iBtnArea = (ImageButton)  findViewById(R.id.iBtnArea);
        iBtnLocality = (ImageButton) findViewById(R.id.iBtnLocality);
        iBtnProv = (ImageButton) findViewById(R.id.iBtnProv);
        spinnerclass = (Spinner) findViewById(R.id.spinnerclass);

        mDB = new DatabaseHelper(this);
        readData();
        firbaseZasiane();
        statusAdd();
        classGrainAdd();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSavrClick();

                Intent i=new Intent(FieldSaveActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        btnEditValueArea();
        btnEditValueLocality();
        btnEditValueProv();
        changeInfo();


    }
    private void classGrainAdd(){

        String[] arraySpinner = new String[] {
                "klasa I", "klasa II", "klasa IIIa" , "klasa IIIb", "klasa IVa", "klasa IVb", "klasa V", "klasa VI"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerclass.setAdapter(adapter);
    }


    private void changeInfo(){


        spinnerclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (id == 0){
                    textView9.setText("Klasa I - Najlepsza Gleba \n Zalecane: Pszenica");
                }
                else if (id == 1){
                    textView9.setText("Klasa II -  Zalecane: Pszenica");
                }
                else if (id == 2){
                    textView9.setText("Klasa IIIa - Zalecane: Jęcznień \n" +
                            "Pszenżyto \n" +
                            "Żyto \n" );
                }
                else if (id == 3){
                    textView9.setText("Klasa IIIb - Zalecane: Jęcznień \n" +
                            "Pszenżyto \n" +
                            "Żyto \n" );
                }
                else if (id == 4){
                    textView9.setText("Klasa IVa - Zalecane: Jęcznień \n" +
                            "Pszenżyto \n" +
                            "Żyto \n" );
                }
                else if (id == 5){
                    textView9.setText("Klasa IVb - Zalecane:  " +
                            "Żyto \n" +
                            "Owies \n"

                    );
                }

                else if (id == 6){
                    textView9.setText("Klasa V - Zalecane:  " +
                            "Owies \n"

                    );
                }
                else if (position == 7){
                    textView9.setText("Klasa VI - gleba słaba"

                    );
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView9.setText("Klasa I - Najlepsza Gleba \n Zalecane: Pszenica");
            }
        });


    }


    private void statusAdd(){

        String[] arraySpinner = new String[] {
                "Zasiano", "Zebrano", "Zaorano"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
    }
    private void firbaseZasiane(){
        productList = new ArrayList<Product>();
        db.collection("products").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Product p = d.toObject(Product.class);
                                p.setId(d.getId());
                                productList.add(p);
                            }
                            List<String> prod = new ArrayList<String>();
                            for(int i= 0 ; i<productList.size(); i++)
                                prod.add(productList.get(i).getName());
                            String[] stringArray = prod.toArray(new String[0]);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(FieldSaveActivity.this,
                                    android.R.layout.simple_spinner_item, stringArray);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerSowing.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void btnEditValueArea(){

        iBtnArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(editTextArea.isEnabled() == true)
                    editTextArea.setEnabled(false);
                else editTextArea.setEnabled(true);
            }
        });
    }

    private void btnEditValueLocality(){
        iBtnLocality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextLocality.isEnabled() == true)
                    editTextLocality.setEnabled(false);
                else editTextLocality.setEnabled(true);
            }
        });
    }

    private void btnEditValueProv(){
        iBtnProv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextProvince.isEnabled() == true)
                    editTextProvince.setEnabled(false);
                else editTextProvince.setEnabled(true);
            }
        });
    }

    private void btnSavrClick(){
        String name = editTextName.getText().toString();
        String sowing = spinnerSowing.getSelectedItem().toString();
        String status = spinnerStatus.getSelectedItem().toString();
        String locality = editTextLocality.getText().toString();
        String province = editTextProvince.getText().toString();
        String area = editTextArea.getText().toString();
        String classqrain = spinnerclass.getSelectedItem().toString();
        String lat = null;
        String lng = null ;
        lat = getIntent().getStringExtra("LAT");
        lng = getIntent().getStringExtra("LNG" );

        if(name != null && sowing != null && status != null && locality != null && province != null && area != null && lat != null && lng != null && classqrain != null)
        saveToDataBase(name,sowing,status, area,locality,province, lat , lng, classqrain);
        else  Toast.makeText(FieldSaveActivity.this, "Wypelnij wszystkie pola", Toast.LENGTH_SHORT).show();
    }
    private void saveToDataBase(String nameString, String sowing, String status, String area, String locality, String province, String lat, String lng, String classqrain){
            boolean udalosie = mDB.saveDane(nameString,sowing,status,area,locality,province,lat,lng,classqrain );
            if (udalosie == true){
                Toast.makeText(FieldSaveActivity.this, "dzialka zostala dodana", Toast.LENGTH_LONG).show();

                }
            else
                Toast.makeText(FieldSaveActivity.this, "dzialka nie zostala dodana", Toast.LENGTH_LONG).show();
    }

    private void readData() {
        double area = 0;
        String miejscowosc = getIntent().getStringExtra("MIEJSCOWOSC");
        editTextLocality.setText(miejscowosc);
        String wojewodztwo =   getIntent().getStringExtra("WOJEWODZTWO");
        editTextProvince.setText(wojewodztwo);
        area =  getIntent().getDoubleExtra("AREA", area );
        String measurment = String.valueOf(area);
         editTextArea.setText(measurment);
    }







}
