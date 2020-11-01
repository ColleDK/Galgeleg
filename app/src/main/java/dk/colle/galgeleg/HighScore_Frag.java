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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class HighScore_Frag extends Fragment implements View.OnClickListener{
    ListView listview;
    SharedPreferences prefs;
    List<String> antalgaettet;
    List<String> ord;

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = i.inflate(R.layout.frag_highscore,container,false);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //prefs.edit().clear().apply();

        String ordSomString = prefs.getString("ord", "");
        ord = Arrays.asList(ordSomString.split(","));


        String antalGættetSomString = prefs.getString("antalgaettet", "");
        antalgaettet = Arrays.asList(antalGættetSomString.split(","));


        if (ord.size() != 1) {
            for (int j = 1; j < ord.size(); j++) {
                String replaceLetters = ord.get(j);
                replaceLetters = replaceLetters.replaceAll("]", "");
                replaceLetters = replaceLetters.substring(ord.size() - j);
                ord.set(j, replaceLetters);

                String replaceLetters2 = antalgaettet.get(j);
                replaceLetters2 = replaceLetters2.replaceAll("]", "");
                replaceLetters2 = replaceLetters2.substring(antalgaettet.size() - j);
                antalgaettet.set(j, replaceLetters2);
            }
        }



        if (ord.size() != 1) {
            for (int j = 1; j < ord.size() - 1; j++) {
                String replaceLetters1 = antalgaettet.get(j);
                String replaceLetters2 = antalgaettet.get(j + 1);
                replaceLetters1 = replaceLetters1.replaceAll(" ", "");
                replaceLetters2 = replaceLetters2.replaceAll(" ", "");

                System.out.println(antalgaettet.get(j));

                if (Integer.parseInt(replaceLetters1.charAt(13) + "") > Integer.parseInt(replaceLetters2.charAt(13) + "")) {
                    String temp1 = antalgaettet.get(j);
                    String temp2 = ord.get(j);

                    antalgaettet.set(j, antalgaettet.get(j + 1));
                    ord.set(j, ord.get(j + 1));

                    antalgaettet.set((j + 1), temp1);
                    ord.set((j + 1), temp2);

                    j = j - 2;
                    if (j < 1) {
                        j = 1;
                    }
                }
            }
        }


        listview = root.findViewById(R.id.highscoreListeView);
        SimpleAdapter adapter;



        /**
         * https://stackoverflow.com/questions/11584398/passing-multiple-listsstring-into-arrayadapter
         * Completely stolen from this site cause i dont know how to set up multiple parameters in a adapter
         */
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = null;
        int count = Math.min(ord.size(),antalgaettet.size());
        for(int k = 1; k < count; k++) {
            map = new HashMap<String, String>();
            map.put("ord", ord.toArray()[k]+"");
            map.put("antalgaettet",antalgaettet.toArray()[k]+"");
            list.add(map);
        }

        adapter = new SimpleAdapter(getActivity(), list, R.layout.highscore_liste_element, new String[] { "ord", "antalgaettet" }, new int[] { R.id.highscore_liste_ordgaettet, R.id.highscore_liste_tid });
        listview.setAdapter(adapter);


        ord = new ArrayList<>();
        antalgaettet = new ArrayList<>();


        return root;
    }


    @Override
    public void onClick(View v) {

    }
}
