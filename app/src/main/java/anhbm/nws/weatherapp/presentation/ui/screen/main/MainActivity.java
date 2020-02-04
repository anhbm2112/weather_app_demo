package anhbm.nws.weatherapp.presentation.ui.screen.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;
import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.application.GPSTracker;
import anhbm.nws.weatherapp.presentation.presenters.MainPresenter;
import anhbm.nws.weatherapp.presentation.ui.adapter.WeatherAdapter;
import anhbm.nws.weatherapp.presentation.ui.adapter.WeatherDayAdapter;
import anhbm.nws.weatherapp.presentation.ui.screen.BaseActivity;
import anhbm.nws.weatherapp.presentation.ui.screen.about.AboutActivity;
import anhbm.nws.weatherapp.presentation.ui.screen.main.mvp.MainPresenterImpl;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainPresenter{
    private BottomNavigationView bottomNavigationView;
    private MainPresenterImpl presenter;
    private TextView tvThanhpho, tvNhietdo, tvNgay, tvUsAQI, tvUSmain, tvonhiem, tvCNmain, tvTieudeOnhiem;
    private RecyclerView recyNgay, recyList;
    GPSTracker gpsTracker;
    private WeatherAdapter weatherListDayAdapter;
    private ImageView imageView;
    private WeatherDayAdapter weatherListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showToastGPS();
        gpsTracker = new GPSTracker(getApplicationContext());
        init();
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyNgay.setLayoutManager(horizontalLayoutManagaer);
        LinearLayoutManager LayoutManagaer = new LinearLayoutManager(getApplicationContext());
        recyList.setLayoutManager(LayoutManagaer);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_bottomn_Left:
                        openActivityAbout();
                        return true;
                    case R.id.menu_bottomn_Right:
                        presenter.nhietDoF();
                        return true;
                }
                return false;
            }
        });

    }

    //        presenter.presentState(ViewState.LOAD_WEATHER);
//        presenter.presentState(ViewState.SHOW_WEATHER);
    private void init() {
        ButterKnife.bind(this);
        presenter = new MainPresenterImpl(this, gpsTracker, this);
//        model = new MainModel(this);
        initLayout();
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
    }

    @Override
    public void thanhpho(String s) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvThanhpho.setTypeface(typeface);
        tvThanhpho.setText(s);
    }

    @Override
    public void getRecyclerView(List<ListAPI> weatherListDays) {
        weatherListDayAdapter = new WeatherAdapter(this, weatherListDays);
        recyNgay.setAdapter(weatherListDayAdapter);
        weatherListAdapter = new WeatherDayAdapter(getApplicationContext(), weatherListDays);
        recyList.setAdapter(weatherListAdapter);
    }

    @Override
    public void nhietdo(double integer) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMonoBold.ttf");
        tvNhietdo.setTypeface(typeface);
        String sub = String.valueOf(integer).substring(0,2);
        tvNhietdo.setText(sub + "ºC");
    }

    @Override
    public void nhietC(double inC) {
        tvNhietdo.setText(inC + "ºC");
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


    private void openActivityAbout() {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
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
//    private void showPeople() {
    // show the data
    //recyclerView.getAdapter().notifyDataSetChanged();
//        presenter.presentState(AboutPresenter.AboutView.ViewState.IDLE);

//    @Override
//    public void USmain(String USmain) {
//
//    }

//    @Override
//    public void ToastGPS() {
////        mainPresenter.showToastGPS();
//    }


    //    @Override
//    protected void onResume() {
//        super.onResume();
//        presenter.resume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        presenter.pause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        presenter.destroy();
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return true;
//    }

    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    @Override
//    public void showProgress(boolean flag) {
//
//    }

//    @Override
//    public void showState(MainPresenter.MainView.ViewState viewState) {
//        switch (viewState) {
//            case IDLE:
//                showProgress(false);
//                break;
//            case LOADING:
//                showProgress(true);
//                break;
//            case SHOW_WEATHER:
//
//                showPeople();
//                break;
//            case OPEN_ABOUT:
//                openActivityAbout();
//                break;
//            case ERROR:
////                showToast(doRetrieveModel().getErrorMessage());
//                break;
//        }
//    }

//    @Override
//    public MainModel doRetrieveModel() {
//        return this.model;
//
//    }

//    @Override
//    public void thanhpho(String s) {
////        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
//        tvThanhpho.setText(s);
//    }

//    @Override
//    public void nhietdo(String nhietdo) {
//        tvNhietdo.setText(nhietdo+"ºC");
//    }
//
//    @Override
//    public void ngay(String ngay) {
//        tvNgay.setText(ngay);
//
//        tvNgay.setPaintFlags(tvNgay.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//    }
//
//    @Override
//    public void usAQI(String integer) {
//        tvUsAQI.setText(integer);
//    }
//
//    @Override
//    public void USmain(String USmain) {
//        tvUSmain.setText(USmain);
//    }


//    /**
//     * show WeatherResponse to UI
//     */
//    private void showPeople() {
//        // show the data
//        presenter.presentState(ViewState.IDLE);
//
//
//    }
//    }
}
