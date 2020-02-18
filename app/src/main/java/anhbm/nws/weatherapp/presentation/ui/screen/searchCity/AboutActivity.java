package anhbm.nws.weatherapp.presentation.ui.screen.searchCity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.City;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.application.SQLiteWeather.SqlDatabase;
import anhbm.nws.weatherapp.presentation.presenters.AboutPresenter;
import anhbm.nws.weatherapp.presentation.ui.adapter.WeatherCityAdapter;
import anhbm.nws.weatherapp.presentation.ui.screen.BaseActivity;
import anhbm.nws.weatherapp.presentation.ui.screen.history.mvp.HistoryModel;
import anhbm.nws.weatherapp.presentation.ui.screen.searchCity.mvp.AboutPresenterImpl;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity implements AboutPresenter, AdapterView.OnItemSelectedListener, View.OnClickListener {
    private AboutPresenterImpl aboutPresenter;
    private TextView tvThanhpho, tvNhietdo, tvNgayCity;
    private WeatherCityAdapter weatherCityAdapter;
    private RecyclerView recyCity;
    private ImageView imageView;
    private Spinner spinner;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        CheckInternetshowCaidat();
        init();
        Intent intent = getIntent();
        String thanhphoLichsu = intent.getStringExtra("timthanhpho");

        //spinner
        ArrayAdapter<CharSequence> arrayAdapterSpin = ArrayAdapter.createFromResource(this, R.array.City,
                android.R.layout.simple_spinner_item);
        arrayAdapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapterSpin);
///recy
        LinearLayoutManager LayoutManagaer = new LinearLayoutManager(getApplicationContext());
        recyCity.setLayoutManager(LayoutManagaer);
        initLayout();
    }

    private void initLayout() {
        ButterKnife.bind(AboutActivity.this);
        spinner.setOnItemSelectedListener(this);
        back.setOnClickListener(this);
    }

    private void init() {
        aboutPresenter = new AboutPresenterImpl(this, this);
        tvThanhpho = findViewById(R.id.tv_city_manCity);
        tvNhietdo = findViewById(R.id.tv_temperature_mancity);
        tvNgayCity = findViewById(R.id.tv_ngaycity);
        recyCity = findViewById(R.id.recyclerViewCity);
        imageView = findViewById(R.id.imgIconchinh);
        spinner = findViewById(R.id.spin);
        back = findViewById(R.id.back_timkiem);
    }

    @Override
    public void thanhpho(String thanhpho) {
        tvThanhpho.setText(thanhpho);
    }

    @Override
    public void nhietdo(String integer) {
        tvNhietdo.setText(integer + "ºC");
    }

    @Override
    public void getRecyCity(List<ListAPI> listCityList) {
        aboutPresenter.insetLichSu(listCityList);
        weatherCityAdapter = new WeatherCityAdapter(listCityList, this);
        recyCity.setAdapter(weatherCityAdapter);
    }

    @Override
    public void icon(String icon) {
        Picasso.with(this).load("http://api.openweathermap.org/img/w/" + icon + ".png").into(imageView);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String thanhpho = adapterView.getItemAtPosition(i).toString();
        aboutPresenter.Tim(thanhpho);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_timkiem) {
            aboutPresenter.quaylai();
        }
    }
}
