package es.puliware.android.evernote.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.evernote.client.android.asyncclient.EvernoteCallback;
import com.evernote.client.android.type.NoteRef;
import com.evernote.edam.type.Note;
import es.puliware.android.evernote.R;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by nexus07 on 20/02/17.
 */
public class SimpleNoteViewAdapter extends RecyclerView.Adapter<SimpleNoteViewAdapter.ViewHolder> {

    /**
     * Tag for logging
     */
    protected final static String TAG = SimpleNoteViewAdapter.class.getSimpleName();
    private static EvernoteCallback<Note> callBack;
    private final List<NoteRef> mValues;
    private final boolean mTwoPane;
    private final WeakReference<FragmentActivity> context;

    public SimpleNoteViewAdapter(List<NoteRef> items, boolean mTwoPane, FragmentActivity context) {
        this.mValues = items;
        this.mTwoPane = mTwoPane;
        this.context = new WeakReference<>(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mDateView.setText(mValues.get(position).getTitle());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callBack = new EvernoteCallback<Note>() {
                    @Override
                    public void onSuccess(Note note) {
                        ((ItemListActivity) context.get()).dismisProgress();
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, note.getContent());
                            ItemDetailFragment fragment = new ItemDetailFragment();
                            fragment.setArguments(arguments);
                            (context.get()).
                                    getSupportFragmentManager().beginTransaction().
                                    replace(R.id.item_detail_container, fragment).commit();
                        } else {
                            Intent intent = new Intent(context.get(), ItemDetailActivity.class);
                            intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, note.getContent());
                            context.get().startActivity(intent);
                        }

                    }

                    @Override
                    public void onException(Exception e) {
                        ((ItemListActivity) context.get()).dismisProgress();
                        ((ItemListActivity) context.get()).showError(e.getMessage());
                        Log.e(TAG, Log.getStackTraceString(e));

                    }
                };

                ((ItemListActivity) context.get()).getNote(holder.mItem.getGuid(), true, SimpleNoteViewAdapter.callBack);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDateView;
        public final TextView mTitleView;
        public NoteRef mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDateView = (TextView) view.findViewById(R.id.date);
            mTitleView = (TextView) view.findViewById(R.id.title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }

}
