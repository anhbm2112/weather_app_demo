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
        doC = Double.parseDouble(String.valueOf(weatherListDays.get(0).getMain().getTemp()).substring(0, 2));
        doF = Double.parseDouble(String.valueOf(weatherListDays.get(0).getMain().onConvertCelsiusToF(doC)).substring(0, 2));

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
        editor.putString("keyC", String.valueOf(doC));
        editor.putString("keyF", String.valueOf(doF));
        editor.apply();
        main.nhietdo(nhietdo);
        main.ngay(ngaygio);
        main.thanhpho(thanhpho);


    }

//    public void nhietDoFff( ) {
//       final double F = nhietdo * 1.8000 + 32.00;
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_f, null);
//        builder.setView(view1);
//        builder.setTitle("Chuyển Đổi ºC vs ºF");
//        final AlertDialog dialog = builder.show();
//        Button buttonC, buttonF;
//        buttonC = dialog.findViewById(R.id.c);
//        buttonF = dialog.findViewById(R.id.f);
//        buttonC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                main.nhietC(nhietdo);
//
//                dialog.dismiss();
//            }
//        });
//        buttonF.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    main.nhietF(F);
//                dialog.dismiss();
//            }
//        });
//
//    }

    @Override
    public void onAPICallSucceedCity(WeatherList weatherCity) {

    }

    @Override
    public void onAPICallFailed(Enums.APIRoute route, Throwable throwable) {
//        onError(throwable.getMessage());
    }

}
