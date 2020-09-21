package com.example.farm.myfarms;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farm.myfarms.Common.Common;
import com.example.farm.myfarms.Database.DatabaseHelper;
import com.example.farm.myfarms.Model.WeatherResult;
import com.example.farm.myfarms.Retrofit.IOpenWeatherMap;
import com.example.farm.myfarms.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayWeatherFragment extends Fragment {


    private Button btn_menager;
    private Button btn_analizer;

    ImageView img_weather;
    TextView txt_cName, txt_humidity, txt_sunrise, txt_sunset, txt_pressure, txt_temperature,  txt_date_time, txt_wind, txt_geo_coord, infoTextMain;
    LinearLayout weather_panel;
    DatabaseHelper mDatabaseHelper;
    Button myButton;

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;
    ProgressBar loading;
    LinearLayout ll ;


    static TodayWeatherFragment instance;

    public static TodayWeatherFragment getInstance() {
        if (instance == null)
            instance = new TodayWeatherFragment();
        return instance;
    }

    public TodayWeatherFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {






        View view = inflater.inflate(R.layout.fragment_today_weather, container, false);

         ll = (LinearLayout)view.findViewById(R.id.linerLine);
        mDatabaseHelper = new DatabaseHelper(getActivity());
        img_weather = (ImageView) view.findViewById(R.id.img_weather);
        txt_cName = (TextView) view.findViewById(R.id.txt_city_name);
        txt_humidity = (TextView) view.findViewById(R.id.txt_humidity);
        txt_sunrise = (TextView) view.findViewById(R.id.txt_sunrise);
        txt_sunset = (TextView) view.findViewById(R.id.txt_sunset);
        txt_pressure = (TextView) view.findViewById(R.id.txt_pressure);
        txt_temperature = (TextView) view.findViewById(R.id.txt_temperature);
        txt_date_time = (TextView) view.findViewById(R.id.txt_date_time);
        txt_wind = (TextView) view.findViewById(R.id.txt_wind);
        txt_geo_coord = (TextView) view.findViewById(R.id.txt_geo_coord);
        weather_panel = (LinearLayout) view.findViewById(R.id.weather_panel);
        loading = (ProgressBar) view.findViewById(R.id.loading);
        btn_menager = (Button) view.findViewById(R.id.btn_menager);
        btn_analizer = (Button) view.findViewById(R.id.btnAnaliz);
        infoTextMain = (TextView) view.findViewById(R.id.infoTextMain);
        getWeatherInformation();
        btn_menager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Onclick", "Onclick");
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            }
        });
        btn_analizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Onclick", "onclickanazlier");
                Intent intent = new Intent(getActivity(), ListFieldsActivity.class);
                startActivity(intent); }
        });

        getNotfff();

        view.refreshDrawableState();
        return view;
    }

    private void getWeatherInformation() {
        compositeDisposable.add(mService.getWeatherByLatlng(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {
                        //Load image
                        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/").append(weatherResult.getWeather().get(0).getIcon()).append(".png").toString()).into(img_weather);


                        txt_cName.setText(weatherResult.getName());
                        txt_temperature.setText(new StringBuilder(
                                String.valueOf(weatherResult.getMain().getTemp())).append("°C").toString());
                        txt_wind.setText(new StringBuilder(String.valueOf(weatherResult.getWind().getSpeed())).append(" KM/h").toString());
                        txt_date_time.setText(Common.convertUnixToDate(weatherResult.getDt()));
                        txt_pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append(" hpa").toString());
                        txt_humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append(" %").toString());
                        txt_sunrise.setText(Common.convertUnixToHour(weatherResult.getSys().getSunrise()));
                        txt_sunset.setText(Common.convertUnixToHour(weatherResult.getSys().getSunset()));
                        txt_geo_coord.setText(new StringBuilder("").append(weatherResult.getCoord().toString()).append("").toString());
                        weather_panel.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        );

    }

    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    public void  getNotfff() {


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(100,100);




        ArrayList<String> listData = new ArrayList<>();

        Cursor kursor = mDatabaseHelper.getNotifica();
        if (kursor.getCount()>0){
            while (kursor.moveToNext()) {

                listData.add(kursor.getString(0) + " USTALONO : " + kursor.getString(1) + " : " + kursor.getString(2) + "\n" );
            }
        }

        for(int i=0  ; i < listData.size(); i++) {
            TextView myText = new TextView(getActivity());
            myText.setText(listData.get(i));
            myText.setTextSize(18);
            ll.addView(myText ,params);

            myButton = new Button(getActivity());
            myButton.setText("X");
            myButton.setBackgroundColor(Color.RED);
            final int index = listData.get(i).charAt(0);
            final int indexAscii = Character.getNumericValue(index);
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabaseHelper.deleteNotff(indexAscii);
                    refreshFragment();
                }
            });
            ll.addView(myButton,params2);

        }



    }

    public void refreshFragment()
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();

    }


}
