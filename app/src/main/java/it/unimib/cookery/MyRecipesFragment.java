package it.unimib.cookery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.widget.AdapterView;
import android.widget.GridView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;


public class MyRecipesFragment extends Fragment {

    /* dichiaro un oggetto di tipoGridView */
    private GridView  myRecipiesGridView;
    private String nomeRicettaTest =" pasta al forno";
    private  static  final  String TAG = "premuto";
    private AdapterClass adapter;

    /* crea l'array list di elementi da mostrare nell gridView */
    ArrayList<Recipe> recipeArrayList = new ArrayList<Recipe>();

    /* creo un array list di backup per quando distruggo e ricreo la view */
   // ArrayList<Recipe> recipeArrayListBackup = new ArrayList<Recipe>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         /* ripristino l'array list di partenza */
       // recipeArrayList= (ArrayList<Recipe>) recipeArrayListBackup.clone();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       /* creo un elemento di tipo view */
        View view = inflater.inflate(R.layout.fragment_my_recipes, container, false);

        /* ottengo la view */
        myRecipiesGridView = view.findViewById(R.id.gridView);



        /* aggiungo gli elementi alla gridView */
       for(int i=0; i<=20; i++){
           recipeArrayList.add(new Recipe(nomeRicettaTest, R.drawable.ic_baseline_add_24));
        }


        /* creo l'oggetto adapter e lo inizializzo con la view corrente e con l'array list*/
         adapter = new AdapterClass(getContext(), recipeArrayList);

        /*associo alla gridView l'adapter creato sopra */
       myRecipiesGridView.setAdapter(adapter);


        /* serve per intercettare l'oggetto premuto */
        myRecipiesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* stampa di debug */
                Log.d(TAG, "PREMUTO "+recipeArrayList.get(position));
            }
        });

        // Inflate the layout for this fragment
        return view;




    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("on destroy", "chiamato on destroy fragment");

        /* creo una copia dell'array list di ricette prima di svuotarla */
      // recipeArrayListBackup = (ArrayList<Recipe>) recipeArrayList.clone();
       /* svuoto l'array list */
      // recipeArrayList.clear();
       /* creo l'oggetto adapter e lo inizializzo con la view corrente e con l'array list*/
        // adapter = new AdapterClass(getContext(), recipeArrayList);
        /*associo alla gridView l'adapter creato sopra */
       // myRecipiesGridView.setAdapter(adapter);

    }
}