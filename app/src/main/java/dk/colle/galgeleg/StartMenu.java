package dk.colle.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartMenu extends AppCompatActivity implements View.OnClickListener {
    Button startspil, indstillinger, highscore, hjaelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        startspil = findViewById(R.id.startSpil);
        indstillinger = findViewById(R.id.indstillinger);
        highscore = findViewById(R.id.highScore);
        hjaelp = findViewById(R.id.hjaelp);

        startspil.setOnClickListener(this);
        indstillinger.setOnClickListener(this);
        highscore.setOnClickListener(this);
        hjaelp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == startspil){
            Intent i = new Intent(this, StartSpil.class);
            startActivity(i);
        }
        if (v == indstillinger){
            Intent i = new Intent(this, Indstillinger.class);
            startActivity(i);
        }
        if (v == hjaelp){
            Intent i = new Intent(this, Hjaelp.class);
            startActivity(i);
        }
        if (v == highscore){
            Intent i = new Intent(this, Highscore.class);
            startActivity(i);
        }




    }
}