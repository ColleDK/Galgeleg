package dk.colle.galgeleg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class StartMenu_knapper_Frag extends Fragment implements View.OnClickListener {
    Button startSpil, indstillinger, highScore, hjaelp;


    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.frag_start_menu_knappper,container,false);
        startSpil = rod.findViewById(R.id.startSpil);
        startSpil.setText("Start spil");

        indstillinger = rod.findViewById(R.id.indstillinger);
        indstillinger.setText("Indstillinger");

        highScore = rod.findViewById(R.id.highScore);
        highScore.setText("High score liste");

        hjaelp = rod.findViewById(R.id.hjaelp);
        hjaelp.setText("Hjælp");

        startSpil.setOnClickListener(this);
        indstillinger.setOnClickListener(this);
        highScore.setOnClickListener(this);
        hjaelp.setOnClickListener(this);

        return rod;
    }

    @Override
    public void onClick(View v) {
        if (v == startSpil){
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(R.id.fragmentView, new StartSpil_frag())
                    .addToBackStack(null)
                    .commit();
        }
        if (v == indstillinger){
            //getFragmentManager().beginTransaction().add().commit();
        }
        if (v == hjaelp){
            //getFragmentManager().beginTransaction().add().commit();
        }
        if (v == highScore){
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(R.id.fragmentView, new HighScore_Frag())
                    .addToBackStack(null)
                    .commit();
        }
    }

}
