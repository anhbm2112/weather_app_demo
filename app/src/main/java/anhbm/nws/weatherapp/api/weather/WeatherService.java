package anhbm.nws.weatherapp.api.weather;

import java.security.Key;

import anhbm.nws.weatherapp.api.weather.modelWeatherAPI.Weather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherService {
    @GET("v2/nearest_city?key=cd058c32-889e-467e-a840-e640670099d2")
    Call<Weather>
    getPeople();
}
