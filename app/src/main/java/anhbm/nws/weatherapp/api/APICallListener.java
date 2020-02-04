package anhbm.nws.weatherapp.api;


import java.util.List;

import anhbm.nws.weatherapp.api.weather.modelWeatherAPI.Weather;
import anhbm.nws.weatherapp.api.weather.modelWeatherCity.WeatherCity;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.WeatherList;
import anhbm.nws.weatherapp.utils.Enums;

public interface APICallListener {
    void onAPICallSucceed(Enums.APIRoute route, Weather weather);

    void onAPICallSucceedList(WeatherList weatherList);

    void onAPICallSucceedCity(WeatherList weatherCity);

    void onAPICallFailed(Enums.APIRoute route, Throwable throwable);
}
