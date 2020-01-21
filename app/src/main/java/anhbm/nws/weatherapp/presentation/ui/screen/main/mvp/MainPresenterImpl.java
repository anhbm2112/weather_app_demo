package anhbm.nws.weatherapp.presentation.ui.screen.main.mvp;

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
    WeatherInteractor peopleInteractor;
    private MainPresenter main;
    Integer USaqi;
    private List<ListAPI> weatherListDays = new ArrayList<ListAPI>();

    public MainPresenterImpl(MainPresenter main, GPSTracker gpsTracker) {
        this.main = main;
        this.peopleInteractor = new WeatherInteractor(this);
        peopleInteractor.callAPIGetContacts(gpsTracker);
        peopleInteractor.callAPIlist(gpsTracker);
    }


    @Override
    public void onAPICallSucceed(Enums.APIRoute route, Weather weather) {
        String thanhpho = weather.getData().getState();
        String nhietdo = String.valueOf(weather.getData().getCurrent().getWeatherCurrent().getTp());
        String ngaygio = weather.getData().getCurrent().getWeatherCurrent().getTs();
        USaqi = weather.getData().getCurrent().getPollution().getAqius();
        String USmainpr = weather.getData().getCurrent().getPollution().getMainus();
        main.nhietdo(nhietdo);
        main.thanhpho(thanhpho);
        main.ngay(ngaygio);
        main.usAQI(USaqi);
        //main.USmain(USmainpr);
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

    @Override
    public void onAPICallSucceedList(Enums.APIRoute route, WeatherList weatherList) {
//        ArrayList<WeatherListDay> ListDuBaoNgay =new ArrayList<>();
//        List<WeatherListDay> weather = weatherList.getList().get(0).getWeather();
//        for(int i=0;i<weatherListDays.size();i++){
//            weatherListDays.add((WeatherListDay) weatherList.getList().get(i).getWeather());
//        }

        weatherListDays = weatherList.getList();
        main.getRecyclerView(weatherListDays);


    }

    @Override
    public void onAPICallFailed(Enums.APIRoute route, Throwable throwable) {
//        onError(throwable.getMessage());
    }

}
