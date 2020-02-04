package anhbm.nws.weatherapp.presentation.ui.screen.about;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.api.weather.modelWeatherCity.ListCity;
import anhbm.nws.weatherapp.api.weather.modelWeatherCity.WeatherCity;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.presentation.presenters.AboutPresenter;
import anhbm.nws.weatherapp.presentation.ui.adapter.WeatherCityAdapter;
import anhbm.nws.weatherapp.presentation.ui.screen.about.mvp.AboutPresenterImpl;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity implements AboutPresenter,
        View.OnClickListener {
    private AboutPresenterImpl aboutPresenter;
    private Button buttonTim;
    private EditText editTextTim;
    public String nhapthanhpho;
    private TextView tvThanhpho, tvNhietdo, tvNgayCity;
    private WeatherCityAdapter weatherCityAdapter;
    private RecyclerView recyCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        buttonTim = findViewById(R.id.bttim);
        editTextTim = findViewById(R.id.edtim);
        tvThanhpho = findViewById(R.id.tv_city_manCity);
        tvNhietdo = findViewById(R.id.tv_temperature_mancity);
        tvNgayCity = findViewById(R.id.tv_ngaycity);
        recyCity=findViewById(R.id.recyclerViewCity);
        LinearLayoutManager LayoutManagaer = new LinearLayoutManager(getApplicationContext());
        recyCity.setLayoutManager(LayoutManagaer);
        initLayout();
//        mPresenter.presentState(ViewState.SHOW_ABOUT);

    }

    private void initLayout() {
        ButterKnife.bind(AboutActivity.this);
        buttonTim.setOnClickListener(this);
        init();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bttim) {
            nhapthanhpho = editTextTim.getText().toString().trim();
            aboutPresenter.Tim(nhapthanhpho);
        }
    }

    private void init() {
        aboutPresenter = new AboutPresenterImpl(this, this);
//        this.mPresenter = new AboutPresenterImpl(this);
//        this.mModel = new AboutModel();
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
        weatherCityAdapter = new WeatherCityAdapter(listCityList, this);
        recyCity.setAdapter(weatherCityAdapter);
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


//    @Override
//    public void showState(ViewState state) {
//        switch (state) {
//            case IDLE:
//                showProgress(false);
//                break;
//            case LOADING:
//                showProgress(true);
//                break;
//            case SHOW_ABOUT:
//                showAbout();
//                break;
//            case ERROR:
//                showError(getString(R.string.error_message_title_oops), getString(R.string.error_message_api));
//                break;
//        }
//    }
//
//    @Override
//    public AboutModel doRetrieveModel() {
//        return this.mModel;
//    }
//
//    @Override
//    public void showToastGPS() {
//
//    }
//
//
//    @Override
//    public void showProgress(boolean flag) {
//
//    }
//
//    @Override
//    public void showToast(String message) {
//        Toast.makeText(AboutActivity.this, "" + message, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void showError(String title, String message) {
//        new MaterialDialog.Builder(AboutActivity.this)
//                .title(title)
//                .content(message)
//                .positiveText(R.string.error_dialog_positive)
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        dialog.dismiss();
//                    }
//                })
//                .autoDismiss(false)
//                .cancelable(false)
//                .contentColor(getResources().getColor(R.color.dark))
//                .backgroundColorRes(R.color.bone_white)
//                .show();
//    }
//
//    private void showAbout() {
//        PackageInfo pInfo;
//        String version = null;
//        try {
//            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//            version = pInfo.versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        textViewVersion.setText("Version: " + version);
//    }
//
//    @OnClick(R.id.layout_about_github)
//    public void onClickGithub() {
//        String url = "http://github.com/nandawperdana/android-mvp-architecture";
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setData(Uri.parse(url));
//        startActivity(i);
//    }
}
