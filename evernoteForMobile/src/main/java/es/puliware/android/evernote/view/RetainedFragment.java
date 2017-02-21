package es.puliware.android.evernote.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.HashMap;

/*Retains and manages state information between runtime configuration
* changes to an Activity. Plays the role of the "Originator" in the
* Memento pattern.*/
public class RetainedFragment extends Fragment {

    /**
     * Maps keys to objects.
     */
    private HashMap<String, Object> mData =
            new HashMap<String, Object>();

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public void put(String key, Object value) {
        mData.put(key,value);
    }

    public <T> T get(String key) {
        return (T) mData.get(key);
    }
}