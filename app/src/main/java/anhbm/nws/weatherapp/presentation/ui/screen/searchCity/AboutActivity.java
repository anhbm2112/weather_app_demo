package anhbm.nws.weatherapp.presentation.ui.screen.searchCity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.presentation.presenters.AboutPresenter;
import anhbm.nws.weatherapp.presentation.ui.adapter.WeatherCityAdapter;
import anhbm.nws.weatherapp.presentation.ui.screen.BaseActivity;
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
    private ArrayList<String> arrayList;
    private String thanhphoLichsu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        CheckInternetshowCaidat();
        init();
        arrayList = new ArrayList<>();
        arrayList.add("Ha noi");
        arrayList.add("Ho chi minh");
        arrayList.add("Tuyen Quang");
        arrayList.add("Yen Bai");
        arrayList.add("Ha Tinh");
        arrayList.add("Ninh Binh");
        arrayList.add("Phu Tho");
        arrayList.add("Bac Giang");
        arrayList.add("Hung Yen");
        arrayList.add("Hai Duong");
        arrayList.add("Bac Ninh");
        arrayList.add("Binh Duong");
        arrayList.add("Thanh Hoa");
        arrayList.add("thai nguyen");
        arrayList.add("Hue");
        arrayList.add("Can Tho");
        arrayList.add("Nam Dinh");
        arrayList.add("Bac lieu");
        arrayList.add("Haiphong");
        arrayList.add("Ben Tre");
        arrayList.add("Tay Ninh");
        arrayList.add("Yen Bai");
        arrayList.add("Vinh Long");
        arrayList.add("Ca mau");

        Intent intent = getIntent();
        thanhphoLichsu = intent.getStringExtra("timthanhpho");
        //spinner
//        ArrayAdapter<CharSequence> arrayAdapterSpin = ArrayAdapter.createFromResource(this, R.array.City,
//                android.R.layout.simple_spinner_item);

        ArrayAdapter<String> arrayAdapterSpin = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapterSpin);
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(thanhphoLichsu)) {
                spinner.setSelection(i);
            }
        }
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
    public void nhietdoF(String F) {
        tvNhietdo.setText(F.substring(0,2)+ "ºF");
    }
    @Override
    public void nhietdoC(Double C) {
        tvNhietdo.setText(String.valueOf(C).substring(0, 2) + "ºC");
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
