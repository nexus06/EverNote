package es.puliware.android.evernote.model;

import android.support.v4.app.FragmentActivity;
import com.evernote.client.android.EvernoteSession;
import es.puliware.android.evernote.MVPLogin;

import java.lang.ref.WeakReference;

/**
 * Created by nexus on 2/19/17.
 */

/**
 * This class plays the "Model" role in the Model-View-Presenter (MVP)
 * pattern by defining an interface for providing data that will be
 * acted upon by the "Presenter" and "View" layers in the MVP pattern.
 * It implements the MVP.ProvidedModelOps so it can be created/managed
 * by the GenericModel framework.
 */
public class UserModel extends GenericEverModel implements MVPLogin.ProvidedLoginModelOps{

    /**
     * tag for logging
     */
    protected final static String TAG =
            UserModel.class.getSimpleName();

    /*Set up an EvernoteSession
    * Define your app credentials (key, secret, and host). See {@linktourl http://dev.evernote.com/documentation/cloud/}
    */
    private static final boolean SUPPORT_APP_LINKED_NOTEBOOKS = true;

    private static final EvernoteSession.EvernoteService EVERNOTE_SERVICE = EvernoteSession.EvernoteService.SANDBOX;

    /**
     * A WeakReference used to access methods in the Presenter layer.
     * The WeakReference enables garbage collection.
     */
    private WeakReference<MVPLogin.RequiredPresenterOps> mPresenter;
    private EvernoteSession mEvernoteSession;


    @Override
    public void onCreate(MVPLogin.RequiredPresenterOps presenter) {

        //set weak reference to presenter
        mPresenter = new WeakReference<>(presenter);

        mEvernoteSession = new EvernoteSession.Builder(mPresenter.get().getApplicationContext())
                .setEvernoteService(EVERNOTE_SERVICE)
                .setSupportAppLinkedNotebooks(SUPPORT_APP_LINKED_NOTEBOOKS)
                .build(CONSUMER_KEY, CONSUMER_SECRET)
                .asSingleton();
    }

    @Override
    public void onDestroy(boolean isChangingConfigurations) {

    }

    @Override
    public void authenticate() {
        mEvernoteSession.authenticate((FragmentActivity) mPresenter.get().getActivityContext());
    }

    @Override
    public boolean isLoggedIn() {
        return mEvernoteSession.isLoggedIn();
    }

    @Override
    public boolean logout() {
       return mEvernoteSession.logOut();
    }
}
