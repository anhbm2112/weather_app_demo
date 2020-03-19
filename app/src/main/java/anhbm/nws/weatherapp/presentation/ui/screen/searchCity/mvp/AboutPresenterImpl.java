package anhbm.nws.weatherapp.presentation.ui.screen.searchCity.mvp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.api.APICallListener;
import anhbm.nws.weatherapp.api.weather.modelWeatherAPI.Weather;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.WeatherList;
import anhbm.nws.weatherapp.application.SQLiteWeather.SqlDatabase;
import anhbm.nws.weatherapp.domains.interactors.WeatherInteractor;
import anhbm.nws.weatherapp.presentation.presenters.AboutPresenter;
import anhbm.nws.weatherapp.presentation.ui.screen.history.mvp.HistoryModel;
import anhbm.nws.weatherapp.utils.Enums;

public class AboutPresenterImpl implements APICallListener {
    private WeatherInteractor weatherInteractor;
    private AboutPresenter mView;
    private Context mcontext;
    List<ListAPI> listCityList = new ArrayList<>();
    private SqlDatabase sqlDatabase;
    private String gio, ngay, dogio, tocdogio, doam, trangthai, icon;
    private double nhietdo, nhietdoF;
    private double s;
    private double s1;
    private HistoryModel historyModel = new HistoryModel();
    private static final String IS_DEGREE = "IS_DEGREE";
    private static final String IS_KELVIN = "IS_KELVIN";

    public AboutPresenterImpl(AboutPresenter mView, Context context) {
        this.mView = mView;
        this.mcontext = context;
        this.weatherInteractor = new WeatherInteractor(this);

    }

    public void Tim(String s) {
        weatherInteractor.callAPICity(s);
        historyModel.setThanhpho(s);
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
        mView.getRecyCitySearch(listCityList);
        String thanhpho = weatherCity.getCity().getName();
        String iconchinh = weatherCity.getList().get(0).getWeather().get(0).getIcon();
        s = listCityList.get(0).getMain().getTemp();
        s1 = listCityList.get(0).getMain().onConvertCelsiusToF(s);
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences("key", mcontext.MODE_PRIVATE);
        boolean c = sharedPreferences.getBoolean(IS_DEGREE, true);
        boolean k = sharedPreferences.getBoolean(IS_KELVIN, false);
        if (c && !k) {
            mView.nhietdoC(s);

        } else if (!c && k) {
            mView.nhietdoF(s1);

        }
        mView.icon(iconchinh);
        mView.thanhpho(thanhpho);
        insetLichSu(listCityList);
    }

    @Override
    public void onAPICallFailed(Enums.APIRoute route, Throwable throwable) {

    }


    public void insetLichSu(List<ListAPI> listCityList) {
        nhietdo = listCityList.get(0).getMain().getTemp();
        nhietdoF = listCityList.get(0).getMain().onConvertCelsiusToF(nhietdo);
        dogio = String.valueOf(listCityList.get(0).getWind().getDeg());
        tocdogio = String.valueOf(listCityList.get(0).getWind().getSpeed());
        doam = String.valueOf(listCityList.get(0).getMain().getHumidity());
        trangthai = listCityList.get(0).getWeather().get(0).getDescription();
        icon = listCityList.get(0).getWeather().get(0).getIcon();
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
            Toast.makeText(mcontext, R.string.ThemLichSu, Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(mcontext, R.string.ThemLichSuThatBai, Toast.LENGTH_LONG).show();
        }
    }



}
