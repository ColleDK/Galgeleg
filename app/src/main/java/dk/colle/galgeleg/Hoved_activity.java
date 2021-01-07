package dk.colle.galgeleg;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;


public class Hoved_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoved_aktivitet);

        if ("openFragment".equals(getIntent().getStringExtra("EXTRA"))) {
            Intent i = getIntent();

            Bundle bundle = new Bundle();
            bundle.putString("harVundet", i.getStringExtra("harVundet"));
            bundle.putString("rigtigtOrd", i.getStringExtra("rigtigtOrd"));
            bundle.putString("antalGættede", i.getStringExtra("antalGættede"));

            Fragment fragment = new VundetTabt_Frag();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView, fragment).commit();
        } else {

            if (savedInstanceState == null) {
                startActivity(new Intent(this, StartMenu_activity.class));
            }

            setTitle("'Galgeleg'");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
