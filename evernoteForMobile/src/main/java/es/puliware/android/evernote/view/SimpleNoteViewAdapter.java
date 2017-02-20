package es.puliware.android.evernote.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.evernote.client.android.type.NoteRef;
import es.puliware.android.evernote.R;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by nexus07 on 20/02/17.
 */
public class SimpleNoteViewAdapter  extends RecyclerView.Adapter<SimpleNoteViewAdapter.ViewHolder> {
    private final List<NoteRef> mValues;
    private final boolean mTwoPane;
    private final WeakReference<FragmentActivity> context;

    public SimpleNoteViewAdapter(List<NoteRef> items, boolean mTwoPane, FragmentActivity context) {
        this.mValues = items;
        this.mTwoPane = mTwoPane;
        this.context =  new WeakReference<>(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getTitle());
        // holder.mContentView.setText(mValues.get(position).describeContents());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.getGuid());
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    context.get().getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment).commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.getGuid());

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public NoteRef mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

}
