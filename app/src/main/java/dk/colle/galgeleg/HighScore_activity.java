package dk.colle.galgeleg;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighScore_activity extends AppCompatActivity {
    private ListView listview;
    private SharedPreferences prefs;
    private List<String> antalgaettet;
    private List<String> ord;
    private Button sletHistorik;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        listview = findViewById(R.id.highscoreListeView);
        sletHistorik = findViewById(R.id.sletHistorik);
        sletHistorik.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            clearHistorik();
        });

        // bruges til at hente data fra cachen
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // hent ord der blev gættet korrekt og antallet forkerte og split strengene til arrays
        String ordSomString = prefs.getString("ord", "");
        ord = Arrays.asList(ordSomString.split(","));


        String antalGættetSomString = prefs.getString("antalgaettet", "");
        antalgaettet = Arrays.asList(antalGættetSomString.split(","));

        // den her del fjerner alle [] fra strengen af og fjerner de mellemrum der er opstået når strengene bliver gemt
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


        // den her del vil sætte det hele i korrekt orden efter antal forkerte
        if (ord.size() != 1) {
            for (int j = 1; j < ord.size() - 1; j++) {
                String replaceLetters1 = antalgaettet.get(j);
                String replaceLetters2 = antalgaettet.get(j + 1);
                replaceLetters1 = replaceLetters1.replaceAll(" ", "");
                replaceLetters2 = replaceLetters2.replaceAll(" ", "");


                if (Integer.parseInt(replaceLetters1.charAt(12) + "") > Integer.parseInt(replaceLetters2.charAt(12) + "")) {
                    String temp1 = antalgaettet.get(j);
                    String temp2 = ord.get(j);

                    antalgaettet.set(j, antalgaettet.get(j + 1));
                    ord.set(j, ord.get(j + 1));

                    antalgaettet.set((j + 1), temp1);
                    ord.set((j + 1), temp2);

                    j -= 2;
                    if (j < 1) {
                        j = 0;
                    }
                }
            }
        }

        // instantier listen der indeholder highscoresne og klargør en adapter til at skrive data ind i listen
        SimpleAdapter adapter;


        /**
         * https://stackoverflow.com/questions/11584398/passing-multiple-listsstring-into-arrayadapter
         * Completely stolen from this site cause i dont know how to set up multiple parameters in a adapter
         */
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = null;
        int count = Math.min(ord.size(), antalgaettet.size());
        for (int k = 1; k < count; k++) {
            map = new HashMap<String, String>();
            map.put("ord", ord.toArray()[k] + "");
            map.put("antalgaettet", antalgaettet.toArray()[k] + "");
            list.add(map);
        }

        adapter = new SimpleAdapter(this, list, R.layout.highscore_liste_element, new String[]{"ord", "antalgaettet"}, new int[]{R.id.highscore_liste_ordgaettet, R.id.highscore_liste_tid});
        listview.setAdapter(adapter);

        ord = new ArrayList<>();
        antalgaettet = new ArrayList<>();
    }


    private void clearHistorik(){
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = null;
        map = new HashMap<String, String>();
        map.put("ord", "");
        map.put("antalgaettet", "");
        list.add(map);
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.highscore_liste_element, new String[]{"ord", "antalgaettet"}, new int[]{R.id.highscore_liste_ordgaettet, R.id.highscore_liste_tid});
        listview.setAdapter(adapter);
    }
}
