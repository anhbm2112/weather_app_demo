package anhbm.nws.weatherapp.presentation.ui.screen.fragment;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import anhbm.nws.weatherapp.R;

public class TemperatureFragment extends BottomSheetDialogFragment {
    private Button buttonC, buttonF;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static final String IS_DEGREE = "IS_DEGREE";
    private static final String IS_KELVIN = "IS_KELVIN";
    private ImageView imageView;
    //    private static final String Check_Permission = "Permission";
    private static final String Check_Permission_TrueFalse = "PermissionTrueFalse";
    private boolean s;
    private CardView cardView;

    public static TemperatureFragment newInstance() {
        return new TemperatureFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonC = view.findViewById(R.id.c);
        buttonF = view.findViewById(R.id.f);
        imageView = view.findViewById(R.id.icon_location);
        cardView = view.findViewById(R.id.location);
        preferences = this.getActivity().getSharedPreferences("key", getActivity().MODE_PRIVATE);
        editor = preferences.edit();
        s = preferences.getBoolean(Check_Permission_TrueFalse, true);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        dismissAllowingStateLoss();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        dismissAllowingStateLoss();
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(),R.string.Permission, Toast.LENGTH_SHORT).show();
            cardView.setVisibility(View.GONE);
        }


        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean(IS_DEGREE, true);
                editor.putBoolean(IS_KELVIN, false);
                editor.commit();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                HomeFragment homeFragment = new HomeFragment();
                fragmentManager.beginTransaction().replace(R.id.frameLayout_main, homeFragment).commit();
                dismissAllowingStateLoss();
            }
        });

        buttonF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean(IS_DEGREE, false);
                editor.putBoolean(IS_KELVIN, true);
                editor.commit();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                HomeFragment homeFragment = new HomeFragment();
                fragmentManager.beginTransaction().replace(R.id.frameLayout_main, homeFragment).commit();
                dismissAllowingStateLoss();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getActivity(), R.string.LayvitriThanhCong, Toast.LENGTH_SHORT).show();
                        editor.putBoolean(Check_Permission_TrueFalse, true);

                    }
                } else {
                    Toast.makeText(getActivity(), R.string.LayvitriThatBai, Toast.LENGTH_SHORT).show();
                    editor.putBoolean(Check_Permission_TrueFalse, false);
                }
                return;
            }

        }
    }
}
