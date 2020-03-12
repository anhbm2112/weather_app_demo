package anhbm.nws.weatherapp.presentation.ui.screen;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.snackbar.Snackbar;

import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.presentation.presenters.base.BaseView;
import anhbm.nws.weatherapp.presentation.ui.screen.main.MainActivity;


public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        this.progressDialog = new ProgressDialog(BaseActivity.this);
        progressDialog.setMessage(getString(R.string.message_loading));
        progressDialog.setCancelable(false);


    }
    @Override
    public void showProgress(boolean flag) {
        if (flag)
            progressDialog.show();
        else progressDialog.dismiss();
    }

    @Override
    public void showToast(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    @Override
    public void showToastGPS() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, R.string.GPSBat, Toast.LENGTH_LONG).show();
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(R.string.GPSTat).setCancelable(false)
                    .setPositiveButton(R.string.GPSCaiDat,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent callGPSSettingIntent = new Intent(
                                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(callGPSSettingIntent);
                                }
                            });
            alertDialogBuilder.setNegativeButton(R.string.GpsHUY,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
    }

    public void CheckInternetshowCaidat() {
        android.net.ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        android.net.NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            Toast.makeText(this,R.string.BatMang, Toast.LENGTH_SHORT).show();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.TatMang);
            builder.setMessage(R.string.caidatMang);
            builder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    builder.create();
                }
            });
            builder.setNegativeButton(R.string.CaiDat, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);

                }
            });
            builder.show();

        }
    }

//    public void showF() {
//        Dialog dialog = new Dialog(this);
//        dialog.setTitle("Chuyển Sang ºF");
//        dialog.setContentView(R.layout.c_f_dialog);
//        dialog.show();
//
//    }

    @Override
    public void showError(String title, String message) {
        new MaterialDialog.Builder(getApplicationContext())
                .title(title)
                .content(message)
                .positiveText(R.string.error_dialog_positive)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .autoDismiss(false)
                .cancelable(false)
                .contentColor(getResources().getColor(R.color.dark))
                .backgroundColorRes(R.color.bone_white)
                .show();
    }

    public void checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,R.string.LayvitriThanhCong, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, R.string.LayvitriThatBai, Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
