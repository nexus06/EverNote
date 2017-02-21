package es.puliware.android.evernote.utils;

/**
 * Created by nexus on 2/19/17.
 */

/**
 * The base interface that an operations ("Ops") class in the
 * Presenter layer must implement.
 */
public interface LoginPresenterOps<RequiredViewOps> {
    /**
     * Hook method dispatched by the GenericActivity framework to
     * initialize an operations ("Ops") object after it's been
     * instantiated.
     *
     * @param view The currently active RequiredViewOps.
     */
    void onCreate(RequiredViewOps view);

    /**
     * update an operations ("Ops") object after a runtime
     * configuration change has occurred in the View layer.
     *
     * @param view The currently active RequiredViewOps.
     */
    void onConfigurationChange(RequiredViewOps view);

    /**
     * Hook method called when an Ops object in the Presenter layer is
     * destroyed.
     *
     * @param isChangingConfigurations True if a runtime configuration triggered the onDestroy() call.
     */
    void onDestroy(boolean isChangingConfigurations);
}
