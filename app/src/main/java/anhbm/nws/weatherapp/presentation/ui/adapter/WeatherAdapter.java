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
import anhbm.nws.weatherapp.api.weather.modelWeatherAPI.Weather;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.WeatherListDay;
import butterknife.ButterKnife;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private List<ListAPI> listPeople;
    private Context mContext;

    public WeatherAdapter(Context applicationContext, List<ListAPI> weatherListDays) {
        this.listPeople = weatherListDays;
        this.mContext = applicationContext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.icon_day_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListAPI listday = listPeople.get(position);
        holder.tvngay.setText(listday.getDtTxt());
        holder.tvmota_trangthai.setText(listday.getWeather().get(0).getDescription());
        holder.tvnhietdo.setText(String.valueOf(listday.getMain().getTemp() - 273.15) + " ºC");
        String s = listday.getWeather().get(0).getIcon();
        Picasso.with(mContext).load("http://api.openweathermap.org/img/w/" + s + ".png").into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listPeople.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvmota_trangthai, tvngay, tvnhietdo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.icon_trangthai);
            tvmota_trangthai = itemView.findViewById(R.id.trangthai_chitiet);
            tvngay = itemView.findViewById(R.id.ngay);
            tvnhietdo = itemView.findViewById(R.id.nhietdo);
        }
    }
}
