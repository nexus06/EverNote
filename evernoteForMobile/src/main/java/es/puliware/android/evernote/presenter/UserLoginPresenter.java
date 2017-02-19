package es.puliware.android.evernote.presenter;

/**
 * Created by nexus on 2/18/17.
 */

import android.content.Context;
import es.puliware.android.evernote.MVP;
import es.puliware.android.evernote.model.UserModel;

import java.lang.ref.WeakReference;

/**
 * This class plays the "Presenter" role in the Model-View-Presenter
 * (MVP) pattern by acting upon the Model and the View, i.e., it
 * retrieves data from the Model (e.g., UserModel) and
 * display in the View (e.g., LoginActivity).  It
 * implements  It implements MVP.ProvidedLoginPresenterOps and
 * MVP.RequiredPresenterOps to decouple the MVP layers.
 *
 */
public class UserLoginPresenter implements MVP.ProvidedLoginPresenterOps, MVP.RequiredPresenterOps{
    /**
     * Tag for logging
     */
    protected final static String TAG = UserLoginPresenter.class.getSimpleName();


    /**
     * A WeakReference used to access methods in the View layer.  The
     * WeakReference enables garbage collection.
     */
    private WeakReference<MVP.RequiredLoginViewOps> mView;

    private UserModel mModelInstance;


    @Override
    public void onCreate(MVP.RequiredLoginViewOps view) {
        // Set the WeakReference.
        mView = new WeakReference<>(view);

        // Perform the first initialization
        // passing in the UserModel class to
        // instantiate and "this" to provide
        // Model with this MVP.RequiredModelOps
        // instance.
        // Create the ModelType object.
        try {
            initializeModel(UserModel.class, this);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * Initialize the model fields.
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void initializeModel(Class<UserModel> opsType,
                                 MVP.RequiredPresenterOps presenter)
            throws InstantiationException, IllegalAccessException {
        // Create the ModelType object.
        mModelInstance = opsType.newInstance();

        // Perform the first initialization.
        mModelInstance.onCreate(presenter);
    }


    @Override
    public Context getActivityContext() {
        return mView.get().getActivityContext();
    }

    @Override
    public Context getApplicationContext() {
        return mView.get().getApplicationContext();
    }


    @Override
    public void displayLoginResult(boolean result, String failureReason) {
        mView.get().displayLoginResult(result,failureReason);
    }

    @Override
    public void onLoginFinished(boolean successful) {
        //no-op call display result
        displayLoginResult(successful, "Testing the API");
    }


    @Override
    public void onConfigurationChange(MVP.RequiredLoginViewOps view) {

    }

    @Override
    public void onDestroy(boolean isChangingConfigurations) {

    }

    @Override
    public void authenticate() {
        mModelInstance.authenticate();
    }

    @Override
    public boolean isLoggedIn() {
        return mModelInstance.isLoggedIn();
    }

    @Override
    public boolean logout() {
       return mModelInstance.logout();
    }

    /**
     * Return the initialized ProvidedOps instance for use by the
     * application.
     */
    @SuppressWarnings("unchecked")
    public MVP.ProvidedLoginModelOps getModel() {
        return (MVP.ProvidedLoginModelOps) mModelInstance;
    }

}
