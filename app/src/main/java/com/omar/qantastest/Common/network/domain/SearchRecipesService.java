package com.omar.qantastest.Common.network.domain;

import com.omar.qantastest.Common.network.domain.models.RecipeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by omz on 23/5/18
 */
public interface SearchRecipesService {

    @GET("recipes.json")
    Observable<RecipeResponse> searchRecipes();

}
