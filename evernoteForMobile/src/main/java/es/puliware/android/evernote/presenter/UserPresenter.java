package es.puliware.android.evernote.presenter;

/**
 * Created by nexus on 2/18/17.
 */

import android.os.AsyncTask;
import com.evernote.client.android.EvernoteSession;
import es.puliware.android.evernote.R;
import es.puliware.android.evernote.model.UserModel;

/**
 * This class plays the "Presenter" role in the Model-View-Presenter
 * (MVP) pattern by acting upon the Model and the View, i.e., it
 * retrieves data from the Model (e.g., UserModel) and formats it
 * for display in the View (e.g., LoginActivity).  It
 * implements GenericAsyncTaskOps so its doInBackground() method runs
 * in a background task.  It implements MVP.ProvidedPresenterOps and
 * MVP.RequiredModelOps to decouple the MVP layers.  It implements
 * AcronymResults so it can be the target of asynchronous callback
 * methods from the Model layer.
 */
public class UserPresenter {
    /**
     * Tag for logging
     */
    protected final static String TAG =
            UserPresenter.class.getSimpleName();

    /*Set up an EvernoteSession
    * Define your app credentials (key, secret, and host). See {@linktourl http://dev.evernote.com/documentation/cloud/}
    */
    private static final String CONSUMER_KEY = "nexus06";
    private static final String CONSUMER_SECRET = "4ded854df3b9aa8d";
    private static final EvernoteSession.EvernoteService EVERNOTE_SERVICE = EvernoteSession.EvernoteService.SANDBOX;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{"foo@example.com:hello", "bar@example.com:world"};

    /**
     * Reference to the UserModel.
     */
    private UserModel mUserModel;






}
