package anhbm.nws.weatherapp.presentation.ui.screen.searchCity.mvp;

import android.content.Context;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import anhbm.nws.weatherapp.api.APICallListener;
import anhbm.nws.weatherapp.api.weather.modelWeatherAPI.Weather;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.WeatherList;
import anhbm.nws.weatherapp.domains.interactors.WeatherInteractor;
import anhbm.nws.weatherapp.presentation.presenters.AboutPresenter;
import anhbm.nws.weatherapp.utils.Enums;

public class AboutPresenterImpl implements APICallListener {
    WeatherInteractor weatherInteractor;
    AboutPresenter mView;
    Context mcontext;
    List<ListAPI> listCityList = new ArrayList<>();

    public AboutPresenterImpl(AboutPresenter mView, Context context) {
        this.mView = mView;
        this.mcontext = context;
        this.weatherInteractor = new WeatherInteractor(this);
    }

    @Override
    public void onAPICallSucceed(Enums.APIRoute route, Weather weather) {
    }

    @Override
    public void onAPICallSucceedList(WeatherList weatherList) {

    }

    @Override
    public void onAPICallSucceedCity(WeatherList weatherCity) {
        listCityList = weatherCity.getList();
        mView.getRecyCity(listCityList);
        String thanhpho = weatherCity.getCity().getName();
        String nhietdo = String.valueOf(weatherCity.getList().get(0).getMain().getTemp()).substring(0, 2);
        String iconchinh = weatherCity.getList().get(0).getWeather().get(0).getIcon();
        mView.icon(iconchinh);
        mView.thanhpho(thanhpho);
        mView.nhietdo(nhietdo);
    }

    @Override
    public void onAPICallFailed(Enums.APIRoute route, Throwable throwable) {

    }

    public void Tim(String snhap) {
        if (snhap.isEmpty()) {
            Toast.makeText(mcontext, "Xin Mời Nhập Thành Phố", Toast.LENGTH_LONG).show();
        } else {
            weatherInteractor.callAPICity(snhap);
            Toast.makeText(mcontext, snhap, Toast.LENGTH_LONG).show();
        }
    }

}
