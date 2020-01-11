package anhbm.nws.weatherapp.domains.interactors;

import android.util.Log;

import anhbm.nws.weatherapp.api.APICallListener;
import anhbm.nws.weatherapp.api.APICallManager;
import anhbm.nws.weatherapp.api.weather.WeatherResponse;
import anhbm.nws.weatherapp.api.weather.modelWeatherAPI.Weather;
import anhbm.nws.weatherapp.utils.Enums;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherInteractor {
    APICallListener listener;

    public WeatherInteractor(APICallListener listener) {
        this.listener = listener;
    }
    public void callAPIGetContacts() {
        final Enums.APIRoute route = Enums.APIRoute.GET_WEATHER;
        Call<Weather> call = APICallManager.getInstance().peopleManager.getContacts();
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                listener.onAPICallSucceed(route, response.body());
                Log.e("Chay vao Dung", String.valueOf(response.body()));
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                listener.onAPICallFailed(route, t);
                Log.e("Ket Qua", String.valueOf(t));
            }
        });
    }



}
