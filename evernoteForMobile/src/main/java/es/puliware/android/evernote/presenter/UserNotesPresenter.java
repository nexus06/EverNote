package es.puliware.android.evernote.presenter;

/**
 * Created by nexus on 2/18/17.
 */

import android.content.Context;
import android.util.Log;
import com.evernote.client.android.asyncclient.EvernoteCallback;
import com.evernote.client.android.asyncclient.EvernoteSearchHelper;
import com.evernote.client.android.type.NoteRef;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.type.NoteSortOrder;
import com.evernote.edam.type.Notebook;
import es.puliware.android.evernote.MVPNotes;
import es.puliware.android.evernote.model.NotesModel;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * This class plays the "Presenter" role in the Model-View-Presenter
 * (MVP) pattern by acting upon the Model and the View, i.e., it
 * retrieves data from the Model (e.g., UserModel) and
 * display in the View (e.g., LoginActivity).  It
 * implements  It implements MVP.ProvidedLoginPresenterOps and
 * MVP.RequiredPresenterOps to decouple the MVP layers.
 *
 */
public class UserNotesPresenter implements MVPNotes.ProvidedLoginPresenterOps, MVPNotes.RequiredPresenterOps, EvernoteCallback<EvernoteSearchHelper.Result>{
    /**
     * Tag for logging
     */
    protected final static String TAG = UserNotesPresenter.class.getSimpleName();


    /**
     * A WeakReference used to access methods in the View layer.  The
     * WeakReference enables garbage collection.
     */
    private WeakReference<MVPNotes.RequiredLoginViewOps> mView;

    private NotesModel mModelInstance;


    @Override
    public void onCreate(MVPNotes.RequiredLoginViewOps view) {
        // Set the WeakReference.
        mView = new WeakReference<>(view);

        // Perform the first initialization
        // passing in the UserModel class to
        // instantiate and "this" to provide
        // Model with this MVP.RequiredModelOps
        // instance.
        // Create the ModelType object.
        try {
            initializeModel(NotesModel.class, this);
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
    private void initializeModel(Class<NotesModel> opsType,
                                 MVPNotes.RequiredPresenterOps presenter)
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
    public void onConfigurationChange(MVPNotes.RequiredLoginViewOps view) {

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

    @Override
    public void listNotesAsync(NoteSortOrder order) {
        NoteFilter noteFilter = new NoteFilter();
        noteFilter.setOrder(order.getValue());
        mModelInstance.listNotesAsync(noteFilter);
    }

    /**
     * Return the initialized ProvidedOps instance for use by the
     * application.
     */
    @SuppressWarnings("unchecked")
    public MVPNotes.ProvidedLoginModelOps getModel() {
        return (MVPNotes.ProvidedLoginModelOps) mModelInstance;
    }

    @Override
    public void onSuccess(EvernoteSearchHelper.Result result) {
        displayNotesResult(result.getAllAsNoteRef());
    }

    @Override
    public void onException(Exception e) {
        Log.e(TAG, Log.getStackTraceString(e));
        showError(e.getMessage());
    }

    @Override
    public void displayNotesResult(List<NoteRef> result) {
        mView.get().displayNotesResult(result);
    }

    @Override
    public void showError(String cause) {
        mView.get().showError(cause);
    }
}
