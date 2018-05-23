package com.omar.qantastest.Recipes.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.omar.qantastest.Common.network.domain.models.Recipe;
import com.omar.qantastest.R;

import java.util.List;

/**
 * Created by omz on 23/5/18
 */
public class RecipesListAdapter  extends RecyclerView.Adapter<RecipesListAdapter.RecipeViewHolder> {

    // The Recycler Adapter class for the properties list recycler view
    private Context mContext;
    private List<Recipe> recipesList;
    private RecipesListListener listener;

    public RecipesListAdapter(Context mContext, List<Recipe> recipesList, RecipesListListener listener) {
        this.mContext = mContext;
        this.recipesList = recipesList;
        this.listener = listener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_recipe_popular, parent, false);

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, final int position) {
        // Update data of all views
        holder.recipeHeading.setText(recipesList.get(position).getTitle());

        // Load images from received URLs
        Glide.with(mContext)
                .load(recipesList.get(position).getThumbnail())
                .placeholder(R.drawable.ic_recipe_placeholder)
                .into(holder.thumbnail);

        // Listeners to notify the Fragment that an item has been clicked, and then send the property details to the fragment
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecipeClick(recipesList.get(position));
            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
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

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView recipeHeading;
        public ImageView thumbnail;

        public RecipeViewHolder(View view) {
            super(view);
            // Bind all the views to variables in the class
            cardView = (CardView) view.findViewById(R.id.cardView);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            recipeHeading = (TextView) view.findViewById(R.id.recipeHeading);
        }
    }

    public interface RecipesListListener {
        void onRecipeClick(Recipe recipe);
    }
}