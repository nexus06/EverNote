package es.puliware.android.evernote.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.*;

import com.evernote.client.android.EvernoteUtil;
import com.evernote.client.android.asyncclient.EvernoteCallback;
import com.evernote.client.android.type.NoteRef;
import com.evernote.edam.type.Note;
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
public class ItemListActivity extends AppCompatActivity implements MVPNotes.RequiredNotesViewOps, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    /**
     * Tag for logging
     */
    protected final static String TAG = ItemListActivity.class.getSimpleName();
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    /**
     * Provides usernotes-related operations.
     */
    private UserNotesPresenter mNotesPresenter;




    /**
     * mantains notes elements
     */
    private RecyclerView recyclerView;

    SwipeRefreshLayout mProgressView;
    private NoteSortOrder curOrder = NoteSortOrder.CREATED;

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
                curOrder = NoteSortOrder.TITLE;
                mNotesPresenter.listNotesAsync(curOrder);
                return true;

            case R.id.action_order_date:
                curOrder = NoteSortOrder.CREATED;
                mNotesPresenter.listNotesAsync(curOrder);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        initUIControls();

        // Create the UserNotes object one time.
        mNotesPresenter = new UserNotesPresenter();
        mNotesPresenter.onCreate(this);
        mNotesPresenter.listNotesAsync(NoteSortOrder.TITLE);
    }


    private void initUIControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setTitle(getTitle());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(this);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        //mantains notes elements in a list.
        // See {@linktourl https://developer.android.com/training/material/lists-cards.html}
        recyclerView = (RecyclerView) findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);

        mProgressView = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mProgressView.setOnRefreshListener(this);
    }


    public void getNote(String guid, boolean withContent, EvernoteCallback<Note> callback) {
        mNotesPresenter.getNoteAsync(guid, withContent, callback);
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
    public void showProgress() {
        mProgressView.post(new Runnable() {
            @Override public void run() {
                mProgressView.setRefreshing(true);
            }
        });
    }

    @Override
    public void dismisProgress() {
        mProgressView.post(new Runnable() {
            @Override public void run() {
                mProgressView.setRefreshing(false);
            }
        });

    }

    @Override
    public NoteSortOrder getCurrentOrder() {
        return curOrder;
    }

    @Override
    public void onClick(View view) {
        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        //mNotesPresenter.listNotebooksAsync();

        // mNotesPresenter.listNotesAsync(NoteSortOrder.TITLE);
        /*CreateNoteFragment createNoteFragment = new CreateNoteFragment();
        createNoteFragment.show(getSupportFragmentManager(),"createDialog");
*/
        startActivity(CreateGestureNoteActivity.getLaunchIntent(this));


    }

    @Override
    public void onRefresh() {
        mNotesPresenter.listNotesAsync(curOrder);
    }

    public void onAlertDialogPositiveClick(String title, String rawContent) {
        Note note = new Note();
        note.setTitle(title);
        String formattedContent=
        EvernoteUtil.NOTE_PREFIX
                + rawContent
                + EvernoteUtil.NOTE_SUFFIX;
        note.setContent(formattedContent);
        mNotesPresenter.createNoteAsync(note);
    }
}
