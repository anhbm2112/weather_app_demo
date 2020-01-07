package anhbm.nws.weatherapp.api.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import anhbm.nws.weatherapp.api.BaseResponse;
import anhbm.nws.weatherapp.api.weather.example.WeatherForecasts;


public class WeatherResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<WeatherForecasts> weathers = null;

    public List<WeatherForecasts> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<WeatherForecasts> weathers) {
        this.weathers = weathers;
    }
}
