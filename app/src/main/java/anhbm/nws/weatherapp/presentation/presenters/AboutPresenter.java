package anhbm.nws.weatherapp.presentation.presenters;

import java.util.List;

import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.presentation.presenters.base.BasePresenter;

public interface AboutPresenter extends BasePresenter {
    void thanhpho(String thanhpho);

    void nhietdo(String integer);

    void getRecyCity(List<ListAPI> listCityList);

    void icon(String icon);
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
