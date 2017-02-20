package es.puliware.android.evernote.view;

import android.content.Context;
import android.content.Intent;
import android.gesture.*;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import es.puliware.android.evernote.R;
import es.puliware.android.evernote.utils.ContextView;

import java.util.ArrayList;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class CreateGestureNoteActivity extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener {

    private GestureLibrary gestureLibrary;
    private EditText output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_note_gestures);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {

        }

        gestureLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if(!gestureLibrary.load()){
            finish();
        }

        GestureOverlayView gestureOverlayView = (GestureOverlayView) findViewById(R.id.gestureLayout);

        gestureOverlayView.addOnGesturePerformedListener(this);

        output = (EditText)findViewById(R.id.title);



    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture);
        //inferior a 1 demasiado baja
        int threshold = 1;
        Prediction maxPredictionScored = null;
        for(Prediction prediction: predictions){
            if(prediction.score > threshold && maxPredictionScored!=null && maxPredictionScored.score<prediction.score){
                maxPredictionScored = prediction;
            }else if (maxPredictionScored==null){
                maxPredictionScored = prediction;
            }

        }
        if(maxPredictionScored!=null){
            output.append(maxPredictionScored.name);
        }
    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, CreateGestureNoteActivity.class);
    }
}
