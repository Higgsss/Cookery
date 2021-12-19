package it.unimib.cookery.adapters;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.List;

import it.unimib.cookery.R;
import it.unimib.cookery.models.Ingredient;
import it.unimib.cookery.models.IngredientPantry;
import it.unimib.cookery.repository.DatabasePantryRepository;
import it.unimib.cookery.ui.PantryFragment;

public class SearchChipAdapter extends RecyclerView.Adapter<SearchChipAdapter.IngredientViewHolder>{
    private List<Ingredient> mListIngredients;
    private  int k = 0;
    public  void setData( List<Ingredient> list){
        this.mListIngredients = list;
        notifydata();

    }
    public void notifydata(){
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chip_ingredient_search, parent, false);
        return  new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = mListIngredients.get(position);
        if(ingredient == null){ return;}
        holder.chipIngredient.setText(ingredient.getIngredientName()+":");
        holder.chipIngredient.setId(position);
        holder.chipIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("click", "-" + ingredient.getIngredientName());
                openDialogAddProduct(holder.itemView, holder.chipIngredient.getId(), mListIngredients);
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        if(mListIngredients != null){
            return  mListIngredients.size();
        }
        return 0;
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder{
        private Chip chipIngredient;
        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            chipIngredient = itemView.findViewById(R.id.chip_ingredient);
        }
    }

    public void openDialogAddProduct(View itemView, int id, List<Ingredient> list){
        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
        // crea una dialog
        Dialog ingredientDialog = new Dialog(itemView.getContext());
        // elimina il titolo dalla dialog che non serve
        ingredientDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // permette l'uscita dalla dialog solo se si preme cancella
        //ingredientDialog.setCancelable(false);
        // setta il layout che poi verrà mostrato nella dialog
        ingredientDialog.setContentView(R.layout.layout_ingredient_pantry_dialog);
        // creo e trovo l'oggetto textView nella dialog
        TextView ingredientName = ingredientDialog.findViewById(R.id.IngredientName);
        // setto il teso della dialog la stringa andrà poi sostituita col nome dell'ingrediente da aggiungere
        ingredientName.setText(list.get(id).getIngredientName());
        // creo e trovo l'oggetto editText dove l'utente inserisce la quantità
        EditText editText = ingredientDialog.findViewById(R.id.IngredientEditText);
        //Spinner fors select tipe of pantry
        Spinner spinner = (Spinner)ingredientDialog.findViewById(R.id.tipe_pantry_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(itemView.getContext(),R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Button addButton = ingredientDialog.findViewById(R.id.addIngredientButton);
        //listener per il bottone per aggiungere l'ingrediente
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // prendo la quantità scritta nel text edit se non è vuota la converto in stringa e poi in intero
                // se è > 0 la salvo mentre se è = 0 mostro il toast
                // l'edit text è già settata solo per accettare numeri interi positivi
                int pantryPosition = 0;
                String text = spinner.getSelectedItem().toString();
                if (!editText.getText().toString().equals("") && (Integer.parseInt(editText.getText().toString())) > 0) {

                    if (text.equalsIgnoreCase("pantry")){
                        //Log.d("test", "test:- " + text);
                        pantryPosition = 1;
                    }else if(text.equalsIgnoreCase("fridge")){
                        //Log.d("test", "test:- " + text);
                        pantryPosition = 2;
                    }else if(text.equalsIgnoreCase("freezer")){
                        //Log.d("test", "test:- " + text);
                        pantryPosition = 3;
                    }
                    IngredientPantry  ingredientPantry = new IngredientPantry(list.get(id).getIdIngredient(),list.get(id).getIngredientName(),Integer.parseInt(editText.getText().toString()),12, pantryPosition);
                    PantryFragment.savedb(ingredientPantry);
                    ingredientDialog.dismiss();
                } else {
                    // stampa un toast di errore
                    Toast.makeText(itemView.getContext(), R.string.invalid_quantity, Toast.LENGTH_SHORT).show();
                }
            }

        });
        // creo e ottengo l'oggetto per il bottono di delete
        Button deleteButton = ingredientDialog.findViewById(R.id.deleteIngredientButton);
        // listener del bottone di delete
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pr sicurezza setto la quantità a 0
                //quantità = 0;
                // chiude la dialog
                ingredientDialog.dismiss();
            }
        });
        // mostra la dialog a schermo
        ingredientDialog.show();
    }

}