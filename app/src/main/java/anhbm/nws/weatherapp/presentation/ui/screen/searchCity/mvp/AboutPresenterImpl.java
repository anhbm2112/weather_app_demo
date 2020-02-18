package anhbm.nws.weatherapp.presentation.ui.screen.searchCity.mvp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import anhbm.nws.weatherapp.api.APICallListener;
import anhbm.nws.weatherapp.api.weather.modelWeatherAPI.Weather;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.WeatherList;
import anhbm.nws.weatherapp.application.SQLiteWeather.SqlDatabase;
import anhbm.nws.weatherapp.domains.interactors.WeatherInteractor;
import anhbm.nws.weatherapp.presentation.presenters.AboutPresenter;
import anhbm.nws.weatherapp.presentation.ui.screen.history.mvp.HistoryModel;
import anhbm.nws.weatherapp.presentation.ui.screen.main.MainActivity;
import anhbm.nws.weatherapp.utils.Enums;

public class AboutPresenterImpl implements APICallListener {
    WeatherInteractor weatherInteractor;
    AboutPresenter mView;
    Context mcontext;
    List<ListAPI> listCityList = new ArrayList<>();
    private SqlDatabase sqlDatabase;
    private String gio, ngay, dogio, tocdogio, doam, trangthai, icon;
    private double nhietdo;
    private HistoryModel historyModel = new HistoryModel();

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
        weatherInteractor.callAPICity(snhap);
        historyModel.setThanhpho(snhap);
    }

    public void insetLichSu(List<ListAPI> listCityList) {
        nhietdo = listCityList.get(0).getMain().getTemp();
        dogio = String.valueOf(listCityList.get(0).getWind().getDeg());
        tocdogio = String.valueOf(listCityList.get(0).getWind().getSpeed());
        doam = String.valueOf(listCityList.get(0).getMain().getHumidity());
        trangthai = listCityList.get(0).getWeather().get(0).getDescription();
        icon = listCityList.get(0).getWeather().get(0).getIcon();
        Log.e("LLLLL", trangthai);
        Calendar calendar = Calendar.getInstance();
        gio = DateFormat.getTimeInstance(DateFormat.MEDIUM).format(calendar.getTime());
        ngay = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        historyModel.setGiohientai(gio);
        historyModel.setNgayhientai(ngay);
        historyModel.setNhietDoTemp(nhietdo);
        historyModel.setDogioDeg(dogio);
        historyModel.setTocdogioSpeed(tocdogio);
        historyModel.setDoamHumidity(doam);
        historyModel.setTrangthaiDescription(trangthai);
        historyModel.setIconSql(icon);
        sqlDatabase = new SqlDatabase(mcontext);
        long kiemtra = sqlDatabase.insetHistory(historyModel);
        if (kiemtra > 0) {
            Toast.makeText(mcontext, "Thêm Thành Công Vào Lịch Sử Tìm Kiếm", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(mcontext, "Thêm Thất Bại", Toast.LENGTH_LONG).show();
        }
    }

    public void quaylai() {
        Intent intent = new Intent(mcontext, MainActivity.class);
        mcontext.startActivity(intent);
    }
}