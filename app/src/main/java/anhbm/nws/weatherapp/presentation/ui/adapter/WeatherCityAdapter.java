package anhbm.nws.weatherapp.presentation.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;
import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;

public class WeatherCityAdapter extends RecyclerView.Adapter<WeatherCityAdapter.hodel> {
    private Context context;
    private List<ListAPI> listCityList;

    public WeatherCityAdapter(List<ListAPI> listCityList, Context context) {
        this.context = context;
        this.listCityList = listCityList;
    }

    @NonNull
    @Override
    public WeatherCityAdapter.hodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.icon_city_adapter, parent, false);
        return new WeatherCityAdapter.hodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherCityAdapter.hodel holder, int position) {
        ListAPI listCity = listCityList.get(position);
        holder.tocdogio.setText(String.valueOf(listCity.getWind().getSpeed()));
        holder.dogio.setText(String.valueOf(listCity.getWind().getDeg()));
        holder.nhietdo.setText(String.valueOf(listCity.getMain().getTemp()).substring(0,2));
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
            thanhpho = itemView.findViewById(R.id.tv_ten_thanhpho);
            tocdogio = itemView.findViewById(R.id.tocdogio_city);
            dogio = itemView.findViewById(R.id.dogio_city);
            nhietdo = itemView.findViewById(R.id.nhietdo_city);
            doam = itemView.findViewById(R.id.doam_city);
            trangthai = itemView.findViewById(R.id.trangthai_city);
        }
    }
}