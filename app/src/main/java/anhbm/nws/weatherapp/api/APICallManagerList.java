package anhbm.nws.weatherapp.api;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import anhbm.nws.weatherapp.api.weather.WeatherService;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.WeatherList;
import anhbm.nws.weatherapp.application.GPSTracker;
import anhbm.nws.weatherapp.utils.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APICallManagerList {
    public String url = Constants.Base.BASE_URL;
    public static APICallManagerList instance;
    private static Retrofit retrofit;
    public PeopleManagerList peopleManagerList;

    public static APICallManagerList getListDay() {
        if (instance == null) {
            synchronized (APICallManagerList.class) {
                if (instance == null) {
                    instance = new APICallManagerList();
                }
            }

        }
        return instance;

    }

    public APICallManagerList() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        this.peopleManagerList = new PeopleManagerList();
    }

    public static <T> T getServiceList(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public class PeopleManagerList {
        WeatherService service;

        public PeopleManagerList() {
            this.service = getServiceList(WeatherService.class);
        }

        public Call<WeatherList> getContactsListDay(GPSTracker gpsTracker) {
            return service.getWeather(gpsTracker.getLatitude(), gpsTracker.getLongtitude());
        }


    }
}
