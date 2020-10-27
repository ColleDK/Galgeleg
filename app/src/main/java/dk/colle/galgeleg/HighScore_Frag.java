package dk.colle.galgeleg;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HighScore_Frag extends Fragment implements View.OnClickListener{
    ListView listview;
    SharedPreferences prefs;
    Set<String> tider;
    Set<String> ord;

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = i.inflate(R.layout.frag_highscore,container,false);

        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        tider = prefs.getStringSet("tider",new ArraySet<String>());
        ord = prefs.getStringSet("ord",new ArraySet<String>());

//        ord.add("Biler");
//        tider.add("11:30");
//        prefs.edit().putStringSet("ord",ord).apply();
//        prefs.edit().putStringSet("tider",tider).apply();

        //prefs.edit().clear().apply();

        listview = root.findViewById(R.id.highscoreListeView);
        SimpleAdapter adapter;


        /**
         * https://stackoverflow.com/questions/11584398/passing-multiple-listsstring-into-arrayadapter
         * Completely stolen from this site cause i dont know how to set up multiple parameters in a adapter
         */
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        int count = tider.size();
        for(int k = 0; k < count; k++) {
            map = new HashMap<String, String>();
            map.put("ord", "Gættet ord var: " + ord.toArray()[k]);
            map.put("tider", "Tiden for at gætte var: " + tider.toArray()[k]);
            list.add(map);
        }

        adapter = new SimpleAdapter(getContext(), list, R.layout.highscore_liste_element, new String[] { "ord", "tider" }, new int[] { R.id.highscore_liste_ordgaettet, R.id.highscore_liste_tid });
        listview.setAdapter(adapter);

        return root;
    }


    @Override
    public void onClick(View v) {

    }
}
