package anhbm.nws.weatherapp.presentation.ui.screen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import anhbm.nws.weatherapp.R;

public class BaseFragment extends Fragment {

    public void CheckInternetshowCaidat() {
        android.net.ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);

        android.net.NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            Toast.makeText(getActivity(), R.string.BatMang, Toast.LENGTH_SHORT).show();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
}
