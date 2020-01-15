package anhbm.nws.weatherapp.api;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;

import anhbm.nws.weatherapp.api.weather.WeatherResponse;
import anhbm.nws.weatherapp.api.weather.WeatherService;
import anhbm.nws.weatherapp.api.weather.modelWeatherAPI.Weather;
import anhbm.nws.weatherapp.application.GPSTracker;
import anhbm.nws.weatherapp.presentation.ui.screen.BaseActivity;
import anhbm.nws.weatherapp.utils.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class APICallManager extends BaseActivity {
    public String endpoint = Constants.Path.DEFAULT_URL_API_PRODUCTION;
    public static APICallManager instance;
    private static Retrofit retrofit;
    public PeopleManager peopleManager;
    Context context;



    /**
     * singleton class instance
     *
     * @return APICallManager
     *
     */


    public static APICallManager getInstance() {
        if (instance == null) {
            synchronized (APICallManager.class) {
                if (instance == null) {
                    instance = new APICallManager();
                }
            }
        }
        return instance;
    }


    public APICallManager() {
        // enable logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        // registering API endpoint manager
        this.peopleManager = new PeopleManager();
    }

    public static <T> T getService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }


    public class PeopleManager {
        WeatherService service;

        public PeopleManager() {
            this.service = getService(WeatherService.class);
        }

        public Call<Weather> getContacts(double lat, double lon) {
            return service.getPeople(lat,lon);

        }
    }
}
