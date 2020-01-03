package anhbm.nws.weatherapp.api.weather;

import retrofit2.Call;
import retrofit2.http.GET;


public interface WeatherService {
    @GET("/weather.json")
    Call<WeatherResponse>
    getPeople();
}
