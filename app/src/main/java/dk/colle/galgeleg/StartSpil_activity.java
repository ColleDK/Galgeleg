package dk.colle.galgeleg;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class StartSpil_activity extends AppCompatActivity implements View.OnClickListener {
    private GalgeLogik logik;
    private ImageView billede;
    private EditText gaetBogstav;
    private TextView rigtigtOrd;
    private Button gaet, forkert1, forkert2, forkert3, forkert4, forkert5, forkert6;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_spil);

        billede = findViewById(R.id.galgebillede);
        billede.setImageResource(R.drawable.galge);

        gaetBogstav = findViewById(R.id.bogstav);
        gaetBogstav.setHint("Indtast bogstav her");

        gaet = findViewById(R.id.gaet);
        gaet.setText("Gæt på bogstav");

        forkert1 = findViewById(R.id.forkert1);
        forkert2 = findViewById(R.id.forkert2);
        forkert3 = findViewById(R.id.forkert3);
        forkert4 = findViewById(R.id.forkert4);
        forkert5 = findViewById(R.id.forkert5);
        forkert6 = findViewById(R.id.forkert6);


        rigtigtOrd = findViewById(R.id.ordDerBliverGaettetPaa2);

        gaet.setOnClickListener(this);
        gaetBogstav.setOnClickListener(this);

        // byg galgelogikken gennem builderen
        Intent intent = getIntent();
        boolean isOrdFraDR = intent.getBooleanExtra("ordFraDR",true);
        boolean isEgneOrd = intent.getBooleanExtra("egneOrd",true);
        boolean isPre = intent.getBooleanExtra("pre",true);

        GalgeLogik.GalgeLogikBuilder builder = new GalgeLogik.GalgeLogikBuilder().startSpilFrag(this);

        if (isEgneOrd){
            ArrayList<String> egneOrd = intent.getStringArrayListExtra("egneOrdList");
            builder.addEgneOrdList(egneOrd);
        }
        if (isOrdFraDR){
            builder.ordFraDR();
        }
        if (isPre){
            builder.addNormaleOrd();
        }

        logik = builder.build();
    }


    @Override
    public void onClick(View v) {
        if (v == gaet) {
            logik.gætBogstav(gaetBogstav.getText().toString().toLowerCase());
            gaetBogstav.setText("");
        }
        else if (v == gaetBogstav){
            // ved ikke hvorfor den kun virker når man trykker 2 gange på teksten
            gaetBogstav.setText("");
        }
    }

    // skriv det bogstav man gættede forkert med ind på en knap
    public void setNytGaettedeBogstaver(String nytBogstav, int antalForkerte){
        switch (antalForkerte) {
            case 1:
                forkert1.setText(nytBogstav);
                forkert1.setVisibility(View.VISIBLE);
                break;
            case 2:
                forkert2.setText(nytBogstav);
                forkert2.setVisibility(View.VISIBLE);
                break;
            case 3:
                forkert3.setText(nytBogstav);
                forkert3.setVisibility(View.VISIBLE);
                break;
            case 4:
                forkert4.setText(nytBogstav);
                forkert4.setVisibility(View.VISIBLE);
                break;
            case 5:
                forkert5.setText(nytBogstav);
                forkert5.setVisibility(View.VISIBLE);
                break;
            case 6:
                forkert6.setText(nytBogstav);
                forkert6.setVisibility(View.VISIBLE);
                break;
        }
    }

    // opdater det synlige ord i toppen af skærmen
    public void setRigtigtOrd(String ord){
        rigtigtOrd.setText(ord);
    }

    // sæt det billede der skal vises alt efter hvor mange forkerte man har fået
    public void setBillede(int antalFejl) {
        switch (antalFejl) {
            case 1: billede.setImageResource(R.drawable.forkert1);
            break;
            case 2: billede.setImageResource(R.drawable.forkert2);
            break;
            case 3: billede.setImageResource(R.drawable.forkert3);
            break;
            case 4: billede.setImageResource(R.drawable.forkert4);
            break;
            case 5: billede.setImageResource(R.drawable.forkert5);
            break;
            case 6: billede.setImageResource(R.drawable.forkert6);
            break;
        }
    }

    // spil nogle lyde og send noget information videre til næste skærmbillede
    public void vundet(int antalFejl){
        /**
         * https://stackoverflow.com/questions/6464080/how-to-play-mp3-file-in-raw-folder-as-notification-sound-alert-in-android
         */
        MediaPlayer mMediaPlayer = MediaPlayer.create(this,R.raw.cheer);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
        mMediaPlayer = MediaPlayer.create(this,R.raw.youwin);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();

        Intent intent = new Intent (this, Hoved_activity.class);
        intent.putExtra("EXTRA", "openFragment");
        intent.putExtra("harVundet","Du har vundet").putExtra("rigtigtOrd","Ordet var: " + rigtigtOrd.getText()+"").putExtra("antalGættede","Du gættede kun " + antalFejl +" gang(e) forkert");
        finish();
        startActivity(intent);
    }

    // spil nogle lyde og send noget information videre til næste skærmbillede
    public void tabt(){
        MediaPlayer mMediaPlayer = MediaPlayer.create(this,R.raw.ohno);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
        mMediaPlayer = MediaPlayer.create(this,R.raw.sad);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();

        Intent intent = new Intent (this, Hoved_activity.class);
        intent.putExtra("EXTRA", "openFragment");
        intent.putExtra("harVundet","Du vandt ikke").putExtra("rigtigtOrd","Det rigtige ord var " + rigtigtOrd.getText()+"");
        finish();
        startActivity(intent);
    }

    public void playWrongSound(){
        MediaPlayer mMediaPlayer = MediaPlayer.create(this,R.raw.buzzer);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
    }

    public void playCorrectSound(){
        MediaPlayer mMediaPlayer = MediaPlayer.create(this,R.raw.correct);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
    }

    public void playCheatingSound(){
        MediaPlayer mMediaPlayer = MediaPlayer.create(this,R.raw.cheating);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
    }

    public void playSmokeSound(){
        MediaPlayer mMediaPlayer = MediaPlayer.create(this,R.raw.smoke);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
    }

    public void playDeniedSound(){
        MediaPlayer mMediaPlayer = MediaPlayer.create(this,R.raw.denied);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
    }

    public void playNopeSound(){
        MediaPlayer mMediaPlayer = MediaPlayer.create(this,R.raw.nope);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
    }

}
