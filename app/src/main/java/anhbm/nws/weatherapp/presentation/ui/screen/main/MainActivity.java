package anhbm.nws.weatherapp.presentation.ui.screen.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.application.GPSTracker;
import anhbm.nws.weatherapp.presentation.presenters.MainPresenter;
import anhbm.nws.weatherapp.presentation.ui.adapter.WeatherAdapter;
import anhbm.nws.weatherapp.presentation.ui.adapter.WeatherDayAdapter;
import anhbm.nws.weatherapp.presentation.ui.screen.BaseActivity;
import anhbm.nws.weatherapp.presentation.ui.screen.history.HistoryActivity;
import anhbm.nws.weatherapp.presentation.ui.screen.main.mvp.MainPresenterImpl;
import anhbm.nws.weatherapp.presentation.ui.screen.searchCity.AboutActivity;


public class MainActivity extends BaseActivity implements MainPresenter, BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private MainPresenterImpl presenter;
    private TextView tvThanhpho, tvNhietdo, tvNgay, tvUsAQI, tvonhiem, tvTieudeOnhiem;
    private RecyclerView recyNgay, recyList;
    private GPSTracker gpsTracker;
    private WeatherAdapter weatherListDayAdapter;
    private ImageView imageView;
    private WeatherDayAdapter weatherListAdapter;
    ///SharedPreferences
    private List<ListAPI> enums = new ArrayList<>();
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private int type_degree = 0;
    private String nhietdo;


    private static final String IS_DEGREE = "IS_DEGREE";
    private static final String IS_KELVIN = "IS_KELVIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showToastGPS();
        CheckInternetshowCaidat();
        gpsTracker = new GPSTracker(getApplicationContext());
        init();
        Managaer();
        enums = getValueFromPreference();
        initRecyclerView(enums);


    }

    private void init() {
        preferences = getSharedPreferences("key", MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();
        initLayout();
        initData();
        presenter = new MainPresenterImpl(this, gpsTracker, this);
    }

    private void initData() {
        boolean c = preferences.getBoolean(IS_DEGREE, true);
        boolean k = preferences.getBoolean(IS_KELVIN, false);
        if (c && !k) {
            type_degree = 0;
            
        } else if (!c && k) {
            type_degree = 1;
        }
    }

    private void initLayout() {
        tvThanhpho = findViewById(R.id.tv_city);
        tvNhietdo = findViewById(R.id.tv_temperature);
        tvNgay = findViewById(R.id.tv_title);
        recyNgay = findViewById(R.id.recyclerView);
        tvUsAQI = findViewById(R.id.tv_pollution_AQI);
        recyList = findViewById(R.id.recyclerviewDay);
        tvonhiem = findViewById(R.id.tv_pollution2);
        tvTieudeOnhiem = findViewById(R.id.tieude);
        imageView = findViewById(R.id.icon_onhiem);
        bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public void getRecyclerView(List<ListAPI> weatherListDays) {
        saveValueToPreference(weatherListDays);
        //      weatherListDayAdapter = new WeatherAdapter(this, weatherListDays, type_degree);
//        recyNgay.setAdapter(weatherListDayAdapter);
//        weatherListAdapter = new WeatherDayAdapter(MainActivity.this, weatherListDays, type_degree);
//        recyList.setAdapter(weatherListAdapter);

    }

    private void initRecyclerView(List<ListAPI> list) {
        ///hien thi du lieu list khi mat mang
        weatherListDayAdapter = new WeatherAdapter(this, list, type_degree);
        recyNgay.setAdapter(weatherListDayAdapter);
        weatherListAdapter = new WeatherDayAdapter(MainActivity.this, list, type_degree);
        recyList.setAdapter(weatherListAdapter);
        String thanhpho = preferences.getString("keyThanhpho", "");
        tvThanhpho.setText(thanhpho);
        nhietdo = preferences.getString("keynhietdo", "");
        tvNhietdo.setText(String.valueOf(nhietdo) + "ºC");
        Integer Onhiem = preferences.getInt("keyOnhiem", 1);
        tvUsAQI.setText(String.valueOf(Onhiem));
        String ngay = preferences.getString("keyngay", "");
        tvNgay.setText(ngay);
    }

    private void saveValueToPreference(List<ListAPI> list) {
        String json = gson.toJson(list);
        editor.putString("keyList", json);
        editor.commit();
    }

    private List<ListAPI> getValueFromPreference() {
        Type collectionType = new TypeToken<List<ListAPI>>() {
        }.getType();
        return gson.fromJson(preferences.getString("keyList", ""), collectionType);
    }

    private void Managaer() {
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyNgay.setLayoutManager(horizontalLayoutManagaer);
        LinearLayoutManager LayoutManagaer = new LinearLayoutManager(getApplicationContext());
        recyList.setLayoutManager(LayoutManagaer);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_bottomn_Left:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_bottomn_Right:
                nhietDoF();
                return true;
            case R.id.menu_history:
                Intent history = new Intent(this, HistoryActivity.class);
                startActivity(history);
                return true;
        }
        return false;

    }

    private void nhietDoF() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view1 = LayoutInflater.from(this).inflate(R.layout.dialog_f, null);
        builder.setView(view1);
        builder.setTitle("Chuyển Đổi ºC vs ºF");
        final AlertDialog dialog = builder.show();
        Button buttonC, buttonF;
        buttonC = dialog.findViewById(R.id.c);
        buttonF = dialog.findViewById(R.id.f);
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type_degree = 0;
                editor.putBoolean(IS_DEGREE, true);
                editor.putBoolean(IS_KELVIN, false);
                editor.commit();
                initRecyclerView(enums);
                String keyC = preferences.getString("keyC", "");
                tvNhietdo.setText(keyC + "ºC");
                dialog.dismiss();

            }
        });
        buttonF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type_degree = 1;
                editor.putBoolean(IS_DEGREE, false);
                editor.putBoolean(IS_KELVIN, true);
                editor.commit();
                initRecyclerView(enums);
                String keyF = preferences.getString("keyF", "");
                tvNhietdo.setText(keyF + "ºF");
                dialog.dismiss();
            }
        });

    }


    @Override
    public void thanhpho(String s) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvThanhpho.setTypeface(typeface);
        tvThanhpho.setText(s);
    }


    @Override
    public void nhietdo(double integer) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvNhietdo.setTypeface(typeface);
        String sub = String.valueOf(integer).substring(0, 2);
        tvNhietdo.setText(sub + "ºC");


    }

    @Override
    public void nhietC(double inC) {
        tvNhietdo.setText(String.valueOf(inC).substring(0, 2) + "ºC");
    }

    @Override
    public void nhietF(double inF) {
        tvNhietdo.setText(String.valueOf(inF).substring(0, 2) + "ºF");

    }

    @Override
    public void ngay(String ngay) {
        tvNgay.setText(ngay);
    }

    @Override
    public void usAQI(Integer usAQI) {
//        Integer integer = preferences.getInt("keyOnhiem", 0);
//        tvUsAQI.setText(integer + " US AQI");
        tvUsAQI.setText(String.valueOf(usAQI) + " US AQI");
        presenter.MucDoONhiem();
    }

    @Override
    public void AQI301() {
        tvUsAQI.setBackgroundColor(Color.parseColor("#990000"));
        tvonhiem.setText("Không Khí Đang Ở Mức Nguy hiểm");
        tvonhiem.setTextColor(Color.parseColor("#990000"));
        tvTieudeOnhiem.setTextColor(Color.parseColor("#990000"));
        imageView.setImageResource(R.mipmap.ic_onhiem_301);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "VGARAMB.TTF");
        Typeface type = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvonhiem.setTypeface(typeface);
        tvUsAQI.setTypeface(typeface);
        tvTieudeOnhiem.setTypeface(type);
    }

    @Override
    public void AQI201() {
        tvUsAQI.setBackgroundColor(Color.parseColor("#A2007C"));
        tvonhiem.setText("Không Khí Đang Ở Mức RẤt Ô Nhiễm");
        tvonhiem.setTextColor(Color.parseColor("#A2007C"));
        tvTieudeOnhiem.setTextColor(Color.parseColor("#A2007C"));
        imageView.setImageResource(R.mipmap.ic_onhiem_201);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "VGARAMB.TTF");
        Typeface type = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvonhiem.setTypeface(typeface);
        tvUsAQI.setTypeface(typeface);
        tvTieudeOnhiem.setTypeface(type);
    }

    @Override
    public void AQI151() {
        tvUsAQI.setBackgroundColor(Color.parseColor("#FF0000"));
        tvonhiem.setText("Không Khí Đang Ở Mức Ô Nhiễm");
        tvonhiem.setTextColor(Color.parseColor("#FF0000"));
        tvTieudeOnhiem.setTextColor(Color.parseColor("#FF0000"));
        imageView.setImageResource(R.mipmap.ic_onhiem_151);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "VGARAMB.TTF");
        Typeface type = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvonhiem.setTypeface(typeface);
        tvUsAQI.setTypeface(typeface);
        tvTieudeOnhiem.setTypeface(type);
    }

    @Override
    public void AQI101() {
        tvUsAQI.setBackgroundColor(Color.parseColor("#FF6600"));
        tvonhiem.setText("Không Khí Đang Ở Mức Không tốt cho người thuộc nhóm nhạy cảm");
        tvonhiem.setTextColor(Color.parseColor("#FF6600"));
        tvTieudeOnhiem.setTextColor(Color.parseColor("#FF6600"));
        imageView.setImageResource(R.mipmap.ic_onhiem_101);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "VGARAMB.TTF");
        Typeface type = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvonhiem.setTypeface(typeface);
        tvUsAQI.setTypeface(typeface);
        tvTieudeOnhiem.setTypeface(type);
    }

    @Override
    public void AQI51() {
        tvUsAQI.setBackgroundColor(Color.parseColor("#FFFF00"));
        tvonhiem.setText("Không Khí Đang Ở Mức Vừa Phải");
        tvonhiem.setTextColor(Color.parseColor("#FFFF00"));
        tvTieudeOnhiem.setTextColor(Color.parseColor("#FFFF00"));
        imageView.setImageResource(R.mipmap.ic_onhiem_51);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "VGARAMB.TTF");
        Typeface type = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvonhiem.setTypeface(typeface);
        tvUsAQI.setTypeface(typeface);
        tvTieudeOnhiem.setTypeface(type);
    }

    @Override
    public void AQI00() {
        tvUsAQI.setBackgroundColor(Color.parseColor("#00FF33"));
        tvonhiem.setText("Không Khí Đang Ở Mức Tốt");
        tvonhiem.setTextColor(Color.parseColor("#00FF33"));
        tvTieudeOnhiem.setTextColor(Color.parseColor("#00FF33"));
        imageView.setImageResource(R.mipmap.ic_onhiem_50);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "VGARAMB.TTF");
        Typeface type = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvonhiem.setTypeface(typeface);
        tvUsAQI.setTypeface(typeface);
        tvTieudeOnhiem.setTypeface(type);
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }


    /**
     * show WeatherResponse to UI
     */

}
