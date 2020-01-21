package anhbm.nws.weatherapp.presentation.ui.screen.main.mvp;


import android.content.Context;
import android.graphics.Color;

import java.util.List;

import anhbm.nws.weatherapp.api.APICallListener;
import anhbm.nws.weatherapp.api.BaseResponse;
import anhbm.nws.weatherapp.api.weather.modelWeatherAPI.Weather;
import anhbm.nws.weatherapp.application.GPSTracker;
import anhbm.nws.weatherapp.domains.interactors.WeatherInteractor;
import anhbm.nws.weatherapp.presentation.presenters.MainPresenter;
import anhbm.nws.weatherapp.utils.Enums;


public class MainPresenterImpl implements APICallListener {
    //    private MainView view;
    WeatherInteractor peopleInteractor;
    private MainPresenter main;
    Integer USaqi;

    public MainPresenterImpl(MainPresenter main, GPSTracker gpsTracker) {
        this.main = main;
        this.peopleInteractor = new WeatherInteractor(this);
        peopleInteractor.callAPIGetContacts(gpsTracker);
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
    public void onAPICallSucceedList(Enums.APIRoute route, List<BaseResponse> responseModels) {

    }

    @Override
    public void onAPICallFailed(Enums.APIRoute route, Throwable throwable) {
//        onError(throwable.getMessage());
    }


//    public MainPresenterImpl(MainView view) {
//        this.view = view;
//        this.peopleInteractor = new WeatherInteractor(this);
//    }

//    @Override
//    public void presentState(MainView.ViewState state) {
//        switch (state) {
//            case IDLE:
//                view.showState(MainView.ViewState.IDLE);
//                break;
//            case LOADING:
//                view.showState(MainView.ViewState.LOADING);
//                break;
//            case LOAD_WEATHER:
//                presentState(MainView.ViewState.LOADING);
//                peopleInteractor.callAPIGetContacts();
//                break;
//            case SHOW_WEATHER:
//                // set API response to model
////                view.doRetrieveModel().setListPeople();
//                view.showState(MainView.ViewState.SHOW_WEATHER);
//                break;
//            case OPEN_ABOUT:
//                view.showState(MainView.ViewState.OPEN_ABOUT);
//                break;
//            case ERROR:
//                view.showState(MainView.ViewState.ERROR);
//                break;
//        }
//
//
//    }
//
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void pause() {
//
//    }

//    @Override
//    public void stop() {
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//
//    @Override
//    public void onError(String message) {
////        view.doRetrieveModel().setErrorMessage(message);
//        presentState(MainView.ViewState.IDLE);
//        presentState(MainView.ViewState.ERROR);
//    }


}
