package dk.colle.galgeleg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Hjaelp_Frag extends Fragment{
    TextView howToPlay;

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.frag_hjaelp,container,false);

        String howToPlay1 = "Galgeleg er et spil, hvor man gætter et hemmeligt ord (som kan ses øverst på skærmen når spillet er i gang)." +
                "\nNår du skal gætte et bogstav i ordet, så skal du indtaste det i feltet til venstre for \"Gæt på bogstav\"-knappen. " +
                "\nFor at finde ud af om du svarede korrekt eller forkert kan du finde frem til det ved at " +
                "\n\n1. Lytte til lyden der bliver afspillet (en buzzer-lyd for forkert og en ping-lyd hvis det er korrekt), " +
                "\n2. Tjekke om figuren på billedet har fået et ekstra legeme (hvis du svarer forkert bliver der tegnet et nyt led), " +
                "\n3. Tjek lige under billedet efter dit bogstav (her vil alle dine forkerte svar blive vist)" +
                "\n4. Tjekke ordet du gætter på (hvis du har svaret korrekt vil dit bogstav blive synligt i ordet du gætter på)" +
                "\n\nHvis du svarer forkert 7 gange uden at have vundet, så har du tabt. Hertil vil ordet blive afsløret";

        howToPlay = rod.findViewById(R.id.hjaelp_how2play);
        howToPlay.setText(howToPlay1);


        return rod;
    }
}
