package anhbm.nws.weatherapp.api;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;

import anhbm.nws.weatherapp.api.weather.WeatherResponse;
import anhbm.nws.weatherapp.api.weather.WeatherService;
import anhbm.nws.weatherapp.utils.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APICallManager {
    public String endpoint = Constants.Path.DEFAULT_URL_API_PRODUCTION;
    public static APICallManager instance;
    private static Retrofit retrofit;

    public PeopleManager peopleManager;

    /**
     * singleton class instance
     *
     * @return APICallManager
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

        public Call<WeatherResponse> getContacts() {
            return service.getPeople();
        }
    }
}
