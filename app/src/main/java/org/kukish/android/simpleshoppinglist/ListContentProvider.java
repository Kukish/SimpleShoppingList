package org.kukish.android.simpleshoppinglist;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Sq on 9/27/2016.
 */

public class ListContentProvider extends ContentProvider {

    public static final Uri CONTENT_URI =
            Uri.parse("content://org.kukish.android.simpleshoppinglist.ListContentProvider/shopitems");

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_QUANTITY = "quantity";
    public static final String KEY_CREATION_DATE = "creation_date";

    private ShopListSQLHelper sqlHelper;

    private static final int ALLROWS = 1;
    private static final int SINGLE_ROW = 2;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("org.kukish.android.shoppingprovider", "shopitems", ALLROWS);
        uriMatcher.addURI("org.kukish.android.shoppingprovider", "shopitems/#", SINGLE_ROW);
    }

    @Override
    public boolean onCreate() {
        sqlHelper = new ShopListSQLHelper(getContext(),
                ShopListSQLHelper.DATABASE_NAME, null,
                ShopListSQLHelper.DATABASE_VERSION);
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALLROWS:
                return "vnd.android.cursor.dir/vnd.shoplist.items";
            case SINGLE_ROW:
                return "vnd.android.cursor.item/vnd.shoplist.items";
            default:
                throw new IllegalArgumentException("Unsupported uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();

        String groupBy = null;
        String having = null;

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(ShopListSQLHelper.DATABASE_TABLE);

        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW:
                String rowId = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(KEY_ID + "=" + rowId);
            default:
                break;
        }
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, groupBy, having, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        String nullColumnHack = null;

        long id = db.insert(ShopListSQLHelper.DATABASE_TABLE, nullColumnHack, values);

        if (id > -1) {
            Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(insertedId, null);
            return insertedId;
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW:
                String rowId = uri.getPathSegments().get(1);
                selection = KEY_ID + "=" + rowId
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection
                        + ")" : "");
            default:
                break;
        }

        if (selection == null)
            selection = "1";

        int deleteCount = db.delete(ShopListSQLHelper.DATABASE_TABLE, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW:
                String rowId = uri.getPathSegments().get(1);
                selection = KEY_ID + "=" + rowId
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection
                        + ")" : "");
            default:
                break;
        }

        int updatedCount = db.update(ShopListSQLHelper.DATABASE_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return updatedCount;
    }

    private static class ShopListSQLHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "shoplist.db";
        private static final int DATABASE_VERSION = 1;
        public static final String DATABASE_TABLE = "shopitems";

        public static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " ( " + KEY_ID
                + " integer primary key autoincrement, " + KEY_NAME
                + " text not null, " + KEY_DESCRIPTION + " text, "
                + KEY_QUANTITY + " text, " + KEY_CREATION_DATE + " long);";

        public ShopListSQLHelper(Context context, String name,
                                 SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all data");

            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }
}
