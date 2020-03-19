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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;

public class WeatherHorizontalAdapter extends RecyclerView.Adapter<WeatherHorizontalAdapter.ViewHolder> {

    private List<ListAPI> listPeople;
    private Context mContext;
    private int type;

    public WeatherHorizontalAdapter(Context mContext, List<ListAPI> listPeople) {
        this.listPeople = listPeople;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_horizontal_icon, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListAPI listday = listPeople.get(position);
        int fomatnNgay = listday.getDt();
        Date date = new Date(fomatnNgay * 1000l);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd-MM-yyy HH:mm");
        String day = simpleDateFormat.format(date);
        holder.tvngay.setText(day);
        holder.tvmota_trangthai.setText(listday.getWeather().get(0).getDescription());
        String sub = String.valueOf(listday.getMain().getTemp()).substring(0, 2);
        String sF = String.valueOf(listday.getMain().onConvertCelsiusToF(Double.parseDouble(sub))).substring(0, 2);
        if (type == 0) {
            holder.tvnhietdo.setText(sub + "ºC");
        } else if (type == 1) {
            holder.tvnhietdo.setText(sF + "ºF");
        }
        String s = listday.getWeather().get(0).getIcon();
        Picasso.with(mContext).load("http://api.openweathermap.org/img/w/" + s + ".png").into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return (listPeople != null) ? listPeople.size() : 0;
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
