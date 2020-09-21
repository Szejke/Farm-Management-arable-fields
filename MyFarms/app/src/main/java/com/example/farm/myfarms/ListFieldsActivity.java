package com.example.farm.myfarms;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.farm.myfarms.Common.Common;
import com.example.farm.myfarms.Model.WeatherResult;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farm.myfarms.Adapter.FieldListAdapter;
import com.example.farm.myfarms.Database.DatabaseHelper;
import com.example.farm.myfarms.Retrofit.IOpenWeatherMap;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ListFieldsActivity extends AppCompatActivity {


    TextView name;
    TextView status;
    TextView zboze;
    ImageView image;
    TodayWeatherFragment fragment;
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    DatabaseHelper mDatabaseHelper;

    ListView mListView;

    Button btnAnalizerInfo;

    private static final String TAG = "ListFieldsactuvuty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fields);
        mListView = (ListView) findViewById(R.id.ListViewFields);
        mDatabaseHelper = new DatabaseHelper(this);
        image = (ImageView) findViewById(R.id.imageListWeather);
        populateListView();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ListFieldsActivity.this, InfoFieldActivity.class);
                i.putExtra("WYBRANY", position);
                startActivity(i);
            }
        });
    }

    private void populateListView() {
        Log.d(TAG, "populater ListView: Displaying data in the List View. ");

        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        ArrayList<String> cords = new ArrayList<>();
        ArrayList<ImageView> tag = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(1) + " : " + data.getString(2) + " : " + data.getString(3) + " : " + data.getString(6));
        }

        while (data.moveToNext()) {
            listData.add(data.getString(8) + " : " + data.getString(9));
        }


        //  imageadd(cords);


        mListView = (ListView) findViewById(R.id.ListViewFields);

        FieldListAdapter adapter = new FieldListAdapter(ListFieldsActivity.this, listData, image);
        mListView.setAdapter(adapter);
    }

    private void imageadd(ArrayList<String> cords) {




    }
}
