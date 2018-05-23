package com.omar.qantastest.Recipes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omar.qantastest.Common.network.domain.models.Recipe;
import com.omar.qantastest.Common.network.domain.models.RecipeResponse;
import com.omar.qantastest.Common.network.managers.RecipesManager;
import com.omar.qantastest.Common.ui.BaseFragment;
import com.omar.qantastest.R;
import com.omar.qantastest.Recipes.adapters.RecipesListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by omz on 23/5/18
 */
public class RecipesListFragment extends BaseFragment implements RecipesListAdapter.RecipesListListener {

    @BindView(R.id.recyclerViewPopularRecipes) protected RecyclerView recyclerViewPopularRecipes;
    private RecipesListAdapter popularRecipesAdapter;
    private List<Recipe> popularRecipesList;

    @BindView(R.id.recyclerViewOtherRecipes) protected RecyclerView recyclerViewOtherRecipes;
    private List<Recipe> otherRecipesList;

    private RecipesManager recipesManager;
    private RecipesListFragmentListener listener;

    private RecipeResponse responseData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_list, container, false);
        ButterKnife.bind(this, view);

        recipesManager = new RecipesManager();

        popularRecipesList = new ArrayList<>();
        popularRecipesAdapter = new RecipesListAdapter(getActivity(), popularRecipesList, this);
        recyclerViewPopularRecipes.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPopularRecipes.setAdapter(popularRecipesAdapter);

        searchRecipes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        int spanCount = 2;
        if (getActivity().getResources().getBoolean(R.bool.landscapeMode)) {
            spanCount = 3;
        }
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        recyclerViewPopularRecipes.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Initialize the listener
        if (context instanceof RecipesListFragment.RecipesListFragmentListener) {
            listener = (RecipesListFragment.RecipesListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement PropertiesListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void searchRecipes() {
        // Make the API call (retrofit and rxjava)
        recipesManager.searchRecipes()
                .subscribe(getRecipesSubscriber());
    }

    private DisposableObserver<RecipeResponse> getRecipesSubscriber() {
        return new DisposableObserver<RecipeResponse>() {
            @Override
            public void onComplete() {
                // noop
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(RecipeResponse response) {
                responseData = response;
                updateLists();
            }
        };
    }

    private void updateLists() {
        popularRecipesList.clear();
        for (Recipe recipe : responseData.getResults()) {
            if (!recipe.getThumbnail().isEmpty()) {
                popularRecipesList.add(recipe);
            }
        }
        popularRecipesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        listener.recipeClicked(recipe);
    }

    // Listener that will notify the activity that an item was clicked in the RecyclerView
    public interface RecipesListFragmentListener {
        void recipeClicked(Recipe recipe);
    }
}
