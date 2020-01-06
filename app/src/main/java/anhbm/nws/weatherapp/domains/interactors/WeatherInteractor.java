package anhbm.nws.weatherapp.domains.interactors;

import android.content.Context;
import android.widget.Toast;

import anhbm.nws.weatherapp.api.APICallListener;
import anhbm.nws.weatherapp.api.APICallManager;
import anhbm.nws.weatherapp.api.weather.WeatherResponse;
import anhbm.nws.weatherapp.utils.Enums;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherInteractor implements Interactor {
    APICallListener listener;
    Context context;

    public WeatherInteractor(APICallListener listener) {
        this.listener = listener;
    }

    public void callAPIGetContacts() {
        final Enums.APIRoute route = Enums.APIRoute.GET_WEATHER;
        Call<WeatherResponse> call = APICallManager.getInstance().peopleManager.getContacts();
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                listener.onAPICallSucceed(route, response.body());
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                listener.onAPICallFailed(route, t);
            }
        });
    }
}
