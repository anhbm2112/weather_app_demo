package anhbm.nws.weatherapp.presentation.ui.screen.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import anhbm.nws.weatherapp.R;

public class TemperatureFragment extends BottomSheetDialogFragment {
    private Button buttonC, buttonF;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static final String IS_DEGREE = "IS_DEGREE";
    private static final String IS_KELVIN = "IS_KELVIN";

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
        preferences = this.getActivity().getSharedPreferences("key", getActivity().MODE_PRIVATE);
        editor = preferences.edit();

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

}
