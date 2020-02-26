package anhbm.nws.weatherapp.presentation.presenters;

import java.util.List;

import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.presentation.presenters.base.BasePresenter;

public interface AboutPresenter {
    void thanhpho(String thanhpho);

    void getRecyCity(List<ListAPI> listCityList);

    void icon(String icon);

    void nhietdoF(String F);

    void nhietdoC(Double C);
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
