package es.puliware.android.evernote.model;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.asyncclient.*;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteList;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;
import es.puliware.android.evernote.MVPNotes;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by nexus on 2/19/17.
 */

/**
 * This class plays the "Model" role in the Model-View-Presenter (MVP)
 * pattern by defining an interface for providing data that will be
 * acted upon by the "Presenter" and "View" layers in the MVP pattern.
 * It implements the MVP.ProvidedModelOps
 */
public class NotesModel implements MVPNotes.ProvidedLoginModelOps{

    /**
     * tag for logging
     */
    protected final static String TAG =
            NotesModel.class.getSimpleName();

    /*Set up an EvernoteSession
    * Define your app credentials (key, secret, and host). See {@linktourl http://dev.evernote.com/documentation/cloud/}
    */
    private static final String CONSUMER_KEY = "nexus06-1400";
    private static final String CONSUMER_SECRET = "0a4b971e6352f650";
    private static final boolean SUPPORT_APP_LINKED_NOTEBOOKS = true;

    private static final EvernoteSession.EvernoteService EVERNOTE_SERVICE = EvernoteSession.EvernoteService.SANDBOX;

    /**
     * A WeakReference used to access methods in the Presenter layer.
     * The WeakReference enables garbage collection.
     */
    private WeakReference<MVPNotes.RequiredPresenterOps> mPresenter;
    private EvernoteSession mEvernoteSession;
    private EvernoteNoteStoreClient noteStoreClient;
    private EvernoteUserStoreClient userData;
    private EvernoteBusinessNotebookHelper businessNoteBookData;
    private EvernoteLinkedNotebookHelper evernoteLinkedNotebookHelper;
    private EvernoteSearchHelper searchHelper;


    @Override
    public void onCreate(MVPNotes.RequiredPresenterOps presenter) {

        //set weak reference to presenter
        mPresenter = new WeakReference<>(presenter);

        mEvernoteSession = new EvernoteSession.Builder(mPresenter.get().getApplicationContext())
                .setEvernoteService(EVERNOTE_SERVICE)
                .setSupportAppLinkedNotebooks(SUPPORT_APP_LINKED_NOTEBOOKS)
                .build(CONSUMER_KEY, CONSUMER_SECRET)
                .asSingleton();

        //Create an EvernoteNoteStoreClient to access primary methods for personal note data
        noteStoreClient = mEvernoteSession.getEvernoteClientFactory().getNoteStoreClient();

        //Create an EvernoteUserStoreClient to access User related methods
        userData = mEvernoteSession.getEvernoteClientFactory().getUserStoreClient();

        searchHelper = mEvernoteSession.getEvernoteClientFactory().getEvernoteSearchHelper();
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

    @Override
    public void listNotesAsync(NoteFilter filter) {
         EvernoteSearchHelper.Search mSearch = new EvernoteSearchHelper.Search().
                setOffset(0).setMaxNotes(Integer.MAX_VALUE).setNoteFilter(filter);
        searchHelper.executeAsync(mSearch, (EvernoteCallback<EvernoteSearchHelper.Result>) mPresenter.get());
    }
}
