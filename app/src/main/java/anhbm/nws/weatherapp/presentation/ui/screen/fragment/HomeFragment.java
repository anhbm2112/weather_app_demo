package anhbm.nws.weatherapp.presentation.ui.screen.fragment;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.presentation.presenters.MainPresenter;
import anhbm.nws.weatherapp.presentation.ui.adapter.WeatherDayAdapter;
import anhbm.nws.weatherapp.presentation.ui.adapter.WeatherHorizontalAdapter;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements MainPresenter {
    private TextView tvThanhpho, tvNhietdo, tvNgay, tvUsAQI,AQI, tvonhiem, tvTieudeOnhiem;
    private RecyclerView recyNgay, recyList;
    private WeatherHorizontalAdapter weatherListDayAdapter;
    private ImageView imageView;
    private LinearLayout linearLayout;
    private WeatherDayAdapter weatherListAdapter;
    ///SharedPreferences
    private List<ListAPI> enums = new ArrayList<>();
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    //    String oC, oF;
    private static final String IS_DEGREE = "IS_DEGREE";
    private static final String IS_KELVIN = "IS_KELVIN";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvThanhpho = view.findViewById(R.id.tv_city);
        tvNhietdo = view.findViewById(R.id.tv_temperature);
        tvNgay = view.findViewById(R.id.tv_title);
        recyNgay = view.findViewById(R.id.recyclerView);
        tvUsAQI = view.findViewById(R.id.tv_pollution_AQI);
        recyList = view.findViewById(R.id.recyclerviewDay);
        tvonhiem = view.findViewById(R.id.tv_pollution2);
        tvTieudeOnhiem = view.findViewById(R.id.tieude);
        imageView = view.findViewById(R.id.icon_onhiem);
        linearLayout=view.findViewById(R.id.LinearOnhiem);
        AQI=view.findViewById(R.id.tv_pollution_AQI_hai);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        init();
        enums = getValueFromPreference();
        initRecyclerView(enums);
        Managaer();
    }

    private void init() {
        preferences = this.getActivity().getSharedPreferences("key", MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();
        initData();
    }

    private void initData() {
        boolean c = preferences.getBoolean(IS_DEGREE, true);
        boolean k = preferences.getBoolean(IS_KELVIN, false);
        if (c && !k) {

        } else if (!c && k) {

        }
    }

    private void initRecyclerView(List<ListAPI> list) {
        ///hien thi du lieu list khi mat mang
        weatherListDayAdapter = new WeatherHorizontalAdapter(getActivity(), list);
        recyNgay.setAdapter(weatherListDayAdapter);
        weatherListAdapter = new WeatherDayAdapter(getActivity(), list);
        recyList.setAdapter(weatherListAdapter);
        String thanhpho = preferences.getString("keyThanhpho", "");
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
        tvThanhpho.setTypeface(typeface);
        tvThanhpho.setText(thanhpho);
        String ngay = preferences.getString("keyngay", "");
        tvNgay.setTypeface(typeface);
        tvNgay.setText(ngay);
        boolean s = preferences.getBoolean(IS_DEGREE, true);
        boolean k = preferences.getBoolean(IS_KELVIN, false);
        if (s && !k) {
            String keyC = preferences.getString("keyC", "");
            tvNhietdo.setTypeface(typeface);
            tvNhietdo.setText(String.valueOf(keyC) + "ºC");

        } else if (!s && k) {
            String keyF = preferences.getString("keyF", "");
            tvNhietdo.setTypeface(typeface);
            tvNhietdo.setText(String.valueOf(keyF) + "ºF");
        }

        Integer integer = preferences.getInt("keyOnhiem", 0);
        tvUsAQI.setText(integer.toString());
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
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyNgay.setLayoutManager(horizontalLayoutManagaer);
        LinearLayoutManager LayoutManagaer = new LinearLayoutManager(getActivity());
        recyList.setLayoutManager(LayoutManagaer);
        Integer integer = preferences.getInt("keyOnhiem", 1);
        ///hien thi do o nhiem khi tat mang
        tvUsAQI.setText(String.valueOf(integer));
        if (integer >= 301) {
            linearLayout.setBackgroundResource(R.color.MauNguyHiem);
            tvonhiem.setText(R.string.NguyHiem);
            tvonhiem.setTextColor(getResources().getColor(R.color.MauNguyHiem));
            tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauNguyHiem));
            imageView.setImageResource(R.mipmap.ic_onhiem_301);
            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");

            tvonhiem.setTypeface(typeface);
            tvUsAQI.setTypeface(typeface);
            tvTieudeOnhiem.setTypeface(typeface);
            AQI.setTypeface(typeface);

        } else if (integer >= 201) {
            linearLayout.setBackgroundResource(R.color.MauRatONhiem);
            tvonhiem.setText(R.string.RatONhiem);
            tvonhiem.setTextColor(getResources().getColor(R.color.MauRatONhiem));
            tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauRatONhiem));
            imageView.setImageResource(R.mipmap.ic_onhiem_201);
            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
            AQI.setTypeface(typeface);
            tvonhiem.setTypeface(typeface);
            tvUsAQI.setTypeface(typeface);
            tvTieudeOnhiem.setTypeface(typeface);
        } else if (integer >= 151) {
            linearLayout.setBackgroundResource(R.color.MauOnhiem);
            tvonhiem.setText(R.string.Onhiem);
            tvonhiem.setTextColor(getResources().getColor(R.color.MauOnhiem));
            tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauOnhiem));
            imageView.setImageResource(R.mipmap.ic_onhiem_151);
            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
            AQI.setTypeface(typeface);
            tvonhiem.setTypeface(typeface);
            tvUsAQI.setTypeface(typeface);
            tvTieudeOnhiem.setTypeface(typeface);
        } else if (integer >= 101) {
            linearLayout.setBackgroundResource(R.color.MauNhayCam);
            tvonhiem.setText(R.string.NhayCam);
            tvonhiem.setTextColor(getResources().getColor(R.color.MauNhayCam));
            tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauNhayCam));
            imageView.setImageResource(R.mipmap.ic_onhiem_101);
            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
            tvonhiem.setTypeface(typeface);
            tvUsAQI.setTypeface(typeface);
            tvTieudeOnhiem.setTypeface(typeface);
            AQI.setTypeface(typeface);
        } else if (integer >= 51) {
            linearLayout.setBackgroundResource(R.color.MauVuaPhai);
            tvonhiem.setText(R.string.VuaPhai);
            tvonhiem.setTextColor(getResources().getColor(R.color.MauVuaPhai));
            tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauVuaPhai));
            imageView.setImageResource(R.mipmap.ic_onhiem_51);
            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
            tvonhiem.setTypeface(typeface);
            tvUsAQI.setTypeface(typeface);
            tvTieudeOnhiem.setTypeface(typeface);
            AQI.setTypeface(typeface);
        } else {
            linearLayout.setBackgroundResource(R.color.MauTot);
            tvonhiem.setText(R.string.Tot);
            tvonhiem.setTextColor(getResources().getColor(R.color.MauTot));
            tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauTot));
            imageView.setImageResource(R.mipmap.ic_onhiem_50);
            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
            tvonhiem.setTypeface(typeface);
            tvUsAQI.setTypeface(typeface);
            tvTieudeOnhiem.setTypeface(typeface);
            AQI.setTypeface(typeface);
        }
    }

    @Override
    public void getRecyclerView(List<ListAPI> weatherListDays) {
        weatherListDayAdapter = new WeatherHorizontalAdapter(getActivity(), weatherListDays);
        recyNgay.setAdapter(weatherListDayAdapter);
        weatherListAdapter = new WeatherDayAdapter(getActivity(), weatherListDays);
        recyList.setAdapter(weatherListAdapter);
        saveValueToPreference(weatherListDays);
    }


    @Override
    public void thanhpho(String s) {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
        tvThanhpho.setTypeface(typeface);
        tvThanhpho.setText(s);
    }


    @Override
    public void nhietdoC(Double C) {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
        tvNhietdo.setTypeface(typeface);
        tvNhietdo.setText(String.valueOf(C).substring(0,2) + "ºC");
    }

    @Override
    public void nhietdoF(Double F) {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
        tvNhietdo.setTypeface(typeface);
        tvNhietdo.setText(String.valueOf(F).substring(0,2) + "ºF");
    }

    @Override
    public void ngay(String ngay) {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
        tvNgay.setTypeface(typeface);
        tvNgay.setText(ngay);

    }

    @Override
    public void usAQI(Integer usAQI) {
        tvUsAQI.setText(String.valueOf(usAQI));
    }

    @Override
    public void AQI301() {
        linearLayout.setBackgroundResource(R.color.MauNguyHiem);
        tvonhiem.setText(R.string.NguyHiem);
        tvonhiem.setTextColor(getResources().getColor(R.color.MauNguyHiem));
        tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauNguyHiem));
        imageView.setImageResource(R.mipmap.ic_onhiem_301);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
        tvonhiem.setTypeface(typeface);
        tvUsAQI.setTypeface(typeface);
        tvTieudeOnhiem.setTypeface(typeface);
        AQI.setTypeface(typeface);
    }

    @Override
    public void AQI201() {
        linearLayout.setBackgroundResource(R.color.MauRatONhiem);
        tvonhiem.setText(R.string.RatONhiem);
        tvonhiem.setTextColor(getResources().getColor(R.color.MauRatONhiem));
        tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauRatONhiem));
        imageView.setImageResource(R.mipmap.ic_onhiem_201);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
        tvonhiem.setTypeface(typeface);
        tvUsAQI.setTypeface(typeface);
        tvTieudeOnhiem.setTypeface(typeface);
        AQI.setTypeface(typeface);
    }

    @Override
    public void AQI151() {
        linearLayout.setBackgroundResource(R.color.MauOnhiem);
        tvonhiem.setText(R.string.Onhiem);
        tvonhiem.setTextColor(getResources().getColor(R.color.MauOnhiem));
        tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauOnhiem));
        imageView.setImageResource(R.mipmap.ic_onhiem_151);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
        tvonhiem.setTypeface(typeface);
        tvUsAQI.setTypeface(typeface);
        tvTieudeOnhiem.setTypeface(typeface);
        AQI.setTypeface(typeface);
    }

    @Override
    public void AQI101() {
        linearLayout.setBackgroundResource(R.color.MauNhayCam);
        tvonhiem.setText(R.string.NhayCam);
        tvonhiem.setTextColor(getResources().getColor(R.color.MauNhayCam));
        tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauNhayCam));
        imageView.setImageResource(R.mipmap.ic_onhiem_101);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
        tvonhiem.setTypeface(typeface);
        tvUsAQI.setTypeface(typeface);
        tvTieudeOnhiem.setTypeface(typeface);
        AQI.setTypeface(typeface);
    }

    @Override
    public void AQI51() {
        linearLayout.setBackgroundResource(R.color.MauVuaPhai);
        tvonhiem.setText(R.string.VuaPhai);
        tvonhiem.setTextColor(getResources().getColor(R.color.MauVuaPhai));
        tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauVuaPhai));
        imageView.setImageResource(R.mipmap.ic_onhiem_51);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
        tvonhiem.setTypeface(typeface);
        tvUsAQI.setTypeface(typeface);
        tvTieudeOnhiem.setTypeface(typeface);
        AQI.setTypeface(typeface);
    }

    @Override
    public void AQI00() {
        linearLayout.setBackgroundResource(R.color.MauTot);
        tvonhiem.setText(R.string.Tot);
        tvonhiem.setTextColor(getResources().getColor(R.color.MauTot));
        tvTieudeOnhiem.setTextColor(getResources().getColor(R.color.MauTot));
        imageView.setImageResource(R.mipmap.ic_onhiem_50);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "SpaceMonoBold.ttf");
        tvonhiem.setTypeface(typeface);
        tvUsAQI.setTypeface(typeface);
        tvTieudeOnhiem.setTypeface(typeface);
        AQI.setTypeface(typeface);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getActivity(), R.string.LayvitriThanhCong, Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getActivity(), R.string.LayvitriThatBai, Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }

    }
}
