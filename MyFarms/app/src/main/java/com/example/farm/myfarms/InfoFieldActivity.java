package com.example.farm.myfarms;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.example.farm.myfarms.Database.DatabaseHelper;
import com.example.farm.myfarms.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;
import com.example.farm.myfarms.Adapter.WeatherForecasAdapter;

import io.reactivex.annotations.NonNull;

public class InfoFieldActivity extends AppCompatActivity {

    private static final String TAG = "InfoFieldActivity";

    EditText textNameInfo;
    EditText textStatusInfo;
    EditText textSowingInfo;
    EditText textDataInfo;
    EditText textProposedInfo;
    Button btnAnalizerInfo;
    DatabaseHelper mDatabaseHelper;
    FirebaseFirestore db;
    private ArrayList<Product> productList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        WeatherForecasAdapter adapter = null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_field);
        db = FirebaseFirestore.getInstance();
        textNameInfo = (EditText) findViewById(R.id.textNameInfo);
        textStatusInfo = (EditText) findViewById(R.id.textStatusInfo);
        textSowingInfo = (EditText) findViewById(R.id.textSowingInfo);
        textProposedInfo = (EditText) findViewById(R.id.textProposedInfo);
        btnAnalizerInfo = (Button) findViewById(R.id.btnAnalizerInfo);
        mDatabaseHelper = new DatabaseHelper(this);


        int pozucja =  getIntent().getIntExtra("WYBRANY", 0);

         pozucja = pozucja+1;
        textNameInfo.setText(getName(pozucja));

        boolean ozime = getName(pozucja).contains("ozime");

        adapter.ozimCzyJary = ozime;

        textSowingInfo.setText(getSowing(pozucja));
        textStatusInfo.setText(GetStatus(pozucja));
        firbaseWojewodztwo(getSowing(pozucja) , getProvince(pozucja));
        int finalPozucja = pozucja;
        btnAnalizerInfo.setEnabled(false);
        textProposedInfo.setEnabled(false);
        if(textProposedInfo.equals("Loading..") == false)
            btnAnalizerInfo.setEnabled(true);

        btnAnalizerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String taks = textProposedInfo.getText().toString();

                Intent intent = new Intent(InfoFieldActivity.this, CalendarForecastActivity.class);
                intent.putExtra("pozycja", finalPozucja);
                intent.putExtra("DATA", taks);
                startActivity(intent);
            }
        });
    }




    private void firbaseWojewodztwo(String sowing, String province){


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
                               if(p.getName().equals(sowing) == true){
                                   textProposedInfo.setText(p.getWojewodztwa().get(province.toLowerCase()));
                              }

                            }

                        }

                    }
                });
    }




    private String getSowing(int pozucja) {

        String textViewWybrane = null;
        Cursor kursor = mDatabaseHelper.wyswietlZasiane(pozucja);
        if (kursor.getCount()>0){
            while (kursor.moveToNext()){
                textViewWybrane = kursor.getString(0 );
                return textViewWybrane;
            }
        }
        return textViewWybrane;
    }


    private String getProvince(int pozucja) {
        String textViewWybrane = null;
        Cursor kursor = mDatabaseHelper.getProvinces(pozucja);
        if (kursor.getCount()>0){
            while (kursor.moveToNext()){
                textViewWybrane = kursor.getString(0 );
                return textViewWybrane;
            }
        }
        return textViewWybrane;
    }

    private String GetStatus(int pozucja) {
        String textViewWybrane = null;
        Cursor kursor = mDatabaseHelper.wyswietlStatus(pozucja);
        if (kursor.getCount()>0){
            while (kursor.moveToNext()){
                textViewWybrane = kursor.getString(0 );
                return textViewWybrane;
            }
        }
        return textViewWybrane;
    }




    public String  getName(int pozycka) {
        String textViewWybrane = null;
        Cursor kursor = mDatabaseHelper.wyswietlnazw(pozycka);
        if (kursor.getCount()>0){
            while (kursor.moveToNext()){
                textViewWybrane = kursor.getString(0 );
                return textViewWybrane;
            }
        }
        return textViewWybrane;
    }

















}
