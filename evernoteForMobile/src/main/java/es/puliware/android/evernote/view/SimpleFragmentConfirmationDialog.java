package es.puliware.android.evernote.view;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import es.puliware.android.evernote.R;

/**
 * Creates confirmation Dialog based in fragments and performs ok cancel
 * actions
 * To show use show() method with Activity#getSupportFragmentDialog() and the
 * name of the class to show.
 * TODO: more JavaDoc
 */
public abstract class SimpleFragmentConfirmationDialog extends DialogFragment
		implements DialogInterface.OnClickListener {

	private int title;
	private int message;

	public SimpleFragmentConfirmationDialog() {
		super();
	}
	
	public SimpleFragmentConfirmationDialog(final int titleId,
											final int messageId) {
		super();
		this.title = titleId;
		this.message = messageId;
	}

	public SimpleFragmentConfirmationDialog(final int titleId,
											final int messageId, final int iconId) {
		super();
		this.title = titleId;
		this.message = messageId;
	}


	/** Implement the cancel behaviour */
	public abstract void cancel();

	/** Implement the ok behaviour */
	public abstract void ok();

	@Override
	public void onClick(final DialogInterface dialog, final int whichButton) {
		if (whichButton == DialogInterface.BUTTON_POSITIVE) {
			ok();
		} else if (whichButton == DialogInterface.BUTTON_NEGATIVE) {
			cancel();
		}
	}

	@Override
	public android.app.Dialog onCreateDialog(final Bundle savedInstanceState) {
		return new AlertDialog.Builder(getActivity())
				.setTitle(this.title).setMessage(this.message)
				.setPositiveButton(R.string.yes, this)
				.setNegativeButton(R.string.no, this).create();
	
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		getDialog().getWindow()
				.getAttributes().windowAnimations = R.style.DialogAnimation;
	}
}
