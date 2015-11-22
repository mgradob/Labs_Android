package com.itesm.labs.rest.clients;

import com.itesm.labs.R;
import com.itesm.labs.rest.models.Category;
import com.itesm.labs.rest.services.CategoryService;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by mgradob on 11/4/15.
 */
public class CategoryClient {

    private CategoryService mCategoryService;

    public CategoryClient(CategoryService mCategoryService) {
        this.mCategoryService = mCategoryService;
    }

    public Observable<ArrayList<Category>> getCategories(final String token, final String lab) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<Category>>() {
            @Override
            public void call(Subscriber<? super ArrayList<Category>> subscriber) {
                ArrayList<Category> categories = mCategoryService.getCategories(token, lab);

                for (Category category : categories) {
                    setCategoryImage(category);
                }

                subscriber.onNext(categories);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Category> getCategory(final String token, final String lab, final int categoryId) {
        return Observable.create(new Observable.OnSubscribe<Category>() {
            @Override
            public void call(Subscriber<? super Category> subscriber) {
                Category category = mCategoryService.getCategory(token, lab, categoryId);

                setCategoryImage(category);

                subscriber.onNext(category);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> postNewCategory(final String token, final String lab, final Category body) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mCategoryService.postNewCategory(token, lab, body);

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> editCategory(final String token, final String lab, final int catId, final Category body) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mCategoryService.editCategory(token, lab, catId, body);

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> deleteCategory(final String token, final String lab, final int catId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mCategoryService.deleteCategory(token, lab, catId);

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    private void setCategoryImage(Category category) {
        switch (category.getId()) {
            case 1:
                category.setImageResource(R.drawable.ic_resistencias);
                break;
            case 2:
                category.setImageResource(R.drawable.ic_capacitores);
                break;
            case 3:
                category.setImageResource(R.drawable.ic_equipo);
                break;
            case 4:
                category.setImageResource(R.drawable.ic_kits);
                break;
            case 5:
                category.setImageResource(R.drawable.ic_cables);
                break;
            case 6:
                category.setImageResource(R.drawable.ic_integrados);
                break;
            case 7:
                category.setImageResource(R.drawable.ic_diodos);
                break;
            case 8:
                category.setImageResource(R.drawable.ic_herramientas);
                break;
            case 9:
                category.setImageResource(R.drawable.ic_switches);
                break;
            case 10:
                category.setImageResource(R.drawable.ic_adaptadores);
                break;
            case 11:
                category.setImageResource(R.drawable.ic_displays);
                break;
            case 12:
                category.setImageResource(R.drawable.ic_inductores);
                break;
            case 13:
                category.setImageResource(R.drawable.ic_sensores);
                break;
            case 14:
                category.setImageResource(R.drawable.ic_motores);
                break;
            case 15:
                category.setImageResource(R.drawable.ic_potenciometro);
                break;
            case 16:
                category.setImageResource(R.drawable.ic_transformadores);
                break;
            case 17:
                category.setImageResource(R.drawable.ic_transistores);
                break;
            default:
                category.setImageResource(R.drawable.ic_mechatronics);
                break;
        }
    }
}
