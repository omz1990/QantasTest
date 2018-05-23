package com.omar.qantastest.Recipes.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omar.qantastest.Common.network.domain.models.Recipe;
import com.omar.qantastest.R;

import java.util.List;

/**
 * Created by omz on 24/5/18
 */
public class OtherRecipesListAdapter extends RecyclerView.Adapter<OtherRecipesListAdapter.OtherRecipeViewHolder> {

    // The Recycler Adapter class for the recipes list recycler view
    private Context mContext;
    private List<Recipe> recipesList;
    private OtherRecipesListAdapter.OtherRecipesListListener listener;

    public OtherRecipesListAdapter(Context mContext, List<Recipe> recipesList, OtherRecipesListAdapter.OtherRecipesListListener listener) {
        this.mContext = mContext;
        this.recipesList = recipesList;
        this.listener = listener;
    }

    @Override
    public OtherRecipesListAdapter.OtherRecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_recipe_others, parent, false);

        return new OtherRecipesListAdapter.OtherRecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OtherRecipesListAdapter.OtherRecipeViewHolder holder, final int position) {
        // Update data of all views
        holder.recipeHeading.setText(recipesList.get(position).getTitle().trim());

        // Listeners to notify the Fragment that an item has been clicked, and then send the property details to the fragment
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecipeClick(recipesList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    public class OtherRecipeViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView recipeHeading;

        public OtherRecipeViewHolder(View view) {
            super(view);
            // Bind all the views to variables in the class
            cardView = (CardView) view.findViewById(R.id.cardView);
            recipeHeading = (TextView) view.findViewById(R.id.recipeHeading);
        }
    }

    public interface OtherRecipesListListener {
        void onRecipeClick(Recipe recipe);
    }
}