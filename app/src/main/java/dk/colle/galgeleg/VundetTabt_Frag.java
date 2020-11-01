package dk.colle.galgeleg;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class VundetTabt_Frag extends Fragment implements View.OnClickListener {
    TextView vundetTabt, gaettetOrd, antalForsoeg;
    SharedPreferences prefs;
    ArrayList<String> antalgaettet = new ArrayList<>();
    ArrayList<String> ord = new ArrayList<>();

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.frag_vundettabt_spil,container,false);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            vundetTabt = rod.findViewById(R.id.spilSlut_duHarVundetTabt);
            vundetTabt.setText(bundle.getString("harVundet"));

            gaettetOrd = rod.findViewById(R.id.spilSlut_OrdetVar);
            gaettetOrd.setText(bundle.getString("rigtigtOrd"));

            antalForsoeg = rod.findViewById(R.id.spilSlut_AntalForsøg);
            if (antalForsoeg != null) {
                antalForsoeg.setText(bundle.getString("antalGættede"));

                prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

                /**
                 * https://stackoverflow.com/questions/5134466/how-to-cast-arraylist-from-list/41136009
                 */

                String ordSomString = prefs.getString("ord", "");
                //assert ordSomString != null;
                ord = new ArrayList<>(Arrays.asList(ordSomString.split(",")));

                String antalGættetSomString = prefs.getString("antalgaettet", "");
                //assert antalGættetSomString != null;
                antalgaettet = new ArrayList<>(Arrays.asList(antalGættetSomString.split(",")));

                for (int j=1; j<ord.size(); j++) {
                    String replaceLetters  = ord.get(j);
                    replaceLetters = replaceLetters.replaceAll("]","");
                    ord.set(j, replaceLetters);
                    String replaceLetters2  = antalgaettet.get(j);
                    replaceLetters2 = replaceLetters2.replaceAll("]","");
                    antalgaettet.set(j, replaceLetters2);
                }


                antalgaettet.add(bundle.getString("antalGættede"));
                ord.add(bundle.getString("rigtigtOrd"));

                /**
                 * https://stackoverflow.com/questions/17469583/setstring-in-android-sharedpreferences-does-not-save-on-force-close
                 */

                prefs.edit().remove("ord").apply();
                prefs.edit().remove("antalgaettet").apply();

                prefs.edit().putString("ord",ord.toString()).apply();
                prefs.edit().putString("antalgaettet",antalgaettet.toString()).apply();

                antalgaettet.clear();
                ord.clear();
            }
        }

        getFragmentManager().beginTransaction().replace(R.id.spilSlut_nytSpilBox, new StartMenu_knapper_Frag())
                .addToBackStack(null)
                .commit();


        return rod;
    }



        @Override
    public void onClick(View v) {

    }
}
