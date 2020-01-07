
package anhbm.nws.weatherapp.api.weather.example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Current {

    @SerializedName("weather")
    @Expose
    private WeatherForecasts weather;
    @SerializedName("pollution")
    @Expose
    private Pollution pollution;

    public WeatherForecasts getWeather() {
        return weather;
    }

    public void setWeather(WeatherForecasts weather) {
        this.weather = weather;
    }

    public Pollution getPollution() {
        return pollution;
    }

    public void setPollution(Pollution pollution) {
        this.pollution = pollution;
    }

}
