package anhbm.nws.weatherapp.presentation.presenters;

import java.util.List;

import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.presentation.presenters.base.BasePresenter;

public interface AboutPresenter {
    void thanhpho(String thanhpho);

    void getRecyCitySearch(List<ListAPI> listCityList);

    void icon(String icon);

    void nhietdoF(Double F);

    void nhietdoC(Double C);


}