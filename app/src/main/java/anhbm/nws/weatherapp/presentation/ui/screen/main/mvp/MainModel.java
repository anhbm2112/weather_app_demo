package anhbm.nws.weatherapp.presentation.ui.screen.main.mvp;

import android.content.Context;


import java.util.ArrayList;
import java.util.List;

import anhbm.nws.weatherapp.api.weather.Weather;
import anhbm.nws.weatherapp.domains.model.WeatherDomain;
import anhbm.nws.weatherapp.presentation.presenters.base.BaseViewModel;


public class MainModel extends BaseViewModel {
    public WeatherDomain peopleDomain;
    private List<Weather> listPeople;

    public MainModel(Context context) {
        super(context);
        this.peopleDomain = new WeatherDomain();
        this.listPeople = new ArrayList<>();
    }

    public List<Weather> getListPeople() {
        return listPeople;
    }

    public void setListPeople(List<Weather> listPeople) {
        this.listPeople = listPeople;
    }

    public void setListPeople() {
        getListPeople().clear();
//        for (Weather data : peopleDomain.getModel().getWeathers()) {
//            getListPeople().add(data);
//        }
    }
}
