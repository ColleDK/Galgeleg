package dk.colle.galgeleg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Inden_StartSpil_Frag extends Fragment implements View.OnClickListener{

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View root = i.inflate(R.layout.frag_startspil,container,false);

        getFragmentManager().beginTransaction().replace(R.id.startSpil_fragmentBox, new StartSpil_frag())
                .commit();

        return root;
    }



        @Override
    public void onClick(View v) {

    }
}
