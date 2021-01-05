package dk.colle.galgeleg;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.jinatonic.confetti.CommonConfetti;

import java.util.ArrayList;
import java.util.Arrays;

public class VundetTabt_Frag extends Fragment{
    private TextView vundetTabt, gaettetOrd, antalForsoeg;
    private SharedPreferences prefs;
    private ArrayList<String> antalgaettet = new ArrayList<>();
    private ArrayList<String> ord = new ArrayList<>();
    private Bundle gammelBundle;

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.frag_vundettabt_spil,container,false);

        // få bundlen som jeg havde lagt informationer ind i
        Bundle bundle = this.getArguments();

        // den her er nok ligegyldig
        if (bundle != null) {
            vundetTabt = rod.findViewById(R.id.spilSlut_duHarVundetTabt);
            vundetTabt.setText(bundle.getString("harVundet"));

            gaettetOrd = rod.findViewById(R.id.spilSlut_OrdetVar);
            gaettetOrd.setText(bundle.getString("rigtigtOrd"));

            antalForsoeg = rod.findViewById(R.id.spilSlut_AntalForsøg);

            // hvis man har vundet så er antalGættede ikke null
            if (bundle.getString("antalGættede") != null) {
                antalForsoeg.setText(bundle.getString("antalGættede"));

                // hvis man nu havde trykket tilbage (poppet backstacken) så skal den ikke køre den her del igen
                if (bundle != gammelBundle) {
                    CommonConfetti.rainingConfetti(container,new int[] {Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW, Color.BLUE}).stream(7000);
                    gammelBundle = bundle;
                    prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

                    /**
                     * https://stackoverflow.com/questions/5134466/how-to-cast-arraylist-from-list/41136009
                     */

                    String ordSomString = prefs.getString("ord", "");
                    ord = new ArrayList<>(Arrays.asList(ordSomString.split(",")));

                    String antalGættetSomString = prefs.getString("antalgaettet", "");
                    antalgaettet = new ArrayList<>(Arrays.asList(antalGættetSomString.split(",")));

                    for (int j = 1; j < ord.size(); j++) {
                        String replaceLetters = ord.get(j);
                        replaceLetters = replaceLetters.replaceAll("]", "");
                        ord.set(j, replaceLetters);
                        String replaceLetters2 = antalgaettet.get(j);
                        replaceLetters2 = replaceLetters2.replaceAll("]", "");
                        antalgaettet.set(j, replaceLetters2);
                    }


                    antalgaettet.add(bundle.getString("antalGættede"));
                    ord.add(bundle.getString("rigtigtOrd"));

                    /**
                     * https://stackoverflow.com/questions/17469583/setstring-in-android-sharedpreferences-does-not-save-on-force-close
                     * nok ligegyldig nu, men jeg brugte stringset før og den kunne ikke gemme uden at slette forrige set
                     */

                    prefs.edit().remove("ord").apply();
                    prefs.edit().remove("antalgaettet").apply();

                    prefs.edit().putString("ord", ord.toString()).apply();
                    prefs.edit().putString("antalgaettet", antalgaettet.toString()).apply();

                    antalgaettet.clear();
                    ord.clear();
                }
            }
        }

        getFragmentManager().beginTransaction().replace(R.id.spilSlut_nytSpilBox, new StartMenu_knapper_Frag())
                .addToBackStack(null)
                .commit();

        return rod;
    }
}
