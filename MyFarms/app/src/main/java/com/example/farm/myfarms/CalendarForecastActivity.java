package com.example.farm.myfarms;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farm.myfarms.Adapter.WeatherForecasAdapter;
import com.example.farm.myfarms.Common.Common;
import com.example.farm.myfarms.Database.DatabaseHelper;
import com.example.farm.myfarms.Model.WeatherForecastResult;
import com.example.farm.myfarms.Retrofit.IOpenWeatherMap;
import com.example.farm.myfarms.Retrofit.RetrofitClient;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CalendarForecastActivity extends AppCompatActivity {


    private String TAG = "CalendarForecastActivity";
    static CalendarForecastActivity instance;
    DatabaseHelper mDatabaseHelper;
    String Datatext;



    public static CalendarForecastActivity getInstance() {
        if (instance == null)
            instance = new CalendarForecastActivity();
        return instance;
    }

    CompactCalendarView calendarView;
    private SimpleDateFormat dataFormat = new SimpleDateFormat("MMM- yyyy", Locale.getDefault());
    TextView myDate;

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    TextView txt_cite_name_for, txt_geo_coord_for, toolbarTxt;
    RecyclerView recycler_forecast;
    Button btnEnd;
    Toolbar toolbar;

    public CalendarForecastActivity() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_forecast);
        btnEnd = (Button) findViewById(R.id.btnEnd);
        calendarView = (CompactCalendarView) findViewById(R.id.calendarView);
        calendarView.setUseThreeLetterAbbreviation(true);
        myDate = (TextView) findViewById(R.id.myDate);
        mDatabaseHelper = new DatabaseHelper(this);
        txt_cite_name_for = (TextView) findViewById(R.id.txt_city_name_for);
        txt_geo_coord_for = (TextView) findViewById(R.id.txt_geo_coord_for);
        recycler_forecast = (RecyclerView) findViewById(R.id.recycler_dorecast);
        recycler_forecast.setHasFixedSize(true);
        recycler_forecast.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        getForceastWeatherInformation();
        try {
            String data = getIntent().getStringExtra("DATA");
            addEventCalendar(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Datatext = sdf.format(dateClicked);
                if(calendarView.getEvents(dateClicked) !=null);
                Toast.makeText(context, "Day was clicked: " + " " + Datatext, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                toolbarTxt = (TextView) findViewById(R.id.toolbarTxt);
                toolbarTxt.setText(dataFormat.format(firstDayOfNewMonth));
            }
        });
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clikcEnt(v);
            }
        });
    }

private void clikcEnt(View v){
    Intent i = new Intent(CalendarForecastActivity.this, MainActivity.class);
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    int pozucja = getIntent().getIntExtra("pozycja", 0);
    mDatabaseHelper.setNotific(getName(pozucja),Datatext);

    startActivity(i);
}

    private void addEventCalendar(String data) throws ParseException {
        data = data.trim();
        String firstData = data.substring(0, 5);
        String seccondData = data.substring(9);

        firstData = "2019/" + firstData;
        seccondData = "2019/" + seccondData;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date1 = sdf.parse(firstData);
            Date date2 = sdf.parse(seccondData);
            long diff = date2.getTime() - date1.getTime();
            diff = diff / (1000 * 60 * 60 * 24);
            for (int i = 0; i <= diff; i++) {
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(firstData));
                c.add(Calendar.DATE, i);
                String tak = sdf.format(c.getTime());
                Date date = sdf.parse(tak);
                long timeInMillis = date.getTime();
                Event ev1 = new Event(Color.CYAN, timeInMillis, "");
                calendarView.addEvent(ev1);
            }
            for (int i = 0; i <= diff; i++) {
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(firstData));
                c.add(Calendar.YEAR, 1);
                c.add(Calendar.DATE, i);
                String tak = sdf.format(c.getTime());
                Date date = sdf.parse(tak);
                long timeInMillis = date.getTime();
                Event ev1 = new Event(Color.GREEN, timeInMillis, "");
                calendarView.addEvent(ev1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String getLat(int pozucja) {
        String textViewWybrane = null;
        Cursor kursor = mDatabaseHelper.cordslat(pozucja);
        if (kursor.getCount() > 0) {
            while (kursor.moveToNext()) {
                textViewWybrane = kursor.getString(0);
                return textViewWybrane;
            }
        }
        return textViewWybrane;
    }

    private String getLng(int pozucja) {
        String textViewWybrane = null;
        Cursor kursor = mDatabaseHelper.cordslng(pozucja);
        if (kursor.getCount() > 0) {
            while (kursor.moveToNext()) {
                textViewWybrane = kursor.getString(0);
                return textViewWybrane;
            }
        }
        return textViewWybrane;
    }

    private void getForceastWeatherInformation() {
        int pozucja = getIntent().getIntExtra("pozycja", 0);
        compositeDisposable.add(mService.getForecastWeatherByLatlng(
                getLat(pozucja),
                getLng(pozucja),
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecastResult>() {
                    @Override
                    public void accept(WeatherForecastResult weatherForecastResult) throws Exception {

                        displayForecastWeather(weatherForecastResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ERROR", "" + throwable.getMessage());
                    }
                })
        );
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


    private void displayForecastWeather(WeatherForecastResult weatherForecastResult) {

        txt_cite_name_for.setText(new StringBuilder(weatherForecastResult.city.name));
        txt_geo_coord_for.setText(new StringBuilder(weatherForecastResult.city.coord.toString()));

        WeatherForecasAdapter adapter = new WeatherForecasAdapter(getApplicationContext(), weatherForecastResult);
        recycler_forecast.setAdapter(adapter);

    }

}
