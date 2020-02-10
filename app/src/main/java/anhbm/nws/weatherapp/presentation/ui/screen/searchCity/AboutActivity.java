package anhbm.nws.weatherapp.presentation.ui.screen.searchCity;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;
import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.presentation.presenters.AboutPresenter;
import anhbm.nws.weatherapp.presentation.ui.adapter.WeatherCityAdapter;
import anhbm.nws.weatherapp.presentation.ui.screen.searchCity.mvp.AboutPresenterImpl;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity implements AboutPresenter, AdapterView.OnItemSelectedListener {
    private AboutPresenterImpl aboutPresenter;
    public String nhapthanhpho;
    private TextView tvThanhpho, tvNhietdo, tvNgayCity;
    private WeatherCityAdapter weatherCityAdapter;
    private RecyclerView recyCity;
    private ImageView imageView;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();
        //spinner
        ArrayAdapter<CharSequence> arrayAdapterSpin = ArrayAdapter.createFromResource(this, R.array.City,
                android.R.layout.simple_spinner_item);
        arrayAdapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapterSpin);
///recy
        LinearLayoutManager LayoutManagaer = new LinearLayoutManager(getApplicationContext());
        recyCity.setLayoutManager(LayoutManagaer);
        initLayout();
//        mPresenter.presentState(ViewState.SHOW_ABOUT);

    }

    private void initLayout() {
        ButterKnife.bind(AboutActivity.this);
//        buttonTim.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);
    }

//    @Override
//    public void onClick(View view) {
//        if (view.getId() == R.id.bttim) {
////            nhapthanhpho = editTextTim.getText().toString().trim();
////            aboutPresenter.Tim(nhapthanhpho);
//        }
//    }

    private void init() {
        aboutPresenter = new AboutPresenterImpl(this, this);
//        this.mPresenter = new AboutPresenterImpl(this);
//        this.mModel = new AboutModel();

        tvThanhpho = findViewById(R.id.tv_city_manCity);
        tvNhietdo = findViewById(R.id.tv_temperature_mancity);
        tvNgayCity = findViewById(R.id.tv_ngaycity);
        recyCity = findViewById(R.id.recyclerViewCity);
        imageView = findViewById(R.id.imgIconchinh);
        spinner = findViewById(R.id.spin);
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

}
