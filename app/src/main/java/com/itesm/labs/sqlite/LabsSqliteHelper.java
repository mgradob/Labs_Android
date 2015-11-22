package com.itesm.labs.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.itesm.labs.rest.models.Cart;
import com.itesm.labs.rest.models.CartItem;
import com.itesm.labs.rest.models.Category;
import com.itesm.labs.rest.models.Component;
import com.itesm.labs.rest.models.User;

import java.util.ArrayList;

/**
 * Created by mgradob on 4/9/15.
 */
public class LabsSqliteHelper extends SQLiteOpenHelper {

    //region USERS Table.
    public static final String USERS_TABLE = "USERS";
    public static final String USERS_COLUMN_ID = "USER_ID";
    public static final String USERS_COLUMN_NAME = "USER_NAME";
    public static final String USERS_COLUMN_LAST_NAME_1 = "USER_LAST_NAME_1";
    public static final String USERS_COLUMN_LAST_NAME_2 = "USER_LAST_NAME_2";
    public static final String USERS_COLUMN_UID = "USER_UID";
    public static final String USERS_COLUMN_CAREER = "USER_CAREER";
    public static final String USERS_COLUMN_MAIL = "USER_MAIL";
    //region ALLOWED_LABS Table.
    public static final String ALLOWED_LABS_TABLE = "ALLOWED_LABS";
    public static final String ALLOWED_LABS_COLUMN_ID = "LAB_ID";
    public static final String ALLOWED_LABS_COLUMN_USER_ID = "USER_ID";
    //endregion
    public static final String ALLOWED_LABS_COLUMN_URL = "LAB_URL";
    //region CART_ITEM Table.
    public static final String CART_ITEM_TABLE = "CART_ITEM";
    public static final String CART_ITEM_COLUMN_ID = "CART_ITEM_COLUMN_ID";
    public static final String CART_ITEM_COLUMN_USER_ID = "CART_ITEM_COLUMN_USER_ID";
    public static final String CART_ITEM_COLUMN_COMPONENT_ID = "CART_ITEM_COLUMN_COMPONENT_ID";
    //endregion
    public static final String CART_ITEM_COLUMN_QUANTITY = "CART_ITEM_COLUMN_QUANTITY";
    public static final String CART_ITEM_COLUMN_CHECKOUT = "CART_ITEM_COLUMN_CHECKOUT";
    public static final String CART_ITEM_COLUMN_READY = "CART_ITEM_COLUMN_READY";
    public static final String CART_ITEM_COLUMN_DATE_CHECKOUT = "CART_ITEM_COLUMN_DATE_CHECKOUT";
    public static final String CART_ITEM_TABLE_CREATE = "CREATE TABLE " + CART_ITEM_TABLE +
            "(" +
            CART_ITEM_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            CART_ITEM_COLUMN_USER_ID + " TEXT, " +
            CART_ITEM_COLUMN_COMPONENT_ID + " INTEGER, " +
            CART_ITEM_COLUMN_QUANTITY + " INTEGER, " +
            CART_ITEM_COLUMN_CHECKOUT + " INTEGER, " +
            CART_ITEM_COLUMN_READY + " INTEGER, " +
            CART_ITEM_COLUMN_DATE_CHECKOUT + " TEXT" +
            ")";
    //region COMPONENT Table.
    public static final String COMPONENT_TABLE = "COMPONENT";
    public static final String COMPONENT_COLUMN_COMPONENT_ID = "COMPONENT_COLUMN_COMPONENT_ID";
    public static final String COMPONENT_COLUMN_CATEGORY_ID = "COMPONENT_COLUMN_CATEGORY_ID";
    public static final String COMPONENT_COLUMN_NAME = "COMPONENT_COLUMN_NAME";
    //endregion
    public static final String COMPONENT_COLUMN_NOTE = "COMPONENT_COLUMN_NOTE";
    public static final String COMPONENT_COLUMN_TOTAL = "COMPONENT_COLUMN_TOTAL";
    public static final String COMPONENT_COLUMN_AVAILABLE = "COMPONENT_COLUMN_AVAILABLE";
    public static final String COMPONENT_TABLE_CREATE = "CREATE TABLE " + COMPONENT_TABLE +
            "(" +
            COMPONENT_COLUMN_COMPONENT_ID + " INTEGER PRIMARY KEY, " +
            COMPONENT_COLUMN_CATEGORY_ID + " INTEGER, " +
            COMPONENT_COLUMN_NAME + " TEXT, " +
            COMPONENT_COLUMN_NOTE + " TEXT, " +
            COMPONENT_COLUMN_TOTAL + " INTEGER, " +
            COMPONENT_COLUMN_AVAILABLE + " INTEGER" +
            ")";
    //region CATEGORY Table.
    public static final String CATEGORY_TABLE = "CATEGORY";
    public static final String CATEGORY_COLUMN_ID = "CATEGORY_COLUMN_ID";
    public static final String CATEGORY_COLUMN_NAME = "CATEGORY_COLUMN_NAME";
    private static final String DATABASE_NAME = "Labs.db";
    //endregion
    private static final int DATABASE_VERSION = 1;
    private static final String USERS_TABLE_CREATE = "CREATE TABLE " + USERS_TABLE +
            "(" +
            USERS_COLUMN_ID + " TEXT PRIMARY KEY, " +
            USERS_COLUMN_NAME + " TEXT, " +
            USERS_COLUMN_LAST_NAME_1 + " TEXT, " +
            USERS_COLUMN_LAST_NAME_2 + " TEXT, " +
            USERS_COLUMN_UID + " INTEGER, " +
            USERS_COLUMN_CAREER + " TEXT, " +
            USERS_COLUMN_MAIL + " TEXT" +
            ")";
    private static final String ALLOWED_LABS_TABLE_CREATE = "CREATE TABLE " + ALLOWED_LABS_TABLE +
            "(" +
            ALLOWED_LABS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            ALLOWED_LABS_COLUMN_USER_ID + " TEXT, " +
            ALLOWED_LABS_COLUMN_URL + " TEXT" +
            ")";
    private static final String CATEGORY_TABLE_CREATE = "CREATE TABLE " + CATEGORY_TABLE +
            "(" +
            CATEGORY_COLUMN_ID + " INTEGER PRIMARY KEY, " +
            CATEGORY_COLUMN_NAME + " TEXT" +
            ")";
    //endregion

    public LabsSqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERS_TABLE_CREATE);
        db.execSQL(ALLOWED_LABS_TABLE_CREATE);
        db.execSQL(CART_ITEM_TABLE_CREATE);
        db.execSQL(COMPONENT_TABLE_CREATE);
        db.execSQL(CATEGORY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ALLOWED_LABS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CART_ITEM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COMPONENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
        onCreate(db);
    }

    /**
     * Drops all tables and creates them again.
     */
    public void repopulate() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ALLOWED_LABS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CART_ITEM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COMPONENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
        onCreate(db);
    }

    //region Users Table methods.

    /**
     * Add a new user to the database.
     *
     * @param user The user to be added.
     */
    public void addNewUser(User user) {
        // Obtain the database with write permissions.
        SQLiteDatabase database = this.getWritableDatabase();

        // Create a new value to insert.
        ContentValues values = new ContentValues();
        values.put(USERS_COLUMN_ID, user.getUserId());
        values.put(USERS_COLUMN_NAME, user.getUserName());
        values.put(USERS_COLUMN_LAST_NAME_1, user.getUserLastName1());
        values.put(USERS_COLUMN_LAST_NAME_2, user.getUserLastName2());
        values.put(USERS_COLUMN_UID, user.getUserUid());
        values.put(USERS_COLUMN_CAREER, user.getUserCareer());
        values.put(USERS_COLUMN_MAIL, user.getUserMail());

        // Insert row.
        database.insert(USERS_TABLE, null, values);
        database.close();
    }

    /**
     * Gets the data of a specific user.
     *
     * @param userId The specific user to query.
     * @return The specific user data.
     */
    public User getUser(String userId) {
        // Obtain the database with read permissions.
        SQLiteDatabase database = this.getReadableDatabase();

        // Obtain a cursor with the specific user row.
        Cursor cursor = database.query(
                USERS_TABLE, new String[]{USERS_COLUMN_ID, USERS_COLUMN_NAME,
                        USERS_COLUMN_LAST_NAME_1, USERS_COLUMN_LAST_NAME_2, USERS_COLUMN_UID,
                        USERS_COLUMN_CAREER, USERS_COLUMN_MAIL}, USERS_COLUMN_ID + "=?",
                new String[]{userId}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            // Return the new object.
            return new User(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(0), cursor.getString(5), cursor.getLong(4), cursor.getString(6),
                    getAllowedLabsOfUser(cursor.getString(0)));
        } else
            return null;
    }

    /**
     * Get all the users in the database.
     *
     * @return An ArrayList of users.
     */
    public ArrayList<User> getAllUsers() {
        // Obtain the database with read permissions.
        SQLiteDatabase database = this.getReadableDatabase();

        // Obtain a cursor with all rows.
        Cursor cursor = database.rawQuery("SELECT * FROM " + USERS_TABLE, null);

        // Add each row of the cursor to the list.
        ArrayList<User> users = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                User user = new User(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(0), cursor.getString(5), cursor.getLong(4),
                        cursor.getString(6), getAllowedLabsOfUser(cursor.getString(0)));
                users.add(user);
            } while (cursor.moveToNext());
        }

        return users;
    }

    /**
     * Get the total number of users in the database.
     *
     * @return The total users in database.
     */
    public int getUserCount() {
        // Obtain the database with read permissions.
        SQLiteDatabase database = this.getReadableDatabase();

        // Obtain a cursor with all rows.
        Cursor cursor = database.rawQuery("SELECT * FROM " + USERS_TABLE, null);
        cursor.close();

        return cursor.getCount();
    }

    /**
     * Update an user in the database.
     *
     * @param user The model of the user to update.
     */
    public void updateUser(User user) {
        // Obtain the database with write permissions.
        SQLiteDatabase database = this.getWritableDatabase();

        // Create a new value to update.
        ContentValues values = new ContentValues();
        values.put(USERS_COLUMN_ID, user.getUserId());
        values.put(USERS_COLUMN_NAME, user.getUserName());
        values.put(USERS_COLUMN_LAST_NAME_1, user.getUserLastName1());
        values.put(USERS_COLUMN_LAST_NAME_2, user.getUserLastName2());
        values.put(USERS_COLUMN_UID, user.getUserUid());
        values.put(USERS_COLUMN_CAREER, user.getUserCareer());
        values.put(USERS_COLUMN_MAIL, user.getUserMail());

        database.update(USERS_TABLE, values, USERS_COLUMN_ID + "=?",
                new String[]{user.getUserId()});
    }

    /**
     * Delete an specific user from the database.
     *
     * @param user The user to delete.
     */
    public void deleteUser(User user) {
        // Obtain the database with write permissions.
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(USERS_TABLE, USERS_COLUMN_ID + "=?", new String[]{user.getUserId()});
    }
    //endregion

    //region Allowed Labs methods.

    /**
     * Add a new allowed lab to database.
     *
     * @param userId The id of the user.
     * @param url    The url of the lab.
     */
    public void addNewLab(String userId, String url) {
        // Obtain the database with write permissions.
        SQLiteDatabase database = this.getWritableDatabase();

        ArrayList<String> labs = getAllowedLabsOfUser(userId);

        if (!labs.contains(url)) {
            // Create a new value to insert.
            ContentValues values = new ContentValues();
            values.put(ALLOWED_LABS_COLUMN_USER_ID, userId);
            values.put(ALLOWED_LABS_COLUMN_URL, url);

            // Insert row.
            database.insert(ALLOWED_LABS_TABLE, null, values);
        }

        database.close();
    }

    /**
     * Get the allowed labs for a specific user.
     *
     * @param userId The id The id of the user.
     * @return An ArrayList of the allowed labs urls.
     */
    public ArrayList<String> getAllowedLabsOfUser(String userId) {
        // Obtain the database with read permissions.
        SQLiteDatabase database = this.getReadableDatabase();

        // Obtain a cursor with the labs rows.
        Cursor cursor = database.query(
                ALLOWED_LABS_TABLE, new String[]{ALLOWED_LABS_COLUMN_ID,
                        ALLOWED_LABS_COLUMN_USER_ID, ALLOWED_LABS_COLUMN_URL},
                ALLOWED_LABS_COLUMN_USER_ID + "=?", new String[]{userId}, null, null, null, null);

        ArrayList<String> allowedLabsForUser = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                allowedLabsForUser.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // Return the labs urls.
        return allowedLabsForUser;
    }
    //endregion

    //region CartItem methods.

    /**
     * Add a new cart item to the table.
     *
     * @param item the item to add.
     */
    public void addNewCartItem(CartItem item) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CART_ITEM_COLUMN_USER_ID, item.getStudentId());
        values.put(CART_ITEM_COLUMN_COMPONENT_ID, item.getComponentId());
        values.put(CART_ITEM_COLUMN_QUANTITY, item.getQuantity());
        values.put(CART_ITEM_COLUMN_CHECKOUT, item.isCheckout());
        values.put(CART_ITEM_COLUMN_READY, item.isReady());
        values.put(CART_ITEM_COLUMN_DATE_CHECKOUT, item.getDateCheckout());

        database.insert(CART_ITEM_TABLE, null, values);
        database.close();
    }

    /**
     * Obtain a cart for a user of all requests.
     *
     * @param user the user to get the cart for.
     * @return the filtered cart of the user.
     */
    public Cart getCartOf(User user) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(CART_ITEM_TABLE, new String[]{CART_ITEM_COLUMN_USER_ID,
                        CART_ITEM_COLUMN_COMPONENT_ID, CART_ITEM_COLUMN_QUANTITY,
                        CART_ITEM_COLUMN_CHECKOUT, CART_ITEM_COLUMN_READY,
                        CART_ITEM_COLUMN_DATE_CHECKOUT}, CART_ITEM_COLUMN_USER_ID + "=?",
                new String[]{user.getUserId()}, null, null, null, null);

        ArrayList<CartItem> cartItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                CartItem item = new CartItem();
                item.setStudentId(cursor.getString(0));
                item.setComponentId(cursor.getInt(1));
                item.setQuantity(cursor.getInt(2));
                item.setCheckout(Boolean.parseBoolean(cursor.getString(3)));
                item.setReady(Boolean.parseBoolean(cursor.getString(4)));
                item.setDateCheckout(cursor.getString(5));

                cartItems.add(item);
            } while (cursor.moveToNext());
        }

        return new Cart(user.getFullName(), user.getUserId(),
                cartItems.get(cartItems.size() - 1).getDateCheckout(), cartItems,
                cartItems.get(cartItems.size() - 1).isReady());
    }

    /**
     * Get the list of all items in an user cart.
     *
     * @param user the user to get the items for.
     * @return the list of items of the user.
     */
    public ArrayList<CartItem> getItemsOf(User user) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(CART_ITEM_TABLE, new String[]{CART_ITEM_COLUMN_USER_ID,
                        CART_ITEM_COLUMN_COMPONENT_ID, CART_ITEM_COLUMN_QUANTITY,
                        CART_ITEM_COLUMN_CHECKOUT, CART_ITEM_COLUMN_READY,
                        CART_ITEM_COLUMN_DATE_CHECKOUT}, CART_ITEM_COLUMN_USER_ID + "=?",
                new String[]{user.getUserId()}, null, null, null, null);

        ArrayList<CartItem> cartItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                CartItem item = new CartItem();
                item.setStudentId(cursor.getString(0));
                item.setComponentId(cursor.getInt(1));
                item.setQuantity(cursor.getInt(2));
                item.setCheckout(Boolean.parseBoolean(cursor.getString(3)));
                item.setReady(Boolean.parseBoolean(cursor.getString(4)));
                item.setDateCheckout(cursor.getString(5));

                cartItems.add(item);
            } while (cursor.moveToNext());
        }

        return cartItems;
    }

    /**
     * Get the list of all items in an user cart.
     *
     * @param userId the id of the user to get the items for.
     * @return the list of items of the user.
     */
    public ArrayList<CartItem> getItemsOf(String userId) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(CART_ITEM_TABLE, new String[]{CART_ITEM_COLUMN_USER_ID,
                        CART_ITEM_COLUMN_COMPONENT_ID, CART_ITEM_COLUMN_QUANTITY,
                        CART_ITEM_COLUMN_CHECKOUT, CART_ITEM_COLUMN_READY,
                        CART_ITEM_COLUMN_DATE_CHECKOUT}, CART_ITEM_COLUMN_USER_ID + "=?",
                new String[]{userId}, null, null, null, null);

        ArrayList<CartItem> cartItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                CartItem item = new CartItem();
                item.setStudentId(cursor.getString(0));
                item.setComponentId(cursor.getInt(1));
                item.setQuantity(cursor.getInt(2));
                item.setCheckout(Boolean.parseBoolean(cursor.getString(3)));
                item.setReady(Boolean.parseBoolean(cursor.getString(4)));
                item.setDateCheckout(cursor.getString(5));

                cartItems.add(item);
            } while (cursor.moveToNext());
        }

        return cartItems;
    }
    //endregion

    //region Component methods.

    /**
     * Adds a component to the table.
     *
     * @param component the component to be added.
     */
    public void addComponent(Component component) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COMPONENT_COLUMN_COMPONENT_ID, component.getId());
        values.put(COMPONENT_COLUMN_CATEGORY_ID, component.getCategory());
        values.put(COMPONENT_COLUMN_NAME, component.getName());
        values.put(COMPONENT_COLUMN_NOTE, component.getNote());
        values.put(COMPONENT_COLUMN_TOTAL, component.getTotal());
        values.put(COMPONENT_COLUMN_AVAILABLE, component.getAvailable());

        database.insert(COMPONENT_TABLE, null, values);
        database.close();
    }

    /**
     * Updates a component of the table.
     *
     * @param component the component to be updated.
     */
    public void updateComponent(Component component) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COMPONENT_COLUMN_COMPONENT_ID, component.getId());
        values.put(COMPONENT_COLUMN_CATEGORY_ID, component.getCategory());
        values.put(COMPONENT_COLUMN_NAME, component.getName());
        values.put(COMPONENT_COLUMN_NOTE, component.getNote());
        values.put(COMPONENT_COLUMN_TOTAL, component.getTotal());
        values.put(COMPONENT_COLUMN_AVAILABLE, component.getAvailable());

        database.update(COMPONENT_TABLE, values, COMPONENT_COLUMN_COMPONENT_ID + "=?",
                new String[]{"" + component.getId()});
        database.close();
    }

    /**
     * Deletes a component of the table.
     *
     * @param component the component to be deleted.
     */
    public void deleteComponent(Component component) {
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(COMPONENT_TABLE, COMPONENT_COLUMN_COMPONENT_ID + "=?",
                new String[]{"" + component.getId()});
        database.close();
    }

    public Component getComponent(String componentId) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(COMPONENT_TABLE, new String[]{COMPONENT_COLUMN_COMPONENT_ID,
                        COMPONENT_COLUMN_CATEGORY_ID, COMPONENT_COLUMN_NAME, COMPONENT_COLUMN_NOTE,
                        COMPONENT_COLUMN_TOTAL, COMPONENT_COLUMN_AVAILABLE},
                COMPONENT_COLUMN_COMPONENT_ID + "=?", new String[]{componentId}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Component component = new Component();
        component.setId(cursor.getInt(0));
        component.setCategory(cursor.getInt(1));
        component.setName(cursor.getString(2));
        component.setNote(cursor.getString(3));
        component.setTotal(cursor.getInt(4));
        component.setAvailable(cursor.getInt(5));

        return component;
    }
    //endregion

    //region Category methods.

    /**
     * Add a new category to the table.
     *
     * @param category the category to be added.
     */
    public void addCategory(Category category) {
        SQLiteDatabase database = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(CATEGORY_COLUMN_ID, category.getId());
        values.put(CATEGORY_COLUMN_NAME, category.getName());

        database.insert(CATEGORY_TABLE, null, values);
        database.close();
    }

    /**
     * Update a category element.
     *
     * @param category the category to be updated.
     */
    public void updateCategory(Category category) {
        SQLiteDatabase database = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(CATEGORY_COLUMN_ID, category.getId());
        values.put(CATEGORY_COLUMN_NAME, category.getName());

        database.update(CATEGORY_TABLE, values, CATEGORY_COLUMN_ID + "=?",
                new String[]{"" + category.getId()});
        database.close();
    }

    /**
     * Delete a category from the table.
     *
     * @param category
     */
    public void deleteCategory(Category category) {
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(CATEGORY_TABLE, CATEGORY_COLUMN_ID + "=?",
                new String[]{"" + category.getId()});
        database.close();
    }

    /**
     * Get a specific category.
     *
     * @param categoryId the id of the category.
     * @return the queried category.
     */
    public Category getCategory(int categoryId) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(CATEGORY_TABLE, new String[]{CATEGORY_COLUMN_ID,
                        CATEGORY_COLUMN_NAME}, CATEGORY_COLUMN_ID + "=?",
                new String[]{"" + categoryId}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Category category = new Category();
        category.setId(cursor.getInt(0));
        category.setName(cursor.getString(1));

        return category;
    }
    //endregion
}
