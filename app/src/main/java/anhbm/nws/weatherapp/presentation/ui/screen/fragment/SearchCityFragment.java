package anhbm.nws.weatherapp.presentation.ui.screen.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.presentation.presenters.AboutPresenter;
import anhbm.nws.weatherapp.presentation.presenters.onSetInterFace.OnCallBackData;
import anhbm.nws.weatherapp.presentation.ui.adapter.WeatherCityAdapter;
import anhbm.nws.weatherapp.presentation.ui.screen.BaseFragment;


public class SearchCityFragment extends BaseFragment implements AboutPresenter, AdapterView.OnItemSelectedListener {
    private TextView tvThanhpho, tvNhietdo;
    private WeatherCityAdapter weatherCityAdapter;
    private RecyclerView recyCity;
    private ImageView imageView;
    private Spinner spinner;
    private ImageView back;
    private ArrayAdapter<String> arrayAdapterSpin;
    private OnCallBackData onCallBackData;
    private Typeface typeface;
    private ArrayList<String> arrayList;


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
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("key", getActivity().MODE_PRIVATE);
//        String thanhpho11 = sharedPreferences.getString("keyThanhpho", "");
//        if (thanhpho11 == thanhpho) {
//            spinner.setSelection(3);
//        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setSpinner() {
        String[] array = getResources().getStringArray(R.array.List_City);
        arrayList = new ArrayList<String>(Arrays.asList(array));
        arrayAdapterSpin = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, arrayList);
        arrayAdapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapterSpin);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void thanhpho(String thanhpho) {
        tvThanhpho.setText(thanhpho);
    }

    @Override
    public void getRecyCitySearch(List<ListAPI> listCityList) {
        weatherCityAdapter = new WeatherCityAdapter(getContext(), listCityList);
        recyCity.setAdapter(weatherCityAdapter);
    }

    @Override
    public void icon(String icon) {
        Picasso.with(getActivity()).load("http://api.openweathermap.org/img/w/" + icon + ".png").into(imageView);
    }

    @Override
    public void nhietdoF(Double F) {
        tvNhietdo.setText(String.valueOf(F).substring(0, 3) + "ºF");
    }

    @Override
    public void nhietdoC(Double aDouble) {
        tvNhietdo.setText(String.valueOf(aDouble).substring(0, 2) + "ºC");
    }

}
