package dk.colle.galgeleg;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;


public class StartSpil_frag extends Fragment implements View.OnClickListener {
    GalgeLogik logik = new GalgeLogik();
    ImageView billede;
    TableLayout gaettedeBogstaver;
    EditText gaetBogstav;
    Button gaet;
    TextView rigtigtOrd,ekstraText;


    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.activity_start_spil,container,false);
        billede = rod.findViewById(R.id.galgebillede);
        billede.setImageResource(R.drawable.galge);

        gaettedeBogstaver = rod.findViewById(R.id.gaettedeBogstaver);

        gaetBogstav = rod.findViewById(R.id.bogstav);
        gaetBogstav.setText("Indtast bogstav her");

        gaet = rod.findViewById(R.id.gaet);
        gaet.setText("Gæt på bogstav");

        rigtigtOrd = rod.findViewById(R.id.rigtigtOrd);
        rigtigtOrd.setText("");

        ekstraText = rod.findViewById(R.id.ekstraText);
        ekstraText.setText("Det ord du gætter på er: ");

        gaet.setOnClickListener(this);

        return rod;
    }


    @Override
    public void onClick(View v) {
        logik.gætBogstav(gaetBogstav.getText().toString());
    }
}
