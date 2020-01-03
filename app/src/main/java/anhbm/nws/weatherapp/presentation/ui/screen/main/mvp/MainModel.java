package anhbm.nws.weatherapp.presentation.ui.screen.main.mvp;

import android.content.Context;


import java.util.ArrayList;
import java.util.List;

import anhbm.nws.weatherapp.api.weather.example.Weather;
import anhbm.nws.weatherapp.api.weather.example.WeatherForecasts;
import anhbm.nws.weatherapp.domains.model.WeatherDomain;
import anhbm.nws.weatherapp.presentation.presenters.base.BaseViewModel;


public class MainModel extends BaseViewModel {
    public WeatherDomain peopleDomain;
    private List<WeatherForecasts> listPeople;

    public MainModel(Context context) {
        super(context);
        this.peopleDomain = new WeatherDomain();
        this.listPeople = new ArrayList<>();
    }

    public List<WeatherForecasts> getListPeople() {
        return listPeople;
    }

    public void setListPeople(List<WeatherForecasts> listPeople) {
        this.listPeople = listPeople;
    }

    public void setListPeople() {
        getListPeople().clear();
//        for (Weather data : peopleDomain.getModel().getWeathers()) {
//            getListPeople().add(data);
//        }
    }
}
