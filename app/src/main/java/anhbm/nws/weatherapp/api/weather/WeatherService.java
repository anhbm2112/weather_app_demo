package anhbm.nws.weatherapp.api.weather;

import anhbm.nws.weatherapp.api.weather.modelWeatherAPI.Weather;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.WeatherList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherService {
    @GET("v2/nearest_city?key=cd058c32-889e-467e-a840-e640670099d2")
    Call<Weather> getPeople(@Query("lat") double lat,
                            @Query("lon") double lon);
//"https://samples.openweathermap.org/data/2.5/forecast?lat=21.020202 &lon={}&appid=b6907d289e10d714a6e88b30761fae22

    @GET("data/2.5/forecast?appid=ecdd51cc4c60e1fb08cf11263bbb546a")
    Call<WeatherList> getWeather(@Query("lat") double lat,
                                          @Query("lon") double lon);
}
