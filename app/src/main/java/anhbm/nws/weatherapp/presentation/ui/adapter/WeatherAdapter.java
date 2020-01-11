package anhbm.nws.weatherapp.presentation.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import anhbm.nws.weatherapp.api.weather.modelWeatherAPI.Weather;
import butterknife.ButterKnife;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private List<Weather> listPeople;
    private Context mContext;

    public WeatherAdapter(List<Weather> listPeople, Context mContext) {
        this.listPeople = listPeople;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(mContext, listPeople.get(position));
    }

    @Override
    public int getItemCount() {
        return listPeople.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
            initLayout(itemView);
        }

        private void initLayout(View view) {
            ButterKnife.bind(this, view);
        }

        void bindView(Context context, Weather data) {
        }
    }
}
