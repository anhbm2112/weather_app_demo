package anhbm.nws.weatherapp.presentation.ui.screen.history.mvp;

import android.content.Context;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.application.SQLiteWeather.SqlDatabase;
import anhbm.nws.weatherapp.domains.interactors.WeatherInteractor;
import anhbm.nws.weatherapp.presentation.presenters.HistoryPresenter;
import anhbm.nws.weatherapp.presentation.ui.screen.main.MainActivity;

public class HistoryPresenterImpl {
    private WeatherInteractor weatherInteractor;
    private HistoryPresenter mainPresenter;
    private Context mcontext;
    private List<ListAPI> listAPIS = new ArrayList<>();
    private SqlDatabase sqlDatabase;

    public HistoryPresenterImpl(HistoryPresenter mainPresenter, Context context) {
        this.mainPresenter = mainPresenter;
        this.mcontext = context;
    }

    public void backManchinh() {
        Intent intent = new Intent(mcontext, MainActivity.class);
        mcontext.startActivity(intent);
    }
}
