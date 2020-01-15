package anhbm.nws.weatherapp.presentation.presenters.base;

public interface BaseView {
void showToastGPS();
    /**
     * This is a general method used for showing some kind of progress during a background task. For example, this
     * method should show a progress bar and/or disable buttons before some background work starts.
     *
     * @param flag True to show, false to hide progress
     */
    void showProgress(boolean flag);

    /**
     * This method is used for showing toast messages on the UI.
     *
     * @param message
     */
    void showToast(String message);

    /**
     * This method is used for showing error messages on the UI via dialog.
     *
     * @param title
     * @param message
     */
    void showError(String title, String message);

}
