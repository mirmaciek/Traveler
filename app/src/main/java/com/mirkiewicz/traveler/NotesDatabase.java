package com.mirkiewicz.traveler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NotesDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NoteDB";
    private static final String TABLE_NOTES = "NotesTable";
    private static final String KEY_ID = "id";
    private static final String KEY_DESC = "content";


    public NotesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE NotesTable(id Integer PRIMARY KEY,content String);";
        db.execSQL(CREATE_NOTES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);

        onCreate(db);
    }

     void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, note.getIdNote());
        values.put(KEY_DESC, note.getNoteContent());

        db.insert(TABLE_NOTES, null, values);

        Log.w("Baza!", "Pomyslnie dodano notatke do bazy");
        db.close();
    }

    Note getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTES, new String[] { KEY_ID,
                        KEY_DESC}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        return note;
    }

    public List<Note> getAllNotes() {
        List<Note> NotesList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NOTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setIdNote(Integer.parseInt(cursor.getString(0)));
                note.setNoteContent(cursor.getString(1));
                NotesList.add(note);
            } while (cursor.moveToNext());
        }
        return NotesList;
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
    public int getSize() {

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement s = db.compileStatement( "select count(*) from NotesTable " );

        int count = (int)s.simpleQueryForLong();
        db.close();
        return count;
    }
    public void removeAll() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, null, null);
        db.close();
    }
}
