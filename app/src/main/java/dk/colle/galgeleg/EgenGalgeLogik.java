package dk.colle.galgeleg;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EgenGalgeLogik {
    private ArrayList<String> ord = new ArrayList<>();
    private ArrayList<String> brugteBogstaver = new ArrayList<>();
    private String rigtigtOrd = "";
    public static StartSpil_frag sf;
    int antalForkerte=0;
    private SharedPreferences prefs;
    private Set<String> highScores;


    public EgenGalgeLogik(StartSpil_frag sf){
        prefs = PreferenceManager.getDefaultSharedPreferences(sf.getContext());
        this.sf = sf;
        try {
            hentOrdFraDr();
        } catch (Exception e) {
            System.out.println("ohno");
            e.printStackTrace();
        }
        //rigtigtOrd = ord.get((int) (Math.random() * 6));
        brugteBogstaver.clear();
        //highScores = prefs.getStringSet("highScores",null);
    }


    public void gaetBogstav(String bogstav){
        boolean isCorrect = false;
        if (bogstav.length() != 1){return;}
        for (String s: brugteBogstaver) {
            if (s.equals(bogstav)){
                return;
            }
        }

        for (int i=0; i<rigtigtOrd.length(); i++){
            if (bogstav.equals(rigtigtOrd.charAt(i))){
                isCorrect = true;
            }
        }

        if (isCorrect){
            brugteBogstaver.add(bogstav);
        }
        else{
            sf.setBillede(++antalForkerte);
        }
    }













    public static String hentUrl(String url) throws IOException {
        System.out.println("Henter data fra " + url);
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String linje = br.readLine();
        while (linje != null) {
            sb.append(linje + "\n");
            linje = br.readLine();
        }
        return sb.toString();
    }


    public void hentOrdFraDr() throws Exception {
        String data = hentUrl("https://dr.dk");
        //System.out.println("data = " + data);

        data = data.substring(data.indexOf("<body")). // fjern headere
                replaceAll("<.+?>", " ").toLowerCase(). // fjern tags
                replaceAll("&#198;", "æ"). // erstat HTML-tegn
                replaceAll("&#230;", "æ"). // erstat HTML-tegn
                replaceAll("&#216;", "ø"). // erstat HTML-tegn
                replaceAll("&#248;", "ø"). // erstat HTML-tegn
                replaceAll("&oslash;", "ø"). // erstat HTML-tegn
                replaceAll("&#229;", "å"). // erstat HTML-tegn
                replaceAll("[^a-zæøå]", " "). // fjern tegn der ikke er bogstaver
                replaceAll(" [a-zæøå] "," "). // fjern 1-bogstavsord
                replaceAll(" [a-zæøå][a-zæøå] "," "); // fjern 2-bogstavsord

        System.out.println("data = " + data);
        System.out.println("data = " + Arrays.asList(data.split("\\s+")));
        ord.clear();
        ord.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

        System.out.println("muligeOrd = " + ord);
        //nulstil();
    }

}
