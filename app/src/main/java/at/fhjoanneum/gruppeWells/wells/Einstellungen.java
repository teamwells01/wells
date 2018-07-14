package at.fhjoanneum.gruppeWells.wells;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class Einstellungen extends Fragment {


    public Einstellungen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_einstellungen, container, false);

        Button Grundeinstellungen = view.findViewById(R.id.Grundeinstellungen);
        Grundeinstellungen.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                grund_einstellung grund_einstellungfragment = new grund_einstellung();
                FragmentManager manager = getFragmentManager();
                try {
                    manager.beginTransaction().replace(R.id.mainLayout, grund_einstellungfragment, grund_einstellungfragment.getTag()).commit();
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), getString(R.string.error),
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        Button Benachrichtigung = view.findViewById(R.id.Benachrichtigung);
        Benachrichtigung.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Benachrichtigungseinstellungen benachrichtigunggfragment = new Benachrichtigungseinstellungen();
                FragmentManager manager = getFragmentManager();
                try {
                    manager.beginTransaction().replace(R.id.mainLayout, benachrichtigunggfragment, benachrichtigunggfragment.getTag()).commit();
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), getString(R.string.error),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        Button PWAendern = view.findViewById(R.id.PWAendern);
        PWAendern.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                PWaendern pwaendernfragement = new PWaendern();
                FragmentManager manager = getFragmentManager();
                try {
                    manager.beginTransaction().replace(R.id.mainLayout, pwaendernfragement, pwaendernfragement.getTag()).commit();
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), getString(R.string.error),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        Button loeschen_button = view.findViewById(R.id.loeschen);
        loeschen_button.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                loeschen loeschen_fragment = new loeschen();
                FragmentManager manager = getFragmentManager();
                try {
                    manager.beginTransaction().replace(R.id.mainLayout, loeschen_fragment, loeschen_fragment.getTag()).commit();
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), getString(R.string.error),
                            Toast.LENGTH_LONG).show();
                }
            }
        });


        return view;


    }

//        public void OnClickDelete (View view){
////            Intent intent = new Intent(this, loeschen.class);
////            startActivity(intent);
//            //  startActivity(new Intent(this, loeschen.class));
//
//
//        }

}
