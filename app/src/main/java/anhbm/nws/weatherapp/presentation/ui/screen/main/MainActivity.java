package anhbm.nws.weatherapp.presentation.ui.screen.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.application.GPSTracker;
import anhbm.nws.weatherapp.presentation.presenters.onSetInterFace.OnCallBackData;
import anhbm.nws.weatherapp.presentation.ui.screen.BaseActivity;
import anhbm.nws.weatherapp.presentation.ui.screen.fragment.HistoryFragment;
import anhbm.nws.weatherapp.presentation.ui.screen.fragment.HomeFragment;
import anhbm.nws.weatherapp.presentation.ui.screen.fragment.SearchCityFragment;
import anhbm.nws.weatherapp.presentation.ui.screen.fragment.TemperatureFragment;
import anhbm.nws.weatherapp.presentation.ui.screen.main.mvp.MainPresenterImpl;
import anhbm.nws.weatherapp.presentation.ui.screen.searchCity.mvp.AboutPresenterImpl;

public class MainActivity extends BaseActivity implements OnCallBackData, BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private MainPresenterImpl presenterMain;
    private AboutPresenterImpl presenterAbout;
    private GPSTracker gpsTracker;
    ///SharedPreferences
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    //    String oC, oF;
    private static final String IS_DEGREE = "IS_DEGREE";
    private static final String IS_KELVIN = "IS_KELVIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showToastGPS();
        CheckInternetshowCaidat();
        init();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout_main, homeFragment).commit();
        gpsTracker = new GPSTracker(getApplicationContext());
        presenterMain = new MainPresenterImpl(homeFragment, gpsTracker, this);

    }

    private void init() {
        preferences = getSharedPreferences("key", MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();
        initLayout();
        initData();
    }


    private void initData() {
        boolean c = preferences.getBoolean(IS_DEGREE, true);
        boolean k = preferences.getBoolean(IS_KELVIN, false);
        if (c && !k) {

        } else if (!c && k) {

        }
    }
    private void initLayout() {
        bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (menuItem.getItemId()) {
            case R.id.menu_Home:

                HomeFragment homeFragment = new HomeFragment();
                fragmentManager.beginTransaction().replace(R.id.frameLayout_main, homeFragment).commit();
                return true;

            case R.id.menu_bottomn_search:
                SearchCityFragment searchCityFragment = new SearchCityFragment();
                presenterAbout = new AboutPresenterImpl(searchCityFragment, this);
                fragmentManager.beginTransaction().replace(R.id.frameLayout_main, searchCityFragment).commit();
                return true;

            case R.id.menu_bottomn_FvsC:
                TemperatureFragment temperatureFragment = TemperatureFragment.newInstance();
                temperatureFragment.show(getSupportFragmentManager(),
                        "ActionBottomDialog");
                return true;
            case R.id.menu_history:
                HistoryFragment historyFragment = new HistoryFragment();
                fragmentManager.beginTransaction().replace(R.id.frameLayout_main, historyFragment).commit();

                return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, R.string.LayvitriThanhCong, Toast.LENGTH_SHORT).show();
//                        gpsTracker = new GPSTracker(getApplicationContext());
//                        init();
//                        enums = getValueFromPreference();
//                        Managaer();
//                        initRecyclerView(enums);
                    }
                } else {
                    Toast.makeText(this, R.string.LayvitriThatBai, Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }

    }

    @Override
    public void DataCity(String s) {
        presenterAbout.Tim(s);
    }

}
