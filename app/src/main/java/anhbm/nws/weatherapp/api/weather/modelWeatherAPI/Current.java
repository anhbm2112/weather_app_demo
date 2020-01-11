
package anhbm.nws.weatherapp.api.weather.modelWeatherAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Current {

    @SerializedName("weather")
    @Expose
    private Weather_current WeatherCurrent;
    @SerializedName("pollution")
    @Expose
    private Pollution pollution;

    public Weather_current getWeatherCurrent() {
        return WeatherCurrent;
    }

    public void setWeatherCurrent(Weather_current weather) {
        this.WeatherCurrent = weather;
    }

    public Pollution getPollution() {
        return pollution;
    }

    public void setPollution(Pollution pollution) {
        this.pollution = pollution;
    }

}
