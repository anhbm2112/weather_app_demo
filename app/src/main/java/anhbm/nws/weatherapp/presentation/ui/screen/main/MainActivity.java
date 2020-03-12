package anhbm.nws.weatherapp.presentation.ui.screen.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import anhbm.nws.weatherapp.presentation.ui.adapter.WeatherHorizontalAdapter;
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
    private WeatherHorizontalAdapter weatherListDayAdapter;
    private ImageView imageView;
    private WeatherDayAdapter weatherListAdapter;
    ///SharedPreferences
    private List<ListAPI> enums = new ArrayList<>();
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private int type_degree = 0;
    //    String oC, oF;
    private static final String IS_DEGREE = "IS_DEGREE";
    private static final String IS_KELVIN = "IS_KELVIN";
private LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showToastGPS();
        checkLocationPermission();
        CheckInternetshowCaidat();

        gpsTracker = new GPSTracker(getApplicationContext());
        init();
        enums = getValueFromPreference();
        Managaer();
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
//        oC = String.valueOf(weatherListDays.get(0).getMain().getTemp()).substring(0, 2);
//        oF = String.valueOf(weatherListDays.get(0).getMain().onConvertCelsiusToF(Double.parseDouble(oC))).substring(0, 2);
        saveValueToPreference(weatherListDays);
//        weatherListDayAdapter = new WeatherHorizontalAdapter(this, weatherListDays, type_degree);
//        recyNgay.setAdapter(weatherListDayAdapter);
//        weatherListAdapter = new WeatherDayAdapter(MainActivity.this, weatherListDays, type_degree);
//        recyList.setAdapter(weatherListAdapter);

    }

    private void initRecyclerView(List<ListAPI> list) {
        ///hien thi du lieu list khi mat mang
        weatherListDayAdapter = new WeatherHorizontalAdapter(this, list, type_degree);
        recyNgay.setAdapter(weatherListDayAdapter);
        weatherListAdapter = new WeatherDayAdapter(MainActivity.this, list, type_degree);
        recyList.setAdapter(weatherListAdapter);
        String thanhpho = preferences.getString("keyThanhpho", "");
        tvThanhpho.setText(thanhpho);
        String ngay = preferences.getString("keyngay", "");
        tvNgay.setText(ngay);
        presenter.mainCvsF();
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

        Integer integer = preferences.getInt("keyOnhiem", 1);
        tvUsAQI.setText(String.valueOf(integer));
        if (integer >= 301) {
            tvUsAQI.setBackgroundResource(R.color.MauNguyHiem);
            tvonhiem.setText(R.string.NguyHiem);
            tvonhiem.setTextColor(getResources().getColor(R.color.MauNguyHiem));
            tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauNguyHiem));
            imageView.setImageResource(R.mipmap.ic_onhiem_301);
            Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
            Typeface type = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
            tvonhiem.setTypeface(typeface);
            tvUsAQI.setTypeface(typeface);
            tvTieudeOnhiem.setTypeface(type);
///hien thi do o nhiem khi tat mang
        } else if (integer >= 201) {
            tvUsAQI.setBackgroundResource(R.color.MauRatONhiem);
            tvonhiem.setText(R.string.RatONhiem);
            tvonhiem.setTextColor(getResources().getColor(R.color.MauRatONhiem));
            tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauRatONhiem));
            imageView.setImageResource(R.mipmap.ic_onhiem_201);
            Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
            Typeface type = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
            tvonhiem.setTypeface(typeface);
            tvUsAQI.setTypeface(typeface);
            tvTieudeOnhiem.setTypeface(type);
        } else if (integer >= 151) {
            tvUsAQI.setBackgroundResource(R.color.MauOnhiem);
            tvonhiem.setText(R.string.Onhiem);
            tvonhiem.setTextColor(getResources().getColor(R.color.MauOnhiem));
            tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauOnhiem));
            imageView.setImageResource(R.mipmap.ic_onhiem_151);
            Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
            Typeface type = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
            tvonhiem.setTypeface(typeface);
            tvUsAQI.setTypeface(typeface);
            tvTieudeOnhiem.setTypeface(type);
        } else if (integer >= 101) {
            tvUsAQI.setBackgroundResource(R.color.MauNhayCam);
            tvonhiem.setText(R.string.NhayCam);
            tvonhiem.setTextColor(getResources().getColor(R.color.MauNhayCam));
            tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauNhayCam));
            imageView.setImageResource(R.mipmap.ic_onhiem_101);
            Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
            Typeface type = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
            tvonhiem.setTypeface(typeface);
            tvUsAQI.setTypeface(typeface);
            tvTieudeOnhiem.setTypeface(type);
        } else if (integer >= 51) {
            tvUsAQI.setBackgroundResource(R.color.MauVuaPhai);
            tvonhiem.setText(R.string.VuaPhai);
            tvonhiem.setTextColor(getResources().getColor(R.color.MauVuaPhai));
            tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauVuaPhai));
            imageView.setImageResource(R.mipmap.ic_onhiem_51);
            Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
            Typeface type = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
            tvonhiem.setTypeface(typeface);
            tvUsAQI.setTypeface(typeface);
            tvTieudeOnhiem.setTypeface(type);
        } else {
            tvUsAQI.setBackgroundResource(R.color.MauTot);
            tvonhiem.setText(R.string.Tot);
            tvonhiem.setTextColor(getResources().getColor(R.color.MauTot));
            tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauTot));
            imageView.setImageResource(R.mipmap.ic_onhiem_50);
            Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
            Typeface type = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
            tvonhiem.setTypeface(typeface);
            tvUsAQI.setTypeface(typeface);
            tvTieudeOnhiem.setTypeface(type);
        }

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
        View view1 = LayoutInflater.from(this).inflate(R.layout.c_f_dialog, null);
        builder.setView(view1);
        builder.setTitle(R.string.TitleFvsC);
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
                dialog.dismiss();
            }
        });

    }

    @Override
    public void nhietdoC(String C) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvNhietdo.setTypeface(typeface);
        tvNhietdo.setText(C + "ºC");
    }

    @Override
    public void nhietdoF(String F) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvNhietdo.setTypeface(typeface);
        tvNhietdo.setText(F + "ºF");
    }

    @Override
    public void thanhpho(String s) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvThanhpho.setTypeface(typeface);
        tvThanhpho.setText(s);
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

    }

    @Override
    public void AQI301() {
        tvUsAQI.setBackgroundResource(R.color.MauNguyHiem);
        tvonhiem.setText(R.string.NguyHiem);
        tvonhiem.setTextColor(getResources().getColor(R.color.MauNguyHiem));
        tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauNguyHiem));
        imageView.setImageResource(R.mipmap.ic_onhiem_301);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        Typeface type = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvonhiem.setTypeface(typeface);
        tvUsAQI.setTypeface(typeface);
        tvTieudeOnhiem.setTypeface(type);
    }

    @Override
    public void AQI201() {
        tvUsAQI.setBackgroundResource(R.color.MauRatONhiem);
        tvonhiem.setText(R.string.RatONhiem);
        tvonhiem.setTextColor(getResources().getColor(R.color.MauRatONhiem));
        tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauRatONhiem));
        imageView.setImageResource(R.mipmap.ic_onhiem_201);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        Typeface type = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvonhiem.setTypeface(typeface);
        tvUsAQI.setTypeface(typeface);
        tvTieudeOnhiem.setTypeface(type);
    }

    @Override
    public void AQI151() {
        tvUsAQI.setBackgroundResource(R.color.MauOnhiem);
        tvonhiem.setText(R.string.Onhiem);
        tvonhiem.setTextColor(getResources().getColor(R.color.MauOnhiem));
        tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauOnhiem));
        imageView.setImageResource(R.mipmap.ic_onhiem_151);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        Typeface type = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvonhiem.setTypeface(typeface);
        tvUsAQI.setTypeface(typeface);
        tvTieudeOnhiem.setTypeface(type);
    }

    @Override
    public void AQI101() {
        tvUsAQI.setBackgroundResource(R.color.MauNhayCam);
        tvonhiem.setText(R.string.NhayCam);
        tvonhiem.setTextColor(getResources().getColor(R.color.MauNhayCam));
        tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauNhayCam));
        imageView.setImageResource(R.mipmap.ic_onhiem_101);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        Typeface type = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvonhiem.setTypeface(typeface);
        tvUsAQI.setTypeface(typeface);
        tvTieudeOnhiem.setTypeface(type);
    }

    @Override
    public void AQI51() {
        tvUsAQI.setBackgroundResource(R.color.MauVuaPhai);
        tvonhiem.setText(R.string.VuaPhai);
        tvonhiem.setTextColor(getResources().getColor(R.color.MauVuaPhai));
        tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauVuaPhai));
        imageView.setImageResource(R.mipmap.ic_onhiem_51);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        Typeface type = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvonhiem.setTypeface(typeface);
        tvUsAQI.setTypeface(typeface);
        tvTieudeOnhiem.setTypeface(type);
    }

    @Override
    public void AQI00() {
        tvUsAQI.setBackgroundResource(R.color.MauTot);
        tvonhiem.setText(R.string.Tot);
        tvonhiem.setTextColor(getResources().getColor(R.color.MauTot));
        tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauTot));
        imageView.setImageResource(R.mipmap.ic_onhiem_50);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
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
