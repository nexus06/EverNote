package es.puliware.android.evernote;

import com.evernote.client.android.type.NoteRef;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.type.NoteSortOrder;
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
     * LoginUserPresenter class in the Presenter layer to interact with
     * LoginActivity in the View layer.  It extends the
     * ContextView interface so the Presentation layer can access
     * Context's defined in the View layer.
     */
    public interface RequiredLoginViewOps
            extends ContextView {
        /**
         *
         * @param result
         */
        void displayNotesResult(List<NoteRef> result);

        void showError(String cause);
    }

    /**
     * This interface defines the minimum public API provided by the
     * UserLoginPresenter class in the Presenter layer to the
     * LoginActivity in the View layer.  It extends the
     * PresenterOps interface, which is instantiated by the
     * MVP.RequiredViewOps
     */
    public interface ProvidedLoginPresenterOps
            extends LoginPresenterOps<RequiredLoginViewOps> {

        /**
         *Check if user already logged in
         * @return false if already logged in
         */
        boolean isLoggedIn();

        boolean logout();

        void listNotesAsync(NoteSortOrder order);

    }

    /**
     * This interface defines the minimum API needed by the
     * LoginModel class in the Model layer to interact with
     * LoginPresenter class in the Presenter layer.  Since this
     * interface is identical to the one used by the RequiredLogginViewOps
     * interface it simply extends it.
     */
    public interface RequiredPresenterOps
            extends RequiredLoginViewOps {
    }

    /**
     * This interface defines the minimum public API provided by the
     * LoginUserModel class in the Model layer to the UserLoginPresenter
     * class in the Presenter layer.  It extends the ModelOps
     * interface, which is parameterized by the
     * MVP.RequiredPresenterOps interface
     */
    public interface ProvidedLoginModelOps
            extends LoginModelOps<RequiredPresenterOps> {

        /**
         *Check if user already logged in
         * @return false if already logged in
         */
        boolean isLoggedIn();

        boolean logout();

        void listNotesAsync(NoteFilter filter);

    }
}