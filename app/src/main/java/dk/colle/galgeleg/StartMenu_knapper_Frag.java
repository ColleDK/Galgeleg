package dk.colle.galgeleg;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class StartMenu_knapper_Frag extends Fragment implements View.OnClickListener {
    private Button startSpil, highScore, hjaelp;


    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.frag_start_menu_knappper,container,false);
        startSpil = rod.findViewById(R.id.startSpil);
        startSpil.setText("Start spil");

        highScore = rod.findViewById(R.id.highScore);
        highScore.setText("High score liste");

        hjaelp = rod.findViewById(R.id.hjaelp);
        hjaelp.setText("Hjælp");

        startSpil.setOnClickListener(this);
        highScore.setOnClickListener(this);
        hjaelp.setOnClickListener(this);

        return rod;
    }

    @Override
    public void onClick(View v) {
        if (v == startSpil){
            Intent i = new Intent(getContext(), OrdList_activity.class);
            startActivity(i);
        }
        if (v == hjaelp){
            Intent i = new Intent(getContext(), Hjaelp_activity.class);
            startActivity(i);
        }
        if (v == highScore){
            Intent i = new Intent(getContext(), HighScore_activity.class);
            startActivity(i);
        }
    }

}
