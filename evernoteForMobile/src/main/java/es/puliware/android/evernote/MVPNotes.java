package es.puliware.android.evernote;

import com.evernote.client.android.asyncclient.EvernoteCallback;
import com.evernote.client.android.asyncclient.EvernoteSearchHelper;
import com.evernote.client.android.type.NoteRef;
import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.NoteSortOrder;
import com.evernote.thrift.TException;
import es.puliware.android.evernote.utils.ContextView;
import es.puliware.android.evernote.utils.LoginModelOps;
import es.puliware.android.evernote.utils.LoginPresenterOps;

import java.util.List;

/**
 * Created by nexus on 2/19/17.
 * Defines the interfaces that are
 * required and provided by the layers in the Model-View-Presenter
 * (MVP) pattern.  This design ensures loose coupling between the
 * layers in the app's MVP-based architecture.
 */
public interface MVPNotes {
    /**
     * This interface defines the minimum API needed by the
     * UserNotesPresenter class in the Presenter layer to interact with
     * ItemListActivity in the View layer.  It extends the
     * ContextView interface so the Presentation layer can access
     * Context's defined in the View layer.
     */
    public interface RequiredNotesViewOps extends ContextView {
        /**
         * @param result
         */
        void displayNotesResult(List<NoteRef> result);

        void showError(String cause);

        void showProgress();

        void dismisProgress();

        NoteSortOrder getCurrentOrder();
    }

    /**
     * This interface defines the minimum public API provided by the
     * UserNotesPresenter class in the Presenter layer to the
     * ItemListActivity in the View layer.
     */
    public interface ProvidedNotesPresenterOps extends LoginPresenterOps<RequiredNotesViewOps> {

        boolean logout();

        void listNotesAsync(NoteSortOrder order);

        void createNoteAsync(Note note);

        void setNoteCallback();

        void setSearchCallback();

        void getNoteAsync(String guid, boolean withContent, EvernoteCallback<Note> callback);

    }

    /**
     * This interface defines the minimum API needed by the
     * NotesModel class in the Model layer to interact with
     * NotesPresenter class in the Presenter layer.
     */
    public interface RequiredNotesPresenterOps extends RequiredNotesViewOps {
        EvernoteCallback<EvernoteSearchHelper.Result> getSearchCallback();

        EvernoteCallback<Note> getNoteCallback();
    }

    /**
     * This interface defines the minimum public API provided by the
     * LoginUserModel class in the Model layer to the UserLoginPresenter
     * class in the Presenter layer.  It extends the ModelOps
     * interface, which is parameterized by the
     * MVP.RequiredPresenterOps interface
     */
    public interface ProvidedModelOps extends LoginModelOps<RequiredNotesPresenterOps> {

        /**
         * Check if user already logged in
         *
         * @return false if already logged in
         */
        boolean isLoggedIn();

        boolean logout();

        void listNotesAsync(NoteFilter filter);

        void createNoteAsync(Note note) throws EDAMUserException, EDAMSystemException, TException, EDAMNotFoundException;

        void getNoteAsync(String guid, boolean withContent, EvernoteCallback<Note> callback);

    }
}