package dk.colle.galgeleg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class StartMenu_Frag extends Fragment implements View.OnClickListener {
    Button startSpil, indstillinger, highScore, hjaelp;


    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.activity_start_menu,container,false);
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
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(R.id.fragmentView, new Indstillinger_frag())
                    .addToBackStack(null)
                    .commit();
        }
        if (v == hjaelp){
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(R.id.fragmentView, new Hjaelp_frag())
                    .addToBackStack(null)
                    .commit();
        }
        if (v == highScore){
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(R.id.fragmentView, new Highscore_frag())
                    .addToBackStack(null)
                    .commit();
        }
    }

}
