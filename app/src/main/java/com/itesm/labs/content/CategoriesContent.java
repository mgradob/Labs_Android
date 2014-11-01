package com.itesm.labs.content;

import com.itesm.labs.models.CategoryInformation;

import java.util.ArrayList;

/**
 * Created by miguel on 30/10/14.
 */
public class CategoriesContent {

    public ArrayList<CategoryInformation> CATEGORIES = new ArrayList<CategoryInformation>();

    public ArrayList<CategoryInformation> getCATEGORIES() {
        return CATEGORIES;
    }

    public void setCATEGORIES(ArrayList<CategoryInformation> CATEGORIES) {
        this.CATEGORIES = CATEGORIES;
    }
}
