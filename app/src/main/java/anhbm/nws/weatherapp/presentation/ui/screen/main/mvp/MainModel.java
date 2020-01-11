package anhbm.nws.weatherapp.presentation.ui.screen.main.mvp;

import android.content.Context;
import anhbm.nws.weatherapp.api.weather.modelWeatherAPI.Weather;
import anhbm.nws.weatherapp.domains.model.WeatherDomain;
import anhbm.nws.weatherapp.presentation.presenters.base.BaseViewModel;


public class MainModel extends BaseViewModel {
    public WeatherDomain peopleDomain;
    private Weather listPeople;

    public MainModel(Context context) {
        super(context);
        this.peopleDomain = new WeatherDomain();

    }

    public Weather getListPeople() {
        return listPeople;
    }

    public void setListPeople(Weather listPeople) {
        this.listPeople = listPeople;
    }

    public void setListPeople() {
        getListPeople();
//        for (WeatherData data : peopleDomain.getModel().getWeathers()) {
//            getListPeople().add(data);
//        }
    }
}
