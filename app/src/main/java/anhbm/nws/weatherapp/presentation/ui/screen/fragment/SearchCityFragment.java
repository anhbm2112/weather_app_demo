package anhbm.nws.weatherapp.presentation.ui.screen.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.api.APICallListener;
import anhbm.nws.weatherapp.api.weather.modelWeatherAPI.Data;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.application.GPSTracker;
import anhbm.nws.weatherapp.domains.interactors.WeatherInteractor;
import anhbm.nws.weatherapp.presentation.presenters.AboutPresenter;
import anhbm.nws.weatherapp.presentation.presenters.MainPresenter;
import anhbm.nws.weatherapp.presentation.presenters.onSetInterFace.OnCallBackData;
import anhbm.nws.weatherapp.presentation.ui.adapter.WeatherCityAdapter;
import anhbm.nws.weatherapp.presentation.ui.screen.BaseActivity;
import anhbm.nws.weatherapp.presentation.ui.screen.BaseFragment;
import anhbm.nws.weatherapp.presentation.ui.screen.main.MainActivity;
import anhbm.nws.weatherapp.presentation.ui.screen.main.mvp.MainModel;
import anhbm.nws.weatherapp.presentation.ui.screen.main.mvp.MainPresenterImpl;
import anhbm.nws.weatherapp.presentation.ui.screen.searchCity.AboutActivity;
import anhbm.nws.weatherapp.presentation.ui.screen.searchCity.mvp.AboutPresenterImpl;
import butterknife.ButterKnife;

public class SearchCityFragment extends BaseFragment implements AboutPresenter, AdapterView.OnItemSelectedListener {
    private TextView tvThanhpho, tvNhietdo;
    private WeatherCityAdapter weatherCityAdapter;
    private RecyclerView recyCity;
    private ImageView imageView;
    private Spinner spinner;
    private ImageView back;
    private ArrayAdapter<String> arrayAdapterSpin;
    private OnCallBackData onCallBackData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchcity, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvThanhpho = view.findViewById(R.id.tv_city_manCity);
        tvNhietdo = view.findViewById(R.id.tv_temperature_mancity);
        recyCity = view.findViewById(R.id.recyclerViewCity);
        imageView = view.findViewById(R.id.imgIconchinh);
        spinner = view.findViewById(R.id.spin);
        back = view.findViewById(R.id.back_timkiem);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout_main, homeFragment).commit();

            }
        });
        CheckInternetshowCaidat();
        setSpinner();
        LinearLayoutManager LayoutManagaer = new LinearLayoutManager(getActivity());
        recyCity.setLayoutManager(LayoutManagaer);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onCallBackData = (OnCallBackData) context;
        } catch (ClassCastException e) {

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String thanhpho = adapterView.getItemAtPosition(i).toString();
        onCallBackData.DataCity(thanhpho);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setSpinner() {
        String[] array = getResources().getStringArray(R.array.List_City);
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(array));
        arrayAdapterSpin = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrayList);
        arrayAdapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapterSpin);
        spinner.setOnItemSelectedListener(this);

//        for (int i = 0; i < arrayList.size(); i++) {
//            if (arrayList.get(i).equals(thanhphoLichsu)) {
//                spinner.setSelection(i);
//            }
//        }
//        Bundle bundle = getArguments();
//        String lichsuThanhpho = bundle.getString("timthanhpho");
//        Log.e("TTTTHHHHAAAANNNHHHh", lichsuThanhpho);
    }

    @Override
    public void thanhpho(String thanhpho) {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
        tvThanhpho.setTypeface(typeface);
        tvThanhpho.setText(thanhpho);
    }

    @Override
    public void getRecyCitySearch(List<ListAPI> listCityList) {
        weatherCityAdapter = new WeatherCityAdapter(getActivity(), listCityList);
        recyCity.setAdapter(weatherCityAdapter);
    }

    @Override
    public void icon(String icon) {
        Picasso.with(getActivity()).load("http://api.openweathermap.org/img/w/" + icon + ".png").into(imageView);
    }

    @Override
    public void nhietdoF(Double F) {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
        tvNhietdo.setTypeface(typeface);
        tvNhietdo.setText(String.valueOf(F).substring(0, 2) + "ºF");
    }

    @Override
    public void nhietdoC(Double aDouble) {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
        tvNhietdo.setTypeface(typeface);
        tvNhietdo.setText(String.valueOf(aDouble).substring(0, 2) + "ºC");
    }


}
