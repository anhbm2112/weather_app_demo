package anhbm.nws.weatherapp.presentation.ui.screen.main.mvp;


import android.content.Context;
import java.util.List;
import anhbm.nws.weatherapp.api.APICallListener;
import anhbm.nws.weatherapp.api.BaseResponse;
import anhbm.nws.weatherapp.api.weather.modelWeatherAPI.Weather;
import anhbm.nws.weatherapp.domains.interactors.WeatherInteractor;
import anhbm.nws.weatherapp.presentation.presenters.MainPresenter;
import anhbm.nws.weatherapp.utils.Enums;


public class MainPresenterImpl implements APICallListener {
    //    private MainView view;
    WeatherInteractor peopleInteractor;
    private MainPresenter main;

    Context context;

    public MainPresenterImpl(MainPresenter main) {
        this.main = main;
        double lat = 1.0;
        double lon = 1.0;
        this.peopleInteractor = new WeatherInteractor(this);
        peopleInteractor.callAPIGetContacts(lat, lon);
    }




    @Override
    public void onAPICallSucceed(Enums.APIRoute route, Weather weather) {
        String thanhpho = weather.getData().getState();
        String nhietdo = String.valueOf(weather.getData().getCurrent().getWeatherCurrent().getTp());
        String ngaygio = weather.getData().getCurrent().getWeatherCurrent().getTs();
        String USaqi = weather.getData().getCurrent().getPollution().getAqius().toString();
        String USmainpr = weather.getData().getCurrent().getPollution().getMainus();

        main.nhietdo(nhietdo);
        main.thanhpho(thanhpho);
        main.ngay(ngaygio);
        main.usAQI(USaqi);
        //main.USmain(USmainpr);
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
