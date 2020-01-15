package anhbm.nws.weatherapp.presentation.ui.screen.main;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.presentation.presenters.MainPresenter;
import anhbm.nws.weatherapp.presentation.ui.screen.BaseActivity;
import anhbm.nws.weatherapp.presentation.ui.screen.about.AboutActivity;
import anhbm.nws.weatherapp.presentation.ui.screen.main.mvp.MainModel;
import anhbm.nws.weatherapp.presentation.ui.screen.main.mvp.MainPresenterImpl;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainPresenter.MainView {
    private MainPresenter presenter;
    private MainModel model;
    private TextView tvThanhpho, tvNhietdo, tvNgay, tvUsAQI, tvUSmain, tvCNaqi, tvCNmain;
    private RecyclerView recyGio, recyNgay;
    MainPresenterImpl mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();
        presenter.presentState(ViewState.LOAD_WEATHER);
        presenter.presentState(ViewState.SHOW_WEATHER);
    }

    private void init() {
        ButterKnife.bind(this);
        presenter = new MainPresenterImpl(this);
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
        tvUSmain = findViewById(R.id.tv_pollution2);
        tvCNaqi = findViewById(R.id.tv_pollution3);
        tvCNmain = findViewById(R.id.tv_pollution4);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

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
    @Override
    public void showProgress(boolean flag) {

    }

    @Override
    public void showState(MainPresenter.MainView.ViewState viewState) {
        switch (viewState) {
            case IDLE:
                showProgress(false);
                break;
            case LOADING:
                showProgress(true);
                break;
            case SHOW_WEATHER:

                showPeople();
                break;
            case OPEN_ABOUT:
                openActivityAbout();
                break;
            case ERROR:
                showToast(doRetrieveModel().getErrorMessage());
                break;
        }
    }

    @Override
    public MainModel doRetrieveModel() {
        return this.model;

    }

    @Override
    public void thanhpho(String s) {
//        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
        tvThanhpho.setText(s);
    }

    @Override
    public void nhietdo(String nhietdo) {
        tvNhietdo.setText(nhietdo+"ºC");
    }

    @Override
    public void ngay(String ngay) {
        tvNgay.setText(ngay);
        tvNgay.setPaintFlags(tvNgay.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void usAQI(String integer) {
        tvUsAQI.setText(integer);
    }

    @Override
    public void USmain(String USmain) {
        tvUSmain.setText(USmain);
    }

    @Override
    public void CNaqi(String CNaqi) {
        tvCNaqi.setText(CNaqi);
    }

    @Override
    public void CNmain(String CNmain) {
        tvCNmain.setText(CNmain);
    }


    /**
     * show WeatherResponse to UI
     */
    private void showPeople() {
        // show the data
        presenter.presentState(ViewState.IDLE);


    }

    private void openActivityAbout() {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }


}
