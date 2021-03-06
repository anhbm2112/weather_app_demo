package anhbm.nws.weatherapp.presentation.ui.screen.main;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.application.GPSTracker;
import anhbm.nws.weatherapp.presentation.presenters.onSetInterFace.OnCallBackData;
import anhbm.nws.weatherapp.presentation.presenters.onSetInterFace.OndataList;
import anhbm.nws.weatherapp.presentation.ui.screen.BaseActivity;
import anhbm.nws.weatherapp.presentation.ui.screen.fragment.HistoryFragment;
import anhbm.nws.weatherapp.presentation.ui.screen.fragment.HomeFragment;
import anhbm.nws.weatherapp.presentation.ui.screen.fragment.SearchCityFragment;
import anhbm.nws.weatherapp.presentation.ui.screen.fragment.TemperatureFragment;
import anhbm.nws.weatherapp.presentation.ui.screen.main.mvp.MainPresenterImpl;
import anhbm.nws.weatherapp.presentation.ui.screen.searchCity.mvp.AboutPresenterImpl;

public class MainActivity extends BaseActivity implements OnCallBackData {
    private BottomNavigationView bottomNavigationView;
    private MainPresenterImpl presenterMain;
    private AboutPresenterImpl presenterAbout;
    private GPSTracker gpsTracker;
    ///SharedPreferences
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    //    String oC, oF;
    private static final String Check_Permission_TrueFalse = "PermissionTrueFalse";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showToastGPS();
        CheckInternetshowCaidat();
        CheckLocationPermission();
        init();
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frameLayout_main, homeFragment).commit();
        gpsTracker = new GPSTracker(getApplicationContext());
        presenterMain = new MainPresenterImpl(homeFragment, gpsTracker, this);
    }

    private void init() {
        bottomNavigationView = findViewById(R.id.bottomnavigation);
        preferences = getSharedPreferences("key", MODE_PRIVATE);
        editor = preferences.edit();
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


//        initLayout();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            switch (menuItem.getItemId()) {
                case R.id.menu_Home:
                    fragmentManager.beginTransaction().add(R.id.frameLayout_main, new HomeFragment()).commit();
                    break;
                case R.id.menu_bottomn_search:
                    SearchCityFragment searchCityFragment = new SearchCityFragment();
                    presenterAbout = new AboutPresenterImpl(searchCityFragment, getApplicationContext());
                    fragmentManager.beginTransaction().add(R.id.frameLayout_main, searchCityFragment).commit();
                    break;
                case R.id.menu_bottomn_FvsC:
                    TemperatureFragment temperatureFragment = TemperatureFragment.newInstance();
                    temperatureFragment.show(getSupportFragmentManager(), "ActionBottomDialog");
                    break;
                case R.id.menu_history:
                    fragmentManager.beginTransaction().add(R.id.frameLayout_main, new HistoryFragment()).commit();
                    break;
            }
            return false;
        }

    };




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        editor.putBoolean(Check_Permission_TrueFalse, true);
                        Toast.makeText(this, R.string.LayvitriThanhCong, Toast.LENGTH_SHORT).show();
                        HomeFragment homeFragment = new HomeFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frameLayout_main, homeFragment).commit();
                        gpsTracker = new GPSTracker(getApplicationContext());
                        presenterMain = new MainPresenterImpl(homeFragment, gpsTracker, this);

                    }
                } else {
                    Toast.makeText(this, R.string.LayvitriThatBai, Toast.LENGTH_SHORT).show();
                    editor.putBoolean(Check_Permission_TrueFalse, false);
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
