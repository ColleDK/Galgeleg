package dk.colle.galgeleg;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class StartSpil_Frag extends Fragment implements View.OnClickListener {
    private GalgeLogik logik;
    private ImageView billede;
    private EditText gaetBogstav;
    private TextView rigtigtOrd;
    private Button gaet, forkert1, forkert2, forkert3, forkert4, forkert5, forkert6;


    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.frag_start_spil,container,false);
        billede = rod.findViewById(R.id.galgebillede);
        billede.setImageResource(R.drawable.galge);

        gaetBogstav = rod.findViewById(R.id.bogstav);
        gaetBogstav.setText("Indtast bogstav her");

        gaet = rod.findViewById(R.id.gaet);
        gaet.setText("Gæt på bogstav");

        forkert1 = rod.findViewById(R.id.forkert1);
        forkert2 = rod.findViewById(R.id.forkert2);
        forkert3 = rod.findViewById(R.id.forkert3);
        forkert4 = rod.findViewById(R.id.forkert4);
        forkert5 = rod.findViewById(R.id.forkert5);
        forkert6 = rod.findViewById(R.id.forkert6);


        rigtigtOrd = rod.findViewById(R.id.ordDerBliverGaettetPaa2);

        gaet.setOnClickListener(this);
        gaetBogstav.setOnClickListener(this);

        logik = new GalgeLogik.GalgeLogikBuilder().startSpilFrag(this).ordFraDR().addNormaleOrd().build();

        return rod;
    }


    @Override
    public void onClick(View v) {
        if (v == gaet) {
            logik.gætBogstav(gaetBogstav.getText().toString().toLowerCase());
            gaetBogstav.setText("");
        }
        else if (v == gaetBogstav){
            gaetBogstav.setText("");
        }
    }

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


    public void setRigtigtOrd(String ord){
        rigtigtOrd.setText(ord);
    }


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

    public void vundet(int antalFejl){
        /**
         * https://stackoverflow.com/questions/6464080/how-to-play-mp3-file-in-raw-folder-as-notification-sound-alert-in-android
         */
        MediaPlayer mMediaPlayer = MediaPlayer.create(getContext(),R.raw.cheer);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
        mMediaPlayer = MediaPlayer.create(getContext(),R.raw.youwin);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();


        /**
         * https://stackoverflow.com/questions/16036572/how-to-pass-values-between-fragments
         */
        Bundle bundle = new Bundle();
        bundle.putString("harVundet","Du har vundet");
        bundle.putString("rigtigtOrd","Ordet var: " + rigtigtOrd.getText()+"");
        bundle.putString("antalGættede","Du gættede kun " + antalFejl +" gang(e) forkert");

        Fragment fragment = new VundetTabt_Frag();
        fragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.fragmentView,fragment)
                .addToBackStack(null)
                .commit();
    }

    public void tabt(){
        MediaPlayer mMediaPlayer = MediaPlayer.create(getContext(),R.raw.ohno);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
        mMediaPlayer = MediaPlayer.create(getContext(),R.raw.sad);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();

        Bundle bundle = new Bundle();
        bundle.putString("harVundet","Du vandt ikke");
        bundle.putString("rigtigtOrd","Det rigtige ord var " + rigtigtOrd.getText()+"");

        Fragment fragment = new VundetTabt_Frag();
        fragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.fragmentView,fragment)
                .addToBackStack(null)
                .commit();

    }

    public void playWrongSound(){
        MediaPlayer mMediaPlayer = MediaPlayer.create(getContext(),R.raw.buzzer);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
    }

    public void playCorrectSound(){
        MediaPlayer mMediaPlayer = MediaPlayer.create(getContext(),R.raw.correct);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
    }

    public void playCheatingSound(){
        MediaPlayer mMediaPlayer = MediaPlayer.create(getContext(),R.raw.cheating);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
    }

    public void playSmokeSound(){
        MediaPlayer mMediaPlayer = MediaPlayer.create(getContext(),R.raw.smoke);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
    }

    public void playDeniedSound(){
        MediaPlayer mMediaPlayer = MediaPlayer.create(getContext(),R.raw.denied);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
    }

    public void playNopeSound(){
        MediaPlayer mMediaPlayer = MediaPlayer.create(getContext(),R.raw.nope);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
    }

}
