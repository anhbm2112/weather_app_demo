package anhbm.nws.weatherapp.presentation.ui.screen.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.application.GPSTracker;
import anhbm.nws.weatherapp.presentation.presenters.MainPresenter;
import anhbm.nws.weatherapp.presentation.ui.screen.BaseActivity;
import anhbm.nws.weatherapp.presentation.ui.screen.about.AboutActivity;
import anhbm.nws.weatherapp.presentation.ui.screen.main.mvp.MainModel;
import anhbm.nws.weatherapp.presentation.ui.screen.main.mvp.MainPresenterImpl;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainPresenter {
    private MainPresenterImpl presenter;
    private MainModel model;
    private TextView tvThanhpho, tvNhietdo, tvNgay, tvUsAQI, tvUSmain, tvonhiem, tvCNmain, tvTieudeOnhiem;
    private RecyclerView recyGio, recyNgay;
    private LinearLayout linearLayout;
    GPSTracker gpsTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showToastGPS();
        gpsTracker = new GPSTracker(getApplicationContext());
        init();
//        String sd = String.valueOf(gpsTracker.getLatitude());
//        String sa = String.valueOf(gpsTracker.getLongtitude());
//        tvCNaqi.setText(sd);
//        tvCNmain.setText(sa);
    }

    //        presenter.presentState(ViewState.LOAD_WEATHER);
//        presenter.presentState(ViewState.SHOW_WEATHER);
    private void init() {
        ButterKnife.bind(this);
        presenter = new MainPresenterImpl(this, gpsTracker);

        model = new MainModel(this);
        initLayout();
    }

    private void initLayout() {
        tvThanhpho = findViewById(R.id.tv_city);
        tvNhietdo = findViewById(R.id.tv_temperature);
        tvNgay = findViewById(R.id.tv_title);
        recyGio = findViewById(R.id.recyclerView);
        tvUsAQI = findViewById(R.id.tv_pollution_AQI);
        //        recyNgay = findViewById(R.id.cardview_recyclerview);
        tvonhiem = findViewById(R.id.tv_pollution2);
        tvUSmain = findViewById(R.id.tv_pollution3);
        tvCNmain = findViewById(R.id.tv_pollution4);
        linearLayout = findViewById(R.id.chiso_onhiem);
        tvTieudeOnhiem = findViewById(R.id.tieude);
    }

    @Override
    public void thanhpho(String s) {
        tvThanhpho.setText(s);
    }

    @Override
    public void nhietdo(String integer) {
        tvNhietdo.setText(integer + "ºC");
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
        linearLayout.setBackgroundColor(Color.parseColor("#990000"));
        tvonhiem.setText("Không Khí Đang Ở Mức Nguy hiểm");
    }

    @Override
    public void AQI201() {
        linearLayout.setBackgroundColor(Color.parseColor("#A2007C"));
        tvonhiem.setText("Không Khí Đang Ở Mức RẤt Ô Nhiễm");
    }

    @Override
    public void AQI151() {
        linearLayout.setBackgroundColor(Color.parseColor("#FF0000"));
        tvonhiem.setText("Không Khí Đang Ở Mức Ô Nhiễm");
    }

    @Override
    public void AQI101() {
        linearLayout.setBackgroundColor(Color.parseColor("#FF6600"));
        tvonhiem.setText("Không Khí Đang Ở Mức Không tốt cho người thuộc nhóm nhạy cảm");
    }

    @Override
    public void AQI51() {
        linearLayout.setBackgroundColor(Color.parseColor("#FFFF00"));
        tvonhiem.setText("Không Khí Đang Ở Mức Vừa Phải");
        tvonhiem.setTextColor(Color.parseColor("#000000"));
        tvUsAQI.setTextColor(Color.parseColor("#000000"));
        tvCNmain.setTextColor(Color.parseColor("#000000"));
        tvTieudeOnhiem.setTextColor(Color.parseColor("#000000"));
    }

    @Override
    public void AQI00() {
        linearLayout.setBackgroundColor(Color.parseColor("#00FF33"));
        tvonhiem.setText("Không Khí Đang Ở Mức Tốt");
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
