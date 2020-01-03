package anhbm.nws.weatherapp.api.weather;

import retrofit2.Call;
import retrofit2.http.GET;


public interface WeatherService {
    @GET("/nearest_city")
    Call<WeatherResponse>
    getPeople();
}
