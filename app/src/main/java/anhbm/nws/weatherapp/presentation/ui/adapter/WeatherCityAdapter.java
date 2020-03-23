package anhbm.nws.weatherapp.presentation.ui.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;

public class WeatherCityAdapter extends RecyclerView.Adapter<WeatherCityAdapter.hodel> {
    private Context context;
    private List<ListAPI> listCityList;
    private static final String IS_DEGREE = "IS_DEGREE";
    private static final String IS_KELVIN = "IS_KELVIN";

    public WeatherCityAdapter(Context context,List<ListAPI> listCityList) {
        this.listCityList = listCityList;
        this.context = context;
    }


    @NonNull
    @Override
    public WeatherCityAdapter.hodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_city_icon, parent, false);
        return new WeatherCityAdapter.hodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherCityAdapter.hodel holder, int position) {
        ListAPI listCity = listCityList.get(position);

        int fomatnNgay = listCity.getDt();
        Date date = new Date(fomatnNgay * 1000l);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd-MM-yyy");
        String day = simpleDateFormat.format(date);
        holder.ngay.setText(day);
        SharedPreferences sharedPreferences = context.getSharedPreferences("key", context.MODE_PRIVATE);
        boolean c = sharedPreferences.getBoolean(IS_DEGREE, true);
        boolean k = sharedPreferences.getBoolean(IS_KELVIN, false);
        Double temp = Double.valueOf(String.valueOf(listCity.getMain().getTemp()).substring(0, 2));
        Double temF = Double.valueOf(String.valueOf(listCity.getMain().onConvertCelsiusToF(temp)).substring(0, 3));
        if (c && !k) {
            holder.nhietdo.setText(temp + "ºC");
        } else if (!c && k) {
            holder.nhietdo.setText(temF + "ºF");
        }

        holder.tocdogio.setText(String.valueOf(listCity.getWind().getSpeed()));
        holder.dogio.setText(String.valueOf(listCity.getWind().getDeg()));
        holder.doam.setText(String.valueOf(listCity.getMain().getHumidity() + "%"));
        holder.trangthai.setText(listCity.getWeather().get(0).getDescription());
        String maIconAnh = listCity.getWeather().get(0).getIcon();
        Picasso.with(context).load("http://api.openweathermap.org/img/w/" + maIconAnh + ".png").into(holder.imagIcon);

    }

    @Override
    public int getItemCount() {
        return listCityList.size();
    }

    public class hodel extends RecyclerView.ViewHolder {
        TextView ngay, thanhpho, tocdogio, dogio, nhietdo, doam, trangthai;
        ImageView imagIcon;

        public hodel(@NonNull View itemView) {
            super(itemView);
            imagIcon = itemView.findViewById(R.id.iconimg_city);
            ngay = itemView.findViewById(R.id.iconngay_city);
//            thanhpho = itemView.findViewById(R.id.tv_ten_thanhpho);
            tocdogio = itemView.findViewById(R.id.tocdogio_city);
            dogio = itemView.findViewById(R.id.dogio_city);
            nhietdo = itemView.findViewById(R.id.nhietdo_city);
            doam = itemView.findViewById(R.id.doam_city);
            trangthai = itemView.findViewById(R.id.trangthai_city);
        }
    }
}
