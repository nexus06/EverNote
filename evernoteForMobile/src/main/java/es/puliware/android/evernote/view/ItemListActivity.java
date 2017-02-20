package es.puliware.android.evernote.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.*;
import android.widget.TextView;

import com.evernote.client.android.type.NoteRef;
import com.evernote.edam.type.NoteSortOrder;
import es.puliware.android.evernote.MVPNotes;
import es.puliware.android.evernote.R;

import es.puliware.android.evernote.presenter.UserNotesPresenter;

import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity implements MVPNotes.RequiredNotesViewOps, View.OnClickListener {

    /**
     * Tag for logging
     */
    protected final static String TAG = ItemListActivity.class.getSimpleName();
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private UserNotesPresenter mNotesPresenter;
    private RecyclerView recyclerView;

    public static Intent getLaunchIntent(Context context){
        return new Intent(context, ItemListActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order_title:
                mNotesPresenter.listNotesAsync(NoteSortOrder.TITLE);
                return true;

            case R.id.action_order_date:
                mNotesPresenter.listNotesAsync(NoteSortOrder.UPDATED);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setTitle(getTitle());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(this);


        // Create the UserNotes object one time.
        mNotesPresenter = new UserNotesPresenter();
        mNotesPresenter.onCreate(this);

        recyclerView = (RecyclerView) findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        mNotesPresenter.listNotesAsync(NoteSortOrder.TITLE);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
       // recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void displayNotesResult(List<NoteRef> result) {
        Log.d(TAG, "successed getting notes--->"+result.size());
        recyclerView.setAdapter(new SimpleNoteViewAdapter(result,mTwoPane,this));
        recyclerView.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void showError(String cause) {
        Snackbar.make(recyclerView, cause,
                Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    public void onClick(View view) {
        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        //mNotesPresenter.listNotebooksAsync();
         mNotesPresenter.listNotesAsync(NoteSortOrder.TITLE);
    }

}
