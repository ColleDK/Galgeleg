package dk.colle.galgeleg;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class StartSpil_frag extends Fragment implements View.OnClickListener {
    GalgeLogik logik;
    ImageView billede;
    EditText gaetBogstav;
    TextView rigtigtOrd;
    Button gaet;
    ListView listView;
    ArrayList<String> gaettedeBogstaverList = new ArrayList<>();
    ArrayAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.activity_start_spil,container,false);
        billede = rod.findViewById(R.id.galgebillede);
        billede.setImageResource(R.drawable.galge);

        System.out.println("Hej");

        gaetBogstav = rod.findViewById(R.id.bogstav);
        gaetBogstav.setText("Indtast bogstav her");

        gaet = rod.findViewById(R.id.gaet);
        gaet.setText("Gæt på bogstav");

        listView = rod.findViewById(R.id.gaettedeBogstaver);

        rigtigtOrd = rod.findViewById(R.id.ordDerBliverGaettetPaa2);

        gaet.setOnClickListener(this);
        gaetBogstav.setOnClickListener(this);

        logik = new GalgeLogik(this);

        return rod;
    }


    @Override
    public void onClick(View v) {
        if (v == gaet) {
            logik.gætBogstav(gaetBogstav.getText().toString());
            gaetBogstav.setText("");
        }
        else if (v == gaetBogstav){
            gaetBogstav.setText("");
        }
    }

    public void setNytGaettedeBogstaver(String nytBogstav){
        gaettedeBogstaverList.add(nytBogstav);
        adapter = new ArrayAdapter(getContext(), R.layout.gaettedebogstaver_element, R.id.bogstavGaettet_element, gaettedeBogstaverList);
        listView.setAdapter(adapter);
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

    public void vundet(){
        MediaPlayer mMediaPlayer = MediaPlayer.create(getContext(),R.raw.cheer);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
        mMediaPlayer = MediaPlayer.create(getContext(),R.raw.youwin);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();


        Bundle bundle = new Bundle();
        bundle.putString("harVundet","Du har vundet");
        bundle.putString("antalGættede",gaettedeBogstaverList.size()+"");
        bundle.putString("rigtigtOrd",rigtigtOrd.getText()+"");

        Fragment fragment = new VundetTabt_Frag();
        fragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.startSpil_fragmentBox,fragment)
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

        getFragmentManager().beginTransaction().replace(R.id.startSpil_fragmentBox,new VundetTabt_Frag())
                .addToBackStack(null)
                .commit();

    }

    public void playWrongSound(){
        /**
         * https://stackoverflow.com/questions/6464080/how-to-play-mp3-file-in-raw-folder-as-notification-sound-alert-in-android
         */
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

}
