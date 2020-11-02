package dk.colle.galgeleg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class StartMenu_Frag extends Fragment{

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.frag_start_menu,container,false);

        getFragmentManager().beginTransaction().replace(R.id.start_menu_knapper_box, new StartMenu_knapper_Frag())
                .commit();

        return rod;
    }

}
