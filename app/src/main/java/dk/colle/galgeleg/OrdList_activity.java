package dk.colle.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;

public class OrdList_activity extends AppCompatActivity implements View.OnClickListener {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch ordFraDR, predefined, egneOrd;
    private Button start, godkendOrd;
    private TextInputLayout inputOrd;
    private ArrayList<String> egneOrdListe;
    private boolean predefinedSwitch, ordFraDRSwitch, egneOrdSwitch;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ord_list);

        predefinedSwitch = true;
        ordFraDRSwitch = true;
        egneOrdSwitch = true;

        egneOrdListe = new ArrayList<>();
        ordFraDR = findViewById(R.id.ordFraDRSwitch);
        predefined = findViewById(R.id.predefinedOrd);
        start = findViewById(R.id.ordListStart);
        inputOrd = findViewById(R.id.ordListEgetOrd);
        godkendOrd = findViewById(R.id.ordListGodkend);
        egneOrd = findViewById(R.id.egneOrdSwitch);
        listView = findViewById(R.id.egneOrdListView);


        start.setOnClickListener(this);
        ordFraDR.setOnClickListener(this);
        predefined.setOnClickListener(this);
        godkendOrd.setOnClickListener(this);
        egneOrd.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == start) {
            if ((!ordFraDRSwitch && !predefinedSwitch && !egneOrdSwitch) || (!ordFraDRSwitch && !predefinedSwitch && egneOrdListe.size() == 0)) {
                Toast.makeText(this, "Ingen ord er valgt", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(this, StartSpil_activity.class)
                        .putExtra("ordFraDR",ordFraDRSwitch)
                        .putExtra("egneOrd",egneOrdSwitch)
                        .putExtra("pre",predefinedSwitch)
                        .putExtra("egneOrdList",egneOrdListe);
                startActivity(i);
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
                    Toast.makeText(this, "Tal er ikke tilladt og er blevet fjernet fra ordet", Toast.LENGTH_SHORT).show();
                blabla = sb.toString();
                if (blabla.contains(" "))
                    Toast.makeText(this, "Mellemrum er ikke tilladt og er blevet fjernet fra ordet", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(this, "Specialtegn er ikke tilladt og er blevet fjernet fra ordet", Toast.LENGTH_SHORT).show();

                if (sb.length() != 0) {
                    egneOrdListe.add(sb.toString());
                    OrdListeElement_Adapter adapter = new OrdListeElement_Adapter(this, egneOrdListe, this);
                    listView.setAdapter(adapter);
                }
                inputOrd.getEditText().setText("");
            }
        }
    }

    public void setEgneOrdListe(ArrayList<String> ordListe){
        this.egneOrdListe = ordListe;
        OrdListeElement_Adapter adapter = new OrdListeElement_Adapter(this,egneOrdListe, this);
        listView.setAdapter(adapter);
    }
}