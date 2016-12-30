package businesspal.saikrupa.com.businesspal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import businesspal.saikrupa.com.businesspal.R;

/**
 * Created by Ndroid on 12/14/2016.
 */

public class Receivable extends Fragment {

    public Receivable() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_receivable, container, false);
    }

}