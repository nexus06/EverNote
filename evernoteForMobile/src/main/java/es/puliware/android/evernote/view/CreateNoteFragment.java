package es.puliware.android.evernote.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Html;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import es.puliware.android.evernote.R;

/**
 *
 */
public class CreateNoteFragment extends AppCompatDialogFragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private EditText title;
    private EditText content;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CreateNoteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.create_note, container, false);
        title = (EditText)rootView.findViewById(R.id.title);
        content = (EditText)rootView.findViewById(R.id.content);

        // Show the dummy content as text in a TextView.
        Button positiveButton = (Button) rootView.findViewById(R.id.create);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!title.getText().toString().isEmpty()&&!content.getText().toString().isEmpty()){
                    ((ItemListActivity)getActivity()).onAlertDialogPositiveClick(title.getText().toString(), content.getText().toString());
                    dismiss();
                }else {
                    title.setError(getString(R.string.empty));
                    content.setError(getString(R.string.empty));
                }


            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        /*getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/
        super.onResume();
    }
}
