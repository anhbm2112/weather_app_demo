package anhbm.nws.weatherapp.presentation.ui.screen.main.mvp;

import java.util.List;

import anhbm.nws.weatherapp.api.APICallListener;
import anhbm.nws.weatherapp.api.BaseResponse;
import anhbm.nws.weatherapp.api.weather.WeatherResponse;
import anhbm.nws.weatherapp.domains.interactors.WeatherInteractor;
import anhbm.nws.weatherapp.presentation.presenters.MainPresenter;
import anhbm.nws.weatherapp.utils.Enums;

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

    @Override
    public void onAPICallSucceed(Enums.APIRoute route, BaseResponse responseModel) {
        switch (route) {
            case GET_WEATHER:
                view.doRetrieveModel().peopleDomain.setModel((WeatherResponse) responseModel);
                presentState(MainView.ViewState.SHOW_WEATHER);
                break;
        }
    }

    @Override
    public void onAPICallSucceed(Enums.APIRoute route, List<BaseResponse> responseModels) {

    }

    @Override
    public void onAPICallFailed(Enums.APIRoute route, Throwable throwable) {
        onError(throwable.getMessage());
    }
}
