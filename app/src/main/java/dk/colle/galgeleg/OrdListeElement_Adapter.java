package dk.colle.galgeleg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class OrdListeElement_Adapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> ord;
    private OrdList_Frag frag;

    public OrdListeElement_Adapter(@NonNull Context context, @NonNull ArrayList<String> ord, @NonNull OrdList_Frag frag){
        super(context,0,ord);
        this.context = context;
        this.ord = ord;
        this.frag = frag;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            // Vi inflater layoutet som vi har lavet i xml
            listItem = LayoutInflater.from(context).inflate(R.layout.ordlist_egneord_listeelement,parent,false);

        String ordText = ord.get(position);
        TextView ordView = listItem.findViewById(R.id.ordListElementText);
        ordView.setText(ordText);

        Button sletKnap = listItem.findViewById(R.id.ordListElementSlet);
        sletKnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ord.remove(position);
                frag.setEgneOrdListe(ord);
            }
        });

        return listItem;
    }

}
