package com.itesm.labs.content;

import com.itesm.labs.models.CategoryModel;

import java.util.ArrayList;

/**
 * Created by miguel on 30/10/14.
 */
public class CategoriesContent {

    public ArrayList<CategoryModel> CATEGORIES = new ArrayList<CategoryModel>();

    public ArrayList<CategoryModel> getCATEGORIES() {
        return CATEGORIES;
    }

    public void setCATEGORIES(ArrayList<CategoryModel> CATEGORIES) {
        this.CATEGORIES = CATEGORIES;
    }
}
