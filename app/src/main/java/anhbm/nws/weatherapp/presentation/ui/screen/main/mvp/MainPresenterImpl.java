package anhbm.nws.weatherapp.presentation.ui.screen.main.mvp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import anhbm.nws.weatherapp.api.APICallListener;
import anhbm.nws.weatherapp.api.weather.modelWeatherAPI.Weather;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.WeatherList;
import anhbm.nws.weatherapp.application.GPSTracker;
import anhbm.nws.weatherapp.domains.interactors.WeatherInteractor;
import anhbm.nws.weatherapp.presentation.presenters.MainPresenter;
import anhbm.nws.weatherapp.utils.Enums;


public class MainPresenterImpl implements APICallListener {
    //    private MainView view;
    private WeatherInteractor peopleInteractor;
    private MainPresenter main;
    private Integer USaqi;
    private double nhietdo;
    private String ngaygio;
    private List<ListAPI> weatherListDays = new ArrayList<>();
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String nhietdof;
    private double doF, doC;
    private static final String IS_DEGREE = "IS_DEGREE";
    private static final String IS_KELVIN = "IS_KELVIN";

    public MainPresenterImpl(MainPresenter main, GPSTracker gpsTracker, Context context) {
        this.context = context;
        this.main = main;
        this.peopleInteractor = new WeatherInteractor(this);
        peopleInteractor.callAPIGetContacts(gpsTracker);
        peopleInteractor.callAPIlist(gpsTracker);
    }

    @Override
    public void onAPICallSucceed(Enums.APIRoute route, Weather weather) {
        USaqi = weather.getData().getCurrent().getPollution().getAqius();
        sharedPreferences = context.getSharedPreferences("key", context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("keyOnhiem", USaqi);
        editor.apply();
        Integer i = sharedPreferences.getInt("keyOnhiem", 1);
        if (i >= 301) {
            main.AQI301();
        } else if (i >= 201) {
            main.AQI201();
        } else if (i >= 151) {
            main.AQI151();
        } else if (i >= 101) {
            main.AQI101();
        } else if (i >= 51) {
            main.AQI51();
        } else {
            main.AQI00();
        }
        main.usAQI(i);
    }

    public void MucDoONhiem() {
        Integer integer = sharedPreferences.getInt("keyOnhiem", 1);
        if (integer >= 301) {
            main.AQI301();
        } else if (integer >= 201) {
            main.AQI201();
        } else if (integer >= 151) {
            main.AQI151();
        } else if (integer >= 101) {
            main.AQI101();
        } else if (integer >= 51) {
            main.AQI51();
        } else {
            main.AQI00();
        }
    }

    @Override
    public void onAPICallSucceedList(WeatherList weatherList) {
        weatherListDays = weatherList.getList();
        doC = Double.parseDouble(String.valueOf(weatherListDays.get(0).getMain().getTemp()));
        doF = Double.parseDouble(String.valueOf(weatherListDays.get(0).getMain().onConvertCelsiusToF(doC)));


        main.getRecyclerView(weatherListDays);
        String thanhpho = weatherList.getCity().getName();
        nhietdo = weatherList.getList().get(0).getMain().getTemp();
        ngaygio = weatherList.getList().get(0).getDtTxt();
///luu 1 string vao sharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("key", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("keyThanhpho", thanhpho);
        editor.putString("keynhietdo", String.valueOf(nhietdo).substring(0, 2));
        editor.putString("keyngay", ngaygio);
        editor.putString("keyC", String.valueOf(doC).substring(0, 2));
        editor.putString("keyF", String.valueOf(doF).substring(0, 2));
        editor.apply();
        main.ngay(ngaygio);
        main.thanhpho(thanhpho);

    }

    public void mainCvsF() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("key", context.MODE_PRIVATE);
        boolean s = sharedPreferences.getBoolean(IS_DEGREE, true);
        boolean k = sharedPreferences.getBoolean(IS_KELVIN, false);
        if (s && !k) {
            String keyC = sharedPreferences.getString("keyC", "");
            main.nhietdoC(keyC);
        } else if (!s && k) {
            String keyF = sharedPreferences.getString("keyF", "");
            main.nhietdoF(keyF);

        }
    }


    @Override
    public void onAPICallSucceedCity(WeatherList weatherCity) {

    }

    @Override
    public void onAPICallFailed(Enums.APIRoute route, Throwable throwable) {
//        onError(throwable.getMessage());
    }

}
