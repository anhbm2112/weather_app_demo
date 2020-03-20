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

public class WeatherDayAdapter extends RecyclerView.Adapter<WeatherDayAdapter.hodel> {
    private Context context;
    private List<ListAPI> listAPIS;
    private static final String IS_DEGREE = "IS_DEGREE";
    private static final String IS_KELVIN = "IS_KELVIN";

    public WeatherDayAdapter(Context context, List<ListAPI> weatherList) {
        this.context = context;
        this.listAPIS = weatherList;
    }

    @NonNull
    @Override
    public WeatherDayAdapter.hodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_day_icon, parent, false);
        return new hodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDayAdapter.hodel holder, int position) {
        ListAPI listAPI = listAPIS.get(position);
        int fomatnNgay = listAPI.getDt();
        Date date = new Date(fomatnNgay * 1000l);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd-MM-yyy HH.mm");
        String day = simpleDateFormat.format(date);
        holder.tvngay.setText(day);
        holder.tvdogio.setText(String.valueOf(listAPI.getWind().getSpeed()));
        holder.tvgio.setText(String.valueOf(listAPI.getWind().getDeg()));
        String oC = String.valueOf(listAPI.getMain().getTemp()).substring(0,2);

        String oF = String.valueOf(listAPI.getMain().onConvertCelsiusToF(Double.parseDouble(oC))).substring(0, 2);

        SharedPreferences sharedPreferences = context.getSharedPreferences("key", Context.MODE_PRIVATE);
        boolean c = sharedPreferences.getBoolean(IS_DEGREE, true);
        boolean k = sharedPreferences.getBoolean(IS_KELVIN, false);
            if (c && !k) {
                holder.tvnhietdo.setText(oC + "ºC");
            } else if (!c && k) {
                holder.tvnhietdo.setText(oF + "ºF");
            }

        holder.tvdoam.setText(String.valueOf(listAPI.getMain().getHumidity() + "%"));
        holder.tvtrangthai.setText(listAPI.getWeather().get(0).getDescription());
        String ss = listAPI.getWeather().get(0).getIcon();
        Picasso.with(context).load("http://api.openweathermap.org/img/w/" + ss + ".png").into(holder.iconview);

    }

    @Override
    public int getItemCount() {
        return listAPIS.size();
    }

    public class hodel extends RecyclerView.ViewHolder {
        TextView tvngay, tvgio, tvdogio, tvnhietdo, tvtrangthai, tvdoam;
        ImageView iconview;

        public hodel(@NonNull View itemView) {
            super(itemView);
            tvngay = itemView.findViewById(R.id.ngaylist);
            tvgio = itemView.findViewById(R.id.gio);
            tvdogio = itemView.findViewById(R.id.dogio);
            tvnhietdo = itemView.findViewById(R.id.nhietdolist);
            tvtrangthai = itemView.findViewById(R.id.trangthailist);
            tvdoam = itemView.findViewById(R.id.doam);
            iconview = itemView.findViewById(R.id.iconlist);
        }
    }
}
