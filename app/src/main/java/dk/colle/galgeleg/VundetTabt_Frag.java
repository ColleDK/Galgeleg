package dk.colle.galgeleg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class VundetTabt_Frag extends Fragment implements View.OnClickListener {
    TextView vundetTabt, gaettetOrd, antalForsoeg;

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.frag_vundettabt_spil,container,false);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            vundetTabt = rod.findViewById(R.id.spilSlut_duHarVundetTabt);
            vundetTabt.setText(bundle.getString("harVundet"));

            gaettetOrd = rod.findViewById(R.id.spilSlut_OrdetVar);
            gaettetOrd.setText("Ordet var: " + bundle.getString("rigtigtOrd"));

            antalForsoeg = rod.findViewById(R.id.spilSlut_AntalForsøg);
            antalForsoeg.setText("Du gættede " + bundle.getString("antalGættede") + " gange forkert");
        }

        return rod;
    }



        @Override
    public void onClick(View v) {

    }
}
