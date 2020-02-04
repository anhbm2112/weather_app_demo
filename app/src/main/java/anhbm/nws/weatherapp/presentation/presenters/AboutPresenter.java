package anhbm.nws.weatherapp.presentation.presenters;

import java.util.List;

import anhbm.nws.weatherapp.api.weather.modelWeatherCity.ListCity;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.presentation.presenters.base.BasePresenter;
import anhbm.nws.weatherapp.presentation.presenters.base.BaseView;
import anhbm.nws.weatherapp.presentation.ui.screen.about.mvp.AboutModel;

public interface AboutPresenter extends BasePresenter {
    void thanhpho(String thanhpho);

    void nhietdo(String integer);

    void getRecyCity(List<ListAPI> listCityList);

//    interface AboutView extends BaseView {
//        enum ViewState {
//            IDLE, LOADING, SHOW_ABOUT, ERROR
//        }
//
//        void showState(ViewState state);
//
//        AboutModel doRetrieveModel();
//    }
//
//    void presentState(AboutView.ViewState state);
}
