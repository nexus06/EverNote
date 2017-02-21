package es.puliware.android.evernote.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.gesture.*;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import es.puliware.android.evernote.R;

import java.util.ArrayList;

/**
 * An activity representing a single screen for create Note by edit text/ gestures
 * <p>
 * in a {@link ItemListActivity}.
 */
public class CreateGestureNoteActivity extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener, View.OnFocusChangeListener {

    //prediction threshold < 1 is too low
    private static final int threshold = 1;
    private GestureLibrary gestureLibrary;
    private EditText title;
    private EditText content;
    private EditText mOutputView;

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, CreateGestureNoteActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_note_gestures);

        if (savedInstanceState == null) {
            //TODO check
        }
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        title.setOnFocusChangeListener(this);
        content.setOnFocusChangeListener(this);
        loadGesturesLibrary();
    }

    private void loadGesturesLibrary() {
        gestureLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
        gestureLibrary.load();
        GestureOverlayView gestureOverlayView = (GestureOverlayView) findViewById(R.id.gestureLayout);
        gestureOverlayView.addOnGesturePerformedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                createNote();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.title:
                mOutputView = title;
                break;
            case R.id.content:
                mOutputView = content;
                break;
            default:
                mOutputView = title;
                break;
        }
    }


    private void createNote() {
        String titleStr = title.getText().toString();
        String contentStr = content.getText().toString();
        if (!titleStr.isEmpty() && !contentStr.isEmpty()) {
            setResult(Activity.RESULT_OK, getResultIntent(titleStr, contentStr));
            finish();
        } else {
            title.setError(getString(R.string.empty));
            content.setError(getString(R.string.empty));
        }
    }

    private Intent getResultIntent(String titleStr, String contentStr) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(ItemListActivity.TITLE_EXTRA, titleStr);
        returnIntent.putExtra(ItemListActivity.CONTENT_EXTRA, contentStr);
        return returnIntent;

    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture);

        Prediction maxPredictionScored = null;
        for (Prediction prediction : predictions) {
            if (prediction.score > threshold && maxPredictionScored != null && maxPredictionScored.score < prediction.score) {
                maxPredictionScored = prediction;
            } else if (maxPredictionScored == null) {
                maxPredictionScored = prediction;
            }

        }
        if (maxPredictionScored != null) {
            mOutputView.append(maxPredictionScored.name);
        }
    }


}
