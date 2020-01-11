package anhbm.nws.weatherapp.presentation.ui.screen.main.mvp;

import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ResponseCache;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import anhbm.nws.weatherapp.api.APICallListener;
import anhbm.nws.weatherapp.api.BaseResponse;
import anhbm.nws.weatherapp.api.weather.modelWeatherAPI.Weather;
import anhbm.nws.weatherapp.domains.interactors.WeatherInteractor;
import anhbm.nws.weatherapp.presentation.presenters.MainPresenter;
import anhbm.nws.weatherapp.utils.Enums;
import okhttp3.Response;

public class MainPresenterImpl implements MainPresenter, APICallListener {
    private MainView view;
    private WeatherInteractor peopleInteractor;


    public MainPresenterImpl(MainView view) {
        this.view = view;
        this.peopleInteractor = new WeatherInteractor(this);
    }


    @Override
    public void presentState(MainView.ViewState state) {
        switch (state) {
            case IDLE:
                view.showState(MainView.ViewState.IDLE);
                break;
            case LOADING:
                view.showState(MainView.ViewState.LOADING);
                break;
            case LOAD_WEATHER:
                presentState(MainView.ViewState.LOADING);
                peopleInteractor.callAPIGetContacts();
                break;
            case SHOW_WEATHER:
                // set API response to model
                view.doRetrieveModel().setListPeople();
                view.showState(MainView.ViewState.SHOW_WEATHER);
                break;
            case OPEN_ABOUT:
                view.showState(MainView.ViewState.OPEN_ABOUT);
                break;
            case ERROR:
                view.showState(MainView.ViewState.ERROR);
                break;
        }


    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {
        view.doRetrieveModel().setErrorMessage(message);
        presentState(MainView.ViewState.IDLE);
        presentState(MainView.ViewState.ERROR);
    }


//    @Override
//    public void onAPICallSucceed(Enums.APIRoute route, WeatherResponse weatherResponse) {
//
//        WeatherForecasts resource = weatherResponse.getWeathers();
//        //String text = resource.getTs();
//        //  Integer pr = resource.getPr();
//
//        view.thanhpho(resource.getTs());
//        view.nhietdo(resource.getTp());

//        switch (route) {
//            case GET_WEATHER:
//                view.doRetrieveModel().peopleDomain.setModel((WeatherResponse) weatherResponse);
//                presentState(MainView.ViewState.SHOW_WEATHER);
//                break;
//        }
//
//        try {
//            JSONObject jsonObject = new JSONObject(String.valueOf(weatherResponse.getWeathers()));
//            Integer nhietdo = jsonObject.getInt(resource.getTp().toString());
//            view.nhietdo(nhietdo);
//            Log.e("HIEN THI DU LIEU", nhietdo.toString());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


//    }

    @Override
    public void onAPICallSucceed(Enums.APIRoute route, Weather weather) {
        String thanhpho = weather.getData().getState();
        String nhietdo = String.valueOf(weather.getData().getCurrent().getWeatherCurrent().getTp() + "ºC");
        String ngaygio = weather.getData().getCurrent().getWeatherCurrent().getTs();
        String USaqi = weather.getData().getCurrent().getPollution().getAqius().toString();
        view.nhietdo(nhietdo);
        view.thanhpho(thanhpho);
        view.ngay(ngaygio);
        view.usAQI(USaqi);
//        try {
//            JSONObject jsonObject = new JSONObject(String.valueOf(weather.getData().getCurrent().getWeatherCurrent()));
//            String ngay = jsonObject.getString(ngaygio);
//            long l = Long.valueOf(ngay);
//            Date date = new Date(l * 1000L);
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE MMMM dd,yyyy");
//            String s = simpleDateFormat.format(date);
//            view.ngay(s);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


    }

    @Override
    public void onAPICallSucceedList(Enums.APIRoute route, List<BaseResponse> responseModels) {

    }

    @Override
    public void onAPICallFailed(Enums.APIRoute route, Throwable throwable) {
        onError(throwable.getMessage());
    }
}
