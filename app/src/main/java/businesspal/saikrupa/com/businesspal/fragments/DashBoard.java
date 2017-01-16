package businesspal.saikrupa.com.businesspal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import businesspal.saikrupa.com.businesspal.R;
import businesspal.saikrupa.com.businesspal.helper.PieChartActivity;

/**
 * Created by Ndroid on 12/7/2016.
 */

public class DashBoard extends Fragment {

    TextView tvPieChat;

    public DashBoard() {
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
        View v=inflater.inflate(R.layout.fragment_dashboard, container, false);
        tvPieChat=(TextView)v.findViewById(R.id.tvPieChat);
        tvPieChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), PieChartActivity.class);
                startActivity(i);
            }
        });

        return v;
    }

}
