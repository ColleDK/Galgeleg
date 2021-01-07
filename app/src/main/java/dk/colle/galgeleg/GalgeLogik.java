package dk.colle.galgeleg;


import android.os.SystemClock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class GalgeLogik {
    /** AHT afprøvning er muligeOrd synlig på pakkeniveau */
    private ArrayList<String> muligeOrd = new ArrayList<>();
    private String ordet;
    private ArrayList<String> brugteBogstaver = new ArrayList<String>();
    private String synligtOrd;
    private int antalForkerteBogstaver;
    private int korrektBogstaver;
    private boolean spilletErVundet;
    private boolean spilletErTabt;
    private StartSpil_activity spf;

    // brug builderen til at konstruere logikken
    private GalgeLogik(GalgeLogikBuilder logikBuilder) {
        this.spf = logikBuilder.spf;
        if (logikBuilder.muligeOrd != null) {
            muligeOrd.addAll(logikBuilder.muligeOrd);
        }
        nulstil();
    }

    public void nulstil() {
        brugteBogstaver.clear();
        antalForkerteBogstaver = 0;
        spilletErVundet = false;
        spilletErTabt = false;
        if (muligeOrd.isEmpty()) throw new IllegalStateException("Listen over ord er tom!");
        ordet = muligeOrd.get(new Random().nextInt(muligeOrd.size())).toLowerCase();
        opdaterSynligtOrd();
    }


    private void opdaterSynligtOrd() {
        synligtOrd = "";
        spilletErVundet = true;
        korrektBogstaver=0;
        for (int n = 0; n < ordet.length(); n++) {
            String bogstav = ordet.substring(n, n + 1);
            if (brugteBogstaver.contains(bogstav)) {
                synligtOrd = synligtOrd + bogstav;
                korrektBogstaver++;
            } else {
                synligtOrd = synligtOrd + "*";
                spilletErVundet = false;
            }
        }
        spf.setRigtigtOrd(synligtOrd);
    }

    public void gætBogstav(String bogstav) {
        // some easter eggs (jeg havde lidt sjov med mediaplayeren)
        if (bogstav.equals("showmetheway")) {
            System.out.println(ordet.length()+"\t"+korrektBogstaver);
            if (ordet.length() == korrektBogstaver+1) {
                bogstavHint("remove");
                spf.playNopeSound();
            } else {
                bogstavHint("add");
                spf.playCheatingSound();
            }
        }
        if (bogstav.equals("420")){
            spf.playSmokeSound();
        }
        if (bogstav.equals("plsletmein")){
            spf.playDeniedSound();
        }




        if (bogstav.length() != 1) return;
        if (brugteBogstaver.contains(bogstav)) return;
        if (spilletErVundet || spilletErTabt) return;
        /**
         * https://stackoverflow.com/questions/34855046/check-if-input-is-one-single-letter-java
         */
        if (bogstav.matches("[A-Za-zæøå]{1}")) {
            brugteBogstaver.add(bogstav);

            if (ordet.contains(bogstav)) {
                spf.playCorrectSound();
            } else {
                // Vi gættede på et bogstav der ikke var i ordet.
                antalForkerteBogstaver = antalForkerteBogstaver + 1;
                if (antalForkerteBogstaver > 6) {
                    spf.setRigtigtOrd(ordet);
                    spf.tabt();
                    spilletErTabt = true;
                }
                else{
                    spf.setNytGaettedeBogstaver(bogstav,antalForkerteBogstaver);
                    spf.playWrongSound();
                    spf.setBillede(antalForkerteBogstaver);
                }
            }
            opdaterSynligtOrd();
            if (spilletErVundet){
                spf.vundet(antalForkerteBogstaver);
            }
        }
    }


    // hvis man gerne vil have et hint har jeg sat en secret ind
    public void bogstavHint(String remove){
        for (int i = 0; i < ordet.length(); i++) {
            if (remove.equals("add")) {
                if (!brugteBogstaver.contains(ordet.charAt(i) + "")) {
                    brugteBogstaver.add(ordet.charAt(i) + "");
                    opdaterSynligtOrd();
                    if (spilletErVundet) {
                        spf.vundet(antalForkerteBogstaver);
                    }
                    break;
                }
            }
            else if (remove.equals("remove")){
                if (brugteBogstaver.contains(ordet.charAt(i)+"")){
                    brugteBogstaver.remove(ordet.charAt(i)+"");
                    korrektBogstaver=0;
                }
            }
        }
        opdaterSynligtOrd();
    }





    // Inner builder class
    public static class GalgeLogikBuilder{
        private StartSpil_activity spf;
        private ArrayList<String> muligeOrd = new ArrayList<String>();


        public GalgeLogikBuilder(){

        }

        // sæt startspil_frag
        public GalgeLogikBuilder startSpilFrag(StartSpil_activity spf){
            this.spf = spf;
            return this;
        }

        // hvis man vil indsætte prædefinerede ord
        public GalgeLogikBuilder addNormaleOrd(){
            muligeOrd.add("bil");
            muligeOrd.add("computer");
            muligeOrd.add("programmering");
            muligeOrd.add("motorvej");
            muligeOrd.add("busrute");
            muligeOrd.add("gangsti");
            muligeOrd.add("skovsnegl");
            muligeOrd.add("solsort");
            muligeOrd.add("tyve");
            return this;
        }

        public GalgeLogikBuilder addEgneOrdList(ArrayList<String> egneOrd){
            muligeOrd.addAll(egneOrd);
            return this;
        }

        // hvis man vil hente ord fra dr
        public GalgeLogikBuilder ordFraDR(){
            ArrayList<String> muligeOrdTemp = new ArrayList<>();
            // tjek om der er ord i listen, hvis ja så gem indholdet midlertidigt og fjern ordene
            if (!muligeOrd.isEmpty()){
                muligeOrdTemp = muligeOrd;
                muligeOrd.clear();
            }

            Runnable r = () -> {
                try {
                    hentOrdFraDr();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            new Thread(r).start();

            // vent på at der er blevet tilføjet ord, hvis ikke så vent lidt tid
            while(muligeOrd.isEmpty()){
                SystemClock.sleep(1);
            }

            // tilføj ordene fra midlertidig liste (hvis den er tom så kommer der ikke noget)
            muligeOrd.addAll(muligeOrdTemp);
            return this;
        }

        public GalgeLogik build(){
            return new GalgeLogik(this);
        }


        private void hentOrdFraDr(){
            String data = null;
            try {
                data = hentUrl("https://dr.dk");
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

                this.muligeOrd.clear();
                this.muligeOrd.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static String hentUrl(String url) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            StringBuilder sb = new StringBuilder();
            String linje = br.readLine();
            while (linje != null) {
                sb.append(linje + "\n");
                linje = br.readLine();
            }
            return sb.toString();
        }

    }

}
