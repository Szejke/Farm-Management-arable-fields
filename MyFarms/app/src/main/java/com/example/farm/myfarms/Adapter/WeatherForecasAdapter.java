package com.example.farm.myfarms.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.farm.myfarms.Common.Common;
import com.example.farm.myfarms.Model.WeatherForecastResult;
import com.example.farm.myfarms.R;
import com.squareup.picasso.Picasso;

import static android.graphics.Color.parseColor;

public class WeatherForecasAdapter extends RecyclerView.Adapter<WeatherForecasAdapter.MyViewHolder> {
    Context context;
    WeatherForecastResult weatherForecastResult;
    public static boolean ozimCzyJary;


    public WeatherForecasAdapter(Context context, WeatherForecastResult weatherForecastResult) {
        this.context = context;
        this.weatherForecastResult = weatherForecastResult;
    }





    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_weather_forecast,parent,false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
   String tak = Common.convertUnixToHour(weatherForecastResult.list.get(position).dt);
       Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
               .append(weatherForecastResult.list.get(position).weather.get(0).getIcon())
               .append(".png").toString()).into(holder.img_weather);
       holder.txt_data_time.setText(new StringBuilder(Common.convertUnixToDate(weatherForecastResult.list.
               get(position).dt)));
       holder.txt_description.setText(new StringBuilder(weatherForecastResult.list.get(position)
               .weather.get(0).getDescription()));
       holder.txt_temperature.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position)
               .main.getTemp())).append(" C"));

        if (ozimCzyJary == true) {
            if (weatherForecastResult.list.get(position).weather.get(0).getDescription().equals( "few clouds")
                    || weatherForecastResult.list.get(position).weather.get(0).getDescription().equals("sky is clear")
                    || weatherForecastResult.list.get(position).weather.get(0).getDescription().equals("scattered clouds")
                    )
            {
                holder.color.setBackgroundColor(parseColor("#fde910"));
            } else if (weatherForecastResult.list.get(position).weather.get(0).getDescription().equals("few clouds")
                    || weatherForecastResult.list.get(position).weather.get(0).getDescription().equals("sky is clear")
                    || weatherForecastResult.list.get(position).weather.get(0).getDescription().equals("scattered clouds")
                    && weatherForecastResult.list.get(position).main.getTemp() > 18 && weatherForecastResult.list.get(position).main.getTemp() < 35
                    && weatherForecastResult.list.get(position).main.getHumidity() < 80 && weatherForecastResult.list.get(position).main.getHumidity() > 50) {
                holder.color.setBackgroundColor(parseColor("#93f600"));
            } else
                holder.color.setBackgroundColor(parseColor("#e24d2f"));
        }
        else{
            if (weatherForecastResult.list.get(position).weather.get(0).getDescription().equals("few clouds")
                    || weatherForecastResult.list.get(position).weather.get(0).getDescription().equals("broken clouds")
                    || weatherForecastResult.list.get(position).weather.get(0).getDescription().equals( "overcast clouds")
                    || weatherForecastResult.list.get(position).weather.get(0).getDescription().equals("scattered clouds")
                    ) {
                holder.color.setBackgroundColor(parseColor("#fde910"));
            } else if (weatherForecastResult.list.get(position).weather.get(0).getDescription().equals("few clouds")
                    || weatherForecastResult.list.get(position).weather.get(0).getDescription().equals("broken clouds")
                     || weatherForecastResult.list.get(position).weather.get(0).getDescription().equals("overcast clouds")
                    || weatherForecastResult.list.get(position).weather.get(0).getDescription().equals("scattered clouds")
                    && weatherForecastResult.list.get(position).main.getTemp() < 15 && weatherForecastResult.list.get(position).main.getTemp() < 6
                    && weatherForecastResult.list.get(position).main.getHumidity() > 50 && weatherForecastResult.list.get(position).main.getHumidity() < 85) {
                holder.color.setBackgroundColor(parseColor("#93f600"));
            } else
                holder.color.setBackgroundColor(parseColor("#e24d2f"));
        }
    }






    @Override
    public int getItemCount() {

        return weatherForecastResult.list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        FrameLayout color;
        TextView txt_data_time,txt_description, txt_temperature;
        ImageView img_weather;
        public MyViewHolder(View itemView) {
            super(itemView);

            color = (FrameLayout) itemView.findViewById(R.id.colorDot) ;
            img_weather = (ImageView)itemView.findViewById(R.id.img_weather_for);
            txt_data_time =(TextView)itemView.findViewById(R.id.txt_data_for);
            txt_description= (TextView)itemView.findViewById(R.id.txt_description_for);
            txt_temperature = (TextView)itemView.findViewById(R.id.txt_temperature_for);
        }
    }
}
