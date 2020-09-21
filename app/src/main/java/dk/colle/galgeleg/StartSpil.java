package dk.colle.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class StartSpil extends AppCompatActivity implements View.OnClickListener {
    Button gaet;
    EditText bogstav;
    ImageView galgebillede;
    ArrayList<String> ord = new ArrayList<>();
    ArrayList<String> rigtigeBogstaver = new ArrayList<>();
    boolean gaettetRigtigt = false;
    int antalForkerte = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_spil);

        ord.add("hej");

        gaet = findViewById(R.id.gaet);
        bogstav = findViewById(R.id.bogstav);
        galgebillede = findViewById(R.id.galgebillede);

        gaet.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if (bogstav.getText().length() != 1) {
            return;
        } else {
            String bogstavGaettet = bogstav.getText().toString();
            for (int i = 0; i < ord.get(0).length(); i++) {
                if (bogstavGaettet.equals(ord.get(0).charAt(i) + "")) {
                    if (rigtigeBogstaver.isEmpty()){
                        rigtigeBogstaver.add(bogstavGaettet);
                        gaettetRigtigt = true;
                    }
                    else {
                        for (String s : rigtigeBogstaver) {
                            System.out.println(s);
                            System.out.println(rigtigeBogstaver.size());
                            gaettetRigtigt = !s.equals(bogstavGaettet);
                        }
                    }
                }
            }

            if (gaettetRigtigt){
                rigtigeBogstaver.add(bogstavGaettet);
            }

            if (!gaettetRigtigt) {
                switch ((antalForkerte % 6)+1) {
                    case 1:
                        galgebillede.setImageResource(R.drawable.forkert1);
                        break;
                    case 2:
                        galgebillede.setImageResource(R.drawable.forkert2);
                        break;
                    case 3:
                        galgebillede.setImageResource(R.drawable.forkert3);
                        break;
                    case 4:
                        galgebillede.setImageResource(R.drawable.forkert4);
                        break;
                    case 5:
                        galgebillede.setImageResource(R.drawable.forkert5);
                        break;
                    case 6:
                        galgebillede.setImageResource(R.drawable.forkert6);
                        break;
                }
                antalForkerte++;
            }
            gaettetRigtigt = false;
        }

        if (rigtigeBogstaver.size()-1 == ord.get(0).length()){
            System.out.println("Du vandt ordet var: " + ord.get(0));
        }
    }
}