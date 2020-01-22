package anhbm.nws.weatherapp.presentation.ui.screen.main.mvp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import anhbm.nws.weatherapp.R;
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
    WeatherInteractor peopleInteractor;
    private MainPresenter main;
    Integer USaqi;
    Integer nhietdo;
    private List<ListAPI> weatherListDays = new ArrayList<>();
    Context context;

    public MainPresenterImpl(MainPresenter main, GPSTracker gpsTracker, Context context) {
        this.context = context;
        this.main = main;
        this.peopleInteractor = new WeatherInteractor(this);
        peopleInteractor.callAPIGetContacts(gpsTracker);
        peopleInteractor.callAPIlist(gpsTracker);
    }


    @Override
    public void onAPICallSucceed(Enums.APIRoute route, Weather weather) {
        String thanhpho = weather.getData().getState();
        nhietdo = weather.getData().getCurrent().getWeatherCurrent().getTp();
        String ngaygio = weather.getData().getCurrent().getWeatherCurrent().getTs();
        USaqi = weather.getData().getCurrent().getPollution().getAqius();
        main.nhietdo(nhietdo);
        main.thanhpho(thanhpho);
        main.ngay(ngaygio);
        main.usAQI(USaqi);
    }

    public void MucDoONhiem() {
        if (USaqi >= 301) {
            main.AQI301();
        } else if (USaqi >= 201) {
            main.AQI201();
        } else if (USaqi >= 151) {
            main.AQI151();
        } else if (USaqi >= 101) {
            main.AQI101();
        } else if (USaqi >= 51) {
            main.AQI51();
        } else {
            main.AQI00();
        }
    }

    public void nhietDoF() {
        final double F = nhietdo * 1.8000 + 32.00;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_f, null);
        builder.setView(view1);
        builder.setTitle("Chuyển Đổi ºC vs ºF");
        final AlertDialog dialog = builder.show();
        Button buttonC, buttonF;
        buttonC = dialog.findViewById(R.id.c);
        buttonF = dialog.findViewById(R.id.f);
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               main.nhietC(nhietdo);
                dialog.dismiss();
            }
        });
        buttonF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.nhietF(F);
                dialog.dismiss();
            }
        });


    }

    @Override
    public void onAPICallSucceedList(Enums.APIRoute route, WeatherList weatherList) {
        weatherListDays = weatherList.getList();
        main.getRecyclerView(weatherListDays);
    }

    @Override
    public void onAPICallFailed(Enums.APIRoute route, Throwable throwable) {
//        onError(throwable.getMessage());
    }

}
