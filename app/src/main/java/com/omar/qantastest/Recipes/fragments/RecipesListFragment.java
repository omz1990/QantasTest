package com.omar.qantastest.Recipes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.omar.qantastest.Common.network.domain.models.Recipe;
import com.omar.qantastest.Common.network.domain.models.RecipeResponse;
import com.omar.qantastest.Common.network.managers.RecipesManager;
import com.omar.qantastest.Common.ui.BaseFragment;
import com.omar.qantastest.R;
import com.omar.qantastest.Recipes.adapters.OtherRecipesListAdapter;
import com.omar.qantastest.Recipes.adapters.PopularRecipesListAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by omz on 23/5/18
 */
public class RecipesListFragment extends BaseFragment
        implements PopularRecipesListAdapter.RecipesListListener, OtherRecipesListAdapter.OtherRecipesListListener {

    @BindView(R.id.recyclerViewPopularRecipes) protected RecyclerView recyclerViewPopularRecipes;
    private PopularRecipesListAdapter popularRecipesAdapter;
    private List<Recipe> popularRecipesList;

    @BindView(R.id.recyclerViewOtherRecipes) protected RecyclerView recyclerViewOtherRecipes;
    private OtherRecipesListAdapter otherRecipesListAdapter;
    private List<Recipe> otherRecipesList;

    @BindView(R.id.progressBar) protected ProgressBar progressBar;

    private RecipesManager recipesManager;
    private RecipesListFragmentListener listener;

    private RecipeResponse responseData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Keep data saved while screen orientation changes
        setRetainInstance(true);

        // Repopulate previously loaded data to handle not losing data on screen orientation if there is any available
        if (savedInstanceState != null) {
            repopulateValues(savedInstanceState);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_list, container, false);
        ButterKnife.bind(this, view);

        recipesManager = new RecipesManager();

        // We split the two lists to display them in different sections
        popularRecipesList = new ArrayList<>();
        popularRecipesAdapter = new PopularRecipesListAdapter(getActivity(), popularRecipesList, this);
        recyclerViewPopularRecipes.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPopularRecipes.setAdapter(popularRecipesAdapter);

        otherRecipesList = new ArrayList<>();
        otherRecipesListAdapter = new OtherRecipesListAdapter(getActivity(), otherRecipesList, this);
        recyclerViewOtherRecipes.setAdapter(otherRecipesListAdapter);

        if (responseData == null) {
            searchRecipes();
        } else {
            updateLists();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // A config boolean has been defined which determines which orientation the phone is in
        // Based on the orientation, we apply the span counts to the GridLayoutManagers of the recycler views
        int spanCount = 2;
        if (getActivity().getResources().getBoolean(R.bool.landscapeMode)) {
            spanCount = 3;
        }
        RecyclerView.LayoutManager mLayoutManagerPopular = new GridLayoutManager(getActivity(), spanCount);
        recyclerViewPopularRecipes.setLayoutManager(mLayoutManagerPopular);
        RecyclerView.LayoutManager mLayoutManagerOther = new GridLayoutManager(getActivity(), spanCount);
        recyclerViewOtherRecipes.setLayoutManager(mLayoutManagerOther);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Initialize the listener
        if (context instanceof RecipesListFragment.RecipesListFragmentListener) {
            listener = (RecipesListFragment.RecipesListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement RecipesListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void searchRecipes() {
        // Make the API call (Retrofit and RxJava)
        recipesManager.searchRecipes()
                .subscribe(getRecipesSubscriber());
        progressBar.setVisibility(View.VISIBLE);
    }

    private DisposableObserver<RecipeResponse> getRecipesSubscriber() {
        return new DisposableObserver<RecipeResponse>() {
            @Override
            public void onComplete() {
                // noop
            }

            @Override
            public void onError(Throwable e) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onNext(RecipeResponse response) {
                progressBar.setVisibility(View.GONE);
                responseData = response;
                updateLists();
            }
        };
    }

    private void updateLists() {
        popularRecipesList.clear();
        otherRecipesList.clear();

        for (Recipe recipe : responseData.getResults()) {
            if (!recipe.getThumbnail().isEmpty()) {
                popularRecipesList.add(recipe);
            } else {
                otherRecipesList.add(recipe);
            }
        }
        popularRecipesAdapter.notifyDataSetChanged();
        otherRecipesListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently loaded data into the bundle for reloading
        savedInstanceState.putSerializable("responseData", (Serializable) responseData);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void repopulateValues(Bundle bundle) {
        responseData = (RecipeResponse) bundle.getSerializable("responseData");
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
