package anhbm.nws.weatherapp.api;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import anhbm.nws.weatherapp.api.weather.WeatherService;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.WeatherList;
import anhbm.nws.weatherapp.utils.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APICallManagerCity {

    public String url = Constants.Base.BASE_URL;
    public static APICallManagerCity instance;
    private static Retrofit retrofit;
    public PeopleManagerCity peopleManagerCity;

    public static APICallManagerCity getCity() {
        if (instance == null) {
            synchronized (APICallManagerCity.class) {
                if (instance == null) {
                    instance = new APICallManagerCity();
                }
            }
        }
        return instance;

    }

    public APICallManagerCity() {
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
        this.peopleManagerCity = new PeopleManagerCity();
    }

    public static <T> T getServiceCity(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public class PeopleManagerCity {
        WeatherService service;

        public PeopleManagerCity() {
            this.service = getServiceCity(WeatherService.class);
        }

        public Call<WeatherList> getContactsCityDay(String s) {
            return service.getWeatherCity(s);
        }


    }
}
