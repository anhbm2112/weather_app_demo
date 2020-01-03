package anhbm.nws.weatherapp.api.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import anhbm.nws.weatherapp.api.BaseResponse;


public class WeatherResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<Weather> weathers = null;

    public List<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }

}
