package dk.colle.galgeleg;

import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;

public class OrdList_Frag extends Fragment implements View.OnClickListener {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch ordFraDR, predefined, egneOrd;
    private Button start, godkendOrd;
    private TextInputLayout inputOrd;
    private ArrayList<String> egneOrdListe;
    private boolean predefinedSwitch, ordFraDRSwitch, egneOrdSwitch;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.frag_ord_list,container,false);

        predefinedSwitch = true;
        ordFraDRSwitch = true;
        egneOrdSwitch = true;

        egneOrdListe = new ArrayList<>();
        ordFraDR = rod.findViewById(R.id.ordFraDRSwitch);
        predefined = rod.findViewById(R.id.predefinedOrd);
        start = rod.findViewById(R.id.ordListStart);
        inputOrd = rod.findViewById(R.id.ordListEgetOrd);
        godkendOrd = rod.findViewById(R.id.ordListGodkend);
        egneOrd = rod.findViewById(R.id.egneOrdSwitch);
        listView = rod.findViewById(R.id.egneOrdListView);


        start.setOnClickListener(this);
        ordFraDR.setOnClickListener(this);
        predefined.setOnClickListener(this);
        godkendOrd.setOnClickListener(this);
        egneOrd.setOnClickListener(this);

        return rod;
    }


    @Override
    public void onClick(View v) {
        if (v == start) {
            if ((!ordFraDRSwitch && !predefinedSwitch && !egneOrdSwitch) || (!ordFraDRSwitch && !predefinedSwitch && egneOrdListe.size() == 0)) {
                Toast.makeText(getActivity(), "Ingen ord er valgt", Toast.LENGTH_SHORT).show();
            } else {
                assert getFragmentManager() != null;
                Bundle bundle = new Bundle();
                bundle.putBoolean("ordFraDR", ordFraDRSwitch);
                bundle.putBoolean("pre", predefinedSwitch);
                bundle.putBoolean("egneOrd", egneOrdSwitch);
                bundle.putStringArrayList("egneOrdList", egneOrdListe);
                Fragment fragment = new StartSpil_Frag();
                fragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.fragmentView, fragment).commit();
            }
        } else if (v == ordFraDR) {
            ordFraDRSwitch = ordFraDR.isChecked();
            System.out.println(ordFraDRSwitch);
        } else if (v == predefined) {
            predefinedSwitch = predefined.isChecked();
            System.out.println(predefinedSwitch);
        } else if (v == egneOrd) {
            egneOrdSwitch = egneOrd.isChecked();
            System.out.println(egneOrdSwitch);
        } else if (v == godkendOrd) {
            String blabla = inputOrd.getEditText().getText().toString().isEmpty() ? null : inputOrd.getEditText().getText().toString();
            if (blabla != null) {
                StringBuilder sb = new StringBuilder();
                for (char c : blabla.toCharArray()) {
                    if (!Character.isDigit(c)) {
                        sb.append(c);
                    }
                }
                if (sb.length() != blabla.length())
                    Toast.makeText(getActivity(), "Tal er ikke tilladt og er blevet fjernet fra ordet", Toast.LENGTH_SHORT).show();
                blabla = sb.toString();
                if (blabla.contains(" "))
                    Toast.makeText(getActivity(), "Mellemrum er ikke tilladt og er blevet fjernet fra ordet", Toast.LENGTH_SHORT).show();
                blabla = blabla.replace(" ", "");


                // https://javahungry.blogspot.com/2020/06/check-string-contains-special-characters.html
                sb = new StringBuilder();
                String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";
                for (int i = 0; i < blabla.length(); i++) {
                    char ch = blabla.charAt(i);
                    if (!specialCharactersString.contains(Character.toString(ch))) {
                        sb.append(ch);
                    }
                }
                if (sb.length() != blabla.length())
                    Toast.makeText(getActivity(), "Specialtegn er ikke tilladt og er blevet fjernet fra ordet", Toast.LENGTH_SHORT).show();

                if (sb.length() != 0) {
                    egneOrdListe.add(sb.toString());
                    OrdListeElement_Adapter adapter = new OrdListeElement_Adapter(getActivity(), egneOrdListe, this);
                    listView.setAdapter(adapter);
                }
                inputOrd.getEditText().setText("");
            }
        }
    }

    public void setEgneOrdListe(ArrayList<String> ordListe){
        this.egneOrdListe = ordListe;
        OrdListeElement_Adapter adapter = new OrdListeElement_Adapter(getActivity(),egneOrdListe, this);
        listView.setAdapter(adapter);
    }
}