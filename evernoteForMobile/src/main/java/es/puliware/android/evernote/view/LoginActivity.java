package es.puliware.android.evernote.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.evernote.client.android.login.EvernoteLoginFragment;
import es.puliware.android.evernote.MVP;
import es.puliware.android.evernote.R;
import es.puliware.android.evernote.presenter.UserLoginPresenter;
import es.puliware.android.evernote.utils.ContextView;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements
        ContextView,MVP.RequiredLoginViewOps,
        EvernoteLoginFragment.ResultCallback, View.OnClickListener{

    @Override
    public Context getActivityContext() {

        return this;
    }

    /**
     * Get the Application Context.
     */
    public Context getApplicationContext(){
        return getApplication().getApplicationContext();

    }


    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Provides userlogin-related operations.
     */
    private UserLoginPresenter mLoginPresenter;

    // UI references.
    private ScrollView mLoginLayout;
    private View mProgressView;
    private Button mLoginBtn;
    private Button mExitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Create the Userlogin object one time.
        mLoginPresenter = new UserLoginPresenter();
        mLoginPresenter.onCreate(this);
        attemptLogin();
        initUIControls();
    }

    private void initUIControls() {
       mLoginLayout = (ScrollView) findViewById(R.id.login_form);
       mProgressView = findViewById(R.id.bg_progress);
       mLoginBtn = (Button) findViewById(R.id.sign_in_button);
       mExitBtn = (Button) findViewById(R.id.exit_button);

        mLoginBtn.setOnClickListener(this);
        mExitBtn.setOnClickListener(this);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if(mLoginPresenter.isLoggedIn()){
            mLoginPresenter.displayLoginResult(true, "testing API");
            //TODO remove at end version
            mLoginPresenter.logout();

        }else {
            mLoginPresenter.authenticate();
        }
    }



    @Override
    public void displayLoginResult(boolean successful, String failureReason) {
        if (successful) {
            Toast.makeText(this,"login succesed", Toast.LENGTH_LONG).show();
            startActivity(ItemListActivity.getLaunchIntent(this));
        } else {
            mLoginLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoginFinished(boolean successful) {
        //cal presenter to manage received info
        mLoginPresenter.onLoginFinished(successful);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:
                attemptLogin();
                break;
            case R.id.exit_button:
                finish();
                break;
            default:
                break;
        }

    }
}

