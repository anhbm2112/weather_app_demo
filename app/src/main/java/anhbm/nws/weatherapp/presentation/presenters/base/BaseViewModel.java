package anhbm.nws.weatherapp.presentation.presenters.base;

import android.content.Context;

public class BaseViewModel {
    private Context context;
    private String errorMessage;

    public BaseViewModel(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
