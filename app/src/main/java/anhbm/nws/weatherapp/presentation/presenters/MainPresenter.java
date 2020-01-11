package anhbm.nws.weatherapp.presentation.presenters;


import anhbm.nws.weatherapp.api.weather.WeatherResponse;
import anhbm.nws.weatherapp.presentation.presenters.base.BasePresenter;
import anhbm.nws.weatherapp.presentation.presenters.base.BaseView;
import anhbm.nws.weatherapp.presentation.ui.screen.main.mvp.MainModel;

public interface MainPresenter extends BasePresenter {



    interface MainView extends BaseView {


        /**
         * This enum is used for determine the current state of this screen
         */
        enum ViewState {
            IDLE, LOADING,
            LOAD_WEATHER, SHOW_WEATHER,
            OPEN_ABOUT, ERROR
        }

        /**
         * This method is to show the current state of this screen
         *
         * @param viewState
         */
        void showState(ViewState viewState);

        /**
         * This function return the model that was belong to this screen
         *
         * @return
         */
        MainModel doRetrieveModel();
        void thanhpho(String s);
        void nhietdo(String integer);
        void ngay(String ngay);
        void usAQI(String integer);

    }

    /**
     * This method is used for presenting the current state of this screen
     *
     * @param state
     */
    void presentState(MainView.ViewState state);


}
