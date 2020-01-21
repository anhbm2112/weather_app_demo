package anhbm.nws.weatherapp.domains.interactors;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import anhbm.nws.weatherapp.api.APICallListener;
import anhbm.nws.weatherapp.api.APICallManager;
import anhbm.nws.weatherapp.api.APICallManagerList;
import anhbm.nws.weatherapp.api.weather.modelWeatherAPI.Weather;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.WeatherList;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.WeatherListDay;
import anhbm.nws.weatherapp.application.GPSTracker;
import anhbm.nws.weatherapp.utils.Enums;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherInteractor {
    APICallListener listener;
    private List<WeatherListDay> weatherListDays = new ArrayList<>();

    public WeatherInteractor(APICallListener listener) {
        this.listener = listener;
    }

    public void callAPIGetContacts(GPSTracker gpsTracker) {
        final Enums.APIRoute route = Enums.APIRoute.GET_WEATHER;
        Call<Weather> call = APICallManager.getInstance().peopleManager.getContacts(gpsTracker);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                listener.onAPICallSucceed(route, response.body());

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                listener.onAPICallFailed(route, t);

            }
        });

    }

    public void callAPIlist(GPSTracker gpsTracker) {
        final Enums.APIRoute route = Enums.APIRoute.GET_WEATHER;
        Call<WeatherList> call = APICallManagerList.getListDay().peopleManagerList.getContactsListDay(gpsTracker);
        call.enqueue(new Callback<WeatherList>() {
            @Override
            public void onResponse(Call<WeatherList> call, Response<WeatherList> response) {
                listener.onAPICallSucceedList(route, response.body());
                Log.e("Chay vao Dung", String.valueOf(response.body()));
            }

            @Override
            public void onFailure(Call<WeatherList> call, Throwable t) {
                Log.e("Ket Qua", String.valueOf(t));
            }
        });

    }
}
