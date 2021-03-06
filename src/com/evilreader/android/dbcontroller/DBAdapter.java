/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * Dainius Jocas <dainius(dot)jocas(at)gmail(dot)com> wrote this file. 
 * As long as you retain this notice you can do whatever you want with this 
 * stuff. If we meet some day, and you think this stuff is worth it, you can 
 * buy me a beer in return.
 * ----------------------------------------------------------------------------
 */

package com.evilreader.android.dbcontroller;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class is dedicated to control EvilReader communication with database.
 *  
 * @author Dainius Jocas
 *
 */
public class DBAdapter {
	/**************************************************************************
	 * Names and definitions for the EvilReader DB
	 *************************************************************************/
	public static final String DATABASE_NAME = "evilreaderdb";
	public static final int DATABASE_VERSION = 9;
	
	/**************************************************************************
	 * DEFINITIONS FOR NOTE TABLE
	 *************************************************************************/
	public static final String NOTE_TABLE_TITLE = "note";
	public static final String NOTE_ROWID = "rowid";
	public static final String NOTE_BODY  = "body";
	public static final String NOTE_BOOK_ID = "book_id";
	public static final String NOTE_CHAPTER = "chapter";
	public static final String NOTE_PARAGRAPH = "paragraph";
	private static final String CREATE_TABLE_NOTE =
	        "create table " 
	        + NOTE_TABLE_TITLE 
	        + " ( "
	        + NOTE_ROWID
	        + " integer primary key autoincrement, "
	        + NOTE_BODY
	        + " text not null, "
	        + NOTE_BOOK_ID 
	        + " text not null, "
	        + NOTE_CHAPTER
	        + " text not null, "
	        + NOTE_PARAGRAPH
	        + " text not null); ";
	/*************************************************************************/
	/**************************************************************************
	 * DEFINITIONS FOR HIGHLIGHT TABLE
	 *************************************************************************/
	public static final String HIGHLIGHT_TABLE_TITLE = "highlight";
	public static final String HIGHLIGHT_ROWID = "rowid";
	public static final String HIGHLIGHT_BOOK_ID = "book_id";
	public static final String HIGHLIGHT_CHAPTER = "chapter";
	public static final String HIGHLIGHT_PARAGRAPH = "paragraph";
	public static final String HIGHLIGHT_TEXT = "text"; 
	private static final String CREATE_TABLE_HIGHLIGHT =
	        "create table " 
	        + HIGHLIGHT_TABLE_TITLE 
	        + " ( "
	        + HIGHLIGHT_ROWID
	        + " integer primary key autoincrement, "
	        + HIGHLIGHT_TEXT 
	        + " text not null, "
	        + HIGHLIGHT_BOOK_ID 
	        + " text not null, "
	        + HIGHLIGHT_CHAPTER
	        + " text not null, "
	        + HIGHLIGHT_PARAGRAPH
	        + " text not null); ";
	/*************************************************************************/
	
	/**************************************************************************
	 * DEFINITIONS FOR BOOKMARK TABLE
	 *************************************************************************/
	public static String BOOKMARK_TABLE_TITLE = "bookmark";
	public static String BOOKMARK_ROWID = "rowid";
	public static String BOOKMARK_BOOK_ID = "bookid";
	public static String BOOKMARK_CHAPTER = "chapter";
	public static String BOOKMARK_PARAGRAPH = "paragraph";
	public static String CREATE_TABLE_BOOKMARK = 
			"create table " 
			        + BOOKMARK_TABLE_TITLE 
			        + " ( "
			        + BOOKMARK_ROWID
			        + " integer primary key autoincrement, "
			        + BOOKMARK_BOOK_ID 
			        + " text not null, "
			        + BOOKMARK_CHAPTER
			        + " text not null, "
			        + BOOKMARK_PARAGRAPH
			        + " text not null); ";
	
	/**************************************************************************
	 * DEFINITION OF LOCATION TABLE
	 **************************************************************************
	 * LOCATION_FIRST_WORD_NUMBER caption the exact location of an object
	 * related with an ebook. For example, I highlighted two words in a book, 
	 * so, my highlight started at the 5th word of the specific paragraph and
	 * length of selection was two words.
	 * 
	 * Other columns in my opinion are self explainable
	 */
	public static final String LOCATION_TABLE_TITLE = "location";
	// what if object will be deleted? Nothing reader will not take the record
	// into consideration.
	public static final String LOCATION_ROWID = "rowid";
	public static final String LOCATION_BOOK_ID = "book_id";
	public static final String LOCATION_CHAPTER_NUMBER = "chapter_number";
	public static final String LOCATION_PARAGRAPH_NUMBER = "paragraph_number";
	public static final String LOCATION_FIRST_WORD_NUMBER = "first_word_number";
	// LENGTH_OF_SELECTION is in words because it may span multiple paragraphs
	public static final String LOCATION_LENGTH_OF_SELECTION = "length_of_selection";
	private static final String DATABASE_CREATE_LOCATION = 
			"create table "
			+ LOCATION_TABLE_TITLE
			+ " ( "
			+ LOCATION_ROWID
			+ " integer primary key autoincrement, "
			+ LOCATION_BOOK_ID
			+ " text not null, "
			+ LOCATION_CHAPTER_NUMBER
			+ " text not null, "
			+ LOCATION_PARAGRAPH_NUMBER
			+ " text not null, "
			+ LOCATION_FIRST_WORD_NUMBER
			+ " text not null, "
			+ LOCATION_LENGTH_OF_SELECTION
			+ " text not null);";
	
	/**************************************************************************
	 * EVILBOOK table title
	 *************************************************************************/
	private static final String EVILBOOK_TABLE_TITLE = "evilbook";
	/**************************************************************************
     * EVILBOOK column names
     *************************************************************************/
	private static final String EVILBOOK_ROWID = "evilbook_rowid";
	private static final String EVILBOOK_ID = "evilbook_id";
	private static final String EVILBOOK_TITLE = "title";
	private static final String EVILBOOK_AUTHOR = "author";
	private static final String EVILBOOK_YEAR = "year";
	private static final String EVILBOOK_CHAPTERS = "chapters";
	private static final String EVILBOOK_FILENAME = "filename";
	private static final String EVILBOOK_ABSOLUTE_PATH = "absolute_path";
	private static final String EVILBOOK_IS_PRESENT = "is_present";
    /**************************************************************************
     * EVILBOOK table creation SQL statement
     *************************************************************************/
    private static final String CREATE_TABLE_EVILBOOK =
	        "create table " 
	        + EVILBOOK_TABLE_TITLE 
	        + " ( "
	        + EVILBOOK_ROWID
	        + " integer primary key autoincrement, "
	        + EVILBOOK_ID
	        + " text not null unique, "
	        + EVILBOOK_TITLE
	        + " text, "
	        + EVILBOOK_AUTHOR 
	        + " text, "
	        + EVILBOOK_YEAR
	        + " text, "
	        + EVILBOOK_CHAPTERS
	        + " text, "
	        + EVILBOOK_IS_PRESENT 
	        + " text, "
	        + EVILBOOK_FILENAME
	        + " text, "
	        + EVILBOOK_ABSOLUTE_PATH
	        + " text "
	        + ");";
    /*************************************************************************/
	
	//TODO(dainius): describe all the tables;
	//TODO(dainius): add code for all the table handlers.   
	
	private final static String TAG = "DBAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	private final Context mContext;
	
	/**
	 * Constructor - takes the context to allow database to be created or 
	 *   opened.
	 *   
	 *   @param context within which our DB should work
	 */
	public DBAdapter(Context context) {
		this.mContext = context;
	}

	/*
	 * Method for opening DB. If DB cannot be created then SQLException will
	 * be thrown.
	 * 
	 * @return this (self reference)
	 * @throws SQLException if DB cannot be neither opened nor created
	 */
	public DBAdapter open() throws SQLException {
		try {
			mDbHelper = new DatabaseHelper(mContext);
			mDb = mDbHelper.getWritableDatabase();
		} catch (Exception e) {
			int asd = 1;
			// TODO: handle exception
		}
		
		return this;
	}
	
	/*
     * Releasing resourses related to DB connection.
     * TODO(dainius): when should this method be used?
     */
	public void close() {
		mDbHelper.close();
	}

	/**
	 * We'll see what we can do with this static class.
	 * In documentation it is written that SQLiteOpenHelper class usually is 
	 * use to manage database creation and version management. 
	 * @author dainius
	 *
	 */
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        /**
         * All the create table statements are executed in separate db.execSQL
         * statements.
         */
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_EVILBOOK);
            db.execSQL(CREATE_TABLE_NOTE);
            db.execSQL(CREATE_TABLE_BOOKMARK);
            db.execSQL(CREATE_TABLE_HIGHLIGHT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + EVILBOOK_TABLE_TITLE);
            db.execSQL("DROP TABLE IF EXISTS " + BOOKMARK_TABLE_TITLE);
            db.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE_TITLE);
            db.execSQL("DROP TABLE IF EXISTS " + HIGHLIGHT_TABLE_TITLE);
            onCreate(db);
        }
    }
    
    /**************************************************************************
     * Handlers for bookmarks
     *************************************************************************/
    public long storeBookmark(String pBookId, String pChapter, 
    		String pParagraph) {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(BOOKMARK_BOOK_ID, pBookId);
    	initialValues.put(BOOKMARK_CHAPTER, pChapter);
    	initialValues.put(BOOKMARK_PARAGRAPH, pParagraph);
    	return mDb.insert(BOOKMARK_TABLE_TITLE, null, initialValues);
    }
    
    public Cursor fetchBookmarks(String pBookId) {
    	String[] columns = 
    		{BOOKMARK_BOOK_ID, BOOKMARK_CHAPTER, BOOKMARK_PARAGRAPH};
    	String[] selectionArgs = {pBookId};
    	Cursor cursor = mDb.query(
    			BOOKMARK_TABLE_TITLE, 
    			columns,
    			BOOKMARK_BOOK_ID + " = ? ",
    			selectionArgs,
    			null, null, null);
    	return cursor;
    }
    
    public int getNumberOfBookmarks(String pBookId) {
    	int numberOfBookmarks = 0;
    	this.open();
    	Cursor aCursor = fetchBookmarks(pBookId);
    	if (!aCursor.moveToFirst()) {
    		aCursor.close();
        	this.close();
    		return 0;
    	}
    	do {
    		numberOfBookmarks = numberOfBookmarks + 1;
    	} while (aCursor.moveToNext());
    	aCursor.close();
    	this.close();
    	return 0;
    }
    
    /**
     * Gets bookmark's location in the book. Location is described by array of
     * ints where:
     *  - location[0] is chapter number
     *  - location[1] is paragraph number 
     * If there is no such bookmark then {-1, -1} is returned.
     * @param pBookmarkRowId
     * @return
     */
    public int[] getLocationByBookmarkRowId(String pBookmarkRowId) {
    	int[] location = new int[2];
    	String[] columns = {BOOKMARK_CHAPTER, BOOKMARK_PARAGRAPH};
    	String[] selectionArgs = {pBookmarkRowId};
    	this.open();
    	Cursor aCursor = mDb.query(
    			BOOKMARK_TABLE_TITLE, 
    			columns, 
    			BOOKMARK_ROWID + " = ? ",
    			selectionArgs, 
    			null, 
    			null, 
    			null);
    	if (!aCursor.moveToFirst()) {
    		location[0] = -1;
    		location[1] = -1;
    		aCursor.close();
    		return location;
    	}
    	location[0] = Integer.parseInt(aCursor.getString(0));
    	location[1] = Integer.parseInt(aCursor.getString(1));
    	aCursor.close();
    	this.close();
    	return location;
    }
    
    /**
     * Makes an ArrayList of all the bookmarks for the specified book. 
     * If there are no bookmarks then null is returned!!!
     * @param pBookId
     * @return
     */
    public ArrayList<String> getAllBookmarkIdsByBookID(String pBookId) {
    	ArrayList<String> bookmarkIds = new ArrayList();
    	String[] columns = {BOOKMARK_ROWID};
    	String[] selectionArgs = {pBookId};
    	this.open();
    	Cursor aCursor = mDb.query(
    			BOOKMARK_TABLE_TITLE, 
    			columns,
    			BOOKMARK_BOOK_ID + " = ? ",
    			selectionArgs,
    			null, null, null);
    	if (!aCursor.moveToFirst()) {
    		aCursor.close();
    		this.close();
    		return null;
    	}
    	do {
    		 bookmarkIds.add(aCursor.getString(0));
    	} while (aCursor.moveToNext());
    	this.close();
    	return bookmarkIds;
    }
    /*************************************************************************/
    
    /**************************************************************************
     * Handlers for highlights
     *************************************************************************/
    public long storeHighlight(String pBookId, String pChapter, 
    		String pParagraph, String pHighlightedText) {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(HIGHLIGHT_BOOK_ID, pBookId);
    	initialValues.put(HIGHLIGHT_CHAPTER, pChapter);
    	initialValues.put(HIGHLIGHT_PARAGRAPH, pParagraph);
    	initialValues.put(HIGHLIGHT_TEXT, pHighlightedText);
    	return mDb.insert(HIGHLIGHT_TABLE_TITLE, null, initialValues);
    }
    
    public Cursor fetchHighlights(String pBookId) {
    	String[] columns = 
    		{HIGHLIGHT_BOOK_ID, HIGHLIGHT_CHAPTER, HIGHLIGHT_PARAGRAPH, 
    			HIGHLIGHT_TEXT};
    	String[] selectionArgs = {pBookId};
    	Cursor cursor = mDb.query(
    			HIGHLIGHT_TABLE_TITLE, 
    			columns,
    			HIGHLIGHT_BOOK_ID + " = ? ",
    			selectionArgs,
    			null, null, null);
    	return cursor;
    }
    /*************************************************************************/
    
    /************************************************************************** 
     * This series of functions is dedicated to work with NOTES table
     *************************************************************************/
    
    /**
     * Create one entry in the DB table NOTES when body of note and bookId of
     * the eBook to which note is assigned are provided. Is success then rowId 
     * is returned, otherwise -1.
     *  
     * @param body - actual content of the note
     * @param bookId - to which book note is assigned
     * @return rowId or -1 if failed
     */
    public long storeNote(String pBody, String pBookId, String pChapter,
    		String pParagraph) {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(NOTE_BODY, pBody);
    	initialValues.put(NOTE_BOOK_ID, pBookId);
    	initialValues.put(NOTE_CHAPTER, pChapter);
    	initialValues.put(NOTE_PARAGRAPH, pParagraph);
    	return mDb.insert(NOTE_TABLE_TITLE, null, initialValues);
    }
    
    /**
     * Get all notes for a specific book.
     * @return cursor over all selected notes for a specific book.
     */
    public Cursor fetchAllNotes(String pBookId) {
    	String[] columns = 
    		{NOTE_BOOK_ID, NOTE_CHAPTER, NOTE_PARAGRAPH, NOTE_BODY};
    	String[] selectionArgs = {pBookId};
    	Cursor cursor = mDb.query(
    			NOTE_TABLE_TITLE, 
    			columns,
    			NOTE_BOOK_ID + " = ? ",
    			selectionArgs,
    			null, null, null);
    	return cursor;
    }
    
    /**
     * Delete a note with a given note ID
     * @param rowId - id of the note that should be deleted
     * @return true if success, otherwise - false
     */
    public boolean deleteNote(long rowId) {
    	return mDb.delete(NOTE_TABLE_TITLE, NOTE_BOOK_ID + "=" + rowId,
    			null) > 0;
    }
    
    /**
     * Return a cursor positioned at the note that matches the given noteId.
     * 
     * @param noteId
     * @return cursor positioned to a matching note
     * @throws SQLException if note cannot be found/retrieved
     */
    public Cursor fetchNote(long noteId) throws SQLException {
    	Cursor mCursor = mDb.query(NOTE_TABLE_TITLE, 
    			new String[] {NOTE_ROWID, NOTE_BODY},
    			NOTE_ROWID + "=" + noteId, null, null, null, null);
    	if (mCursor != null) {
    		mCursor.moveToFirst();
    	}
    	return mCursor;
    }
    
    /**
     * Updates specific note with specific content. If success the returns true,
     * otherwise false
     * 
     * @param noteId
     * @param body - new content of the note
     * @return isSuccess - true when update was successful, otherwise false
     */
    public boolean updateNote(long noteId, String body) {
    	ContentValues content_values = new ContentValues();
    	content_values.put(NOTE_BODY, body);
    	boolean isSuccess = mDb.update(NOTE_TABLE_TITLE, content_values,
    			NOTE_ROWID + "=" + noteId, null) > 0;
    	return isSuccess;
    }
    /*************************************************************************/
    
    /**************************************************************************
     * TODO(dainius) create controllers for LOCATIONS table.
     *************************************************************************/
    
    /**
     * 
     * @param book_id
     * @param chapterNumber
     * @param paragraphNumber
     * @param firstWordNumber
     * @param lengthOfSelection
     * @return
     */
    public long storeLocation(String book_id, String chapterNumber, 
    		String paragraphNumber, String firstWordNumber,
    		String lengthOfSelection) {
    	ContentValues contentValues = new ContentValues();
    	contentValues.put(LOCATION_BOOK_ID, book_id);
    	contentValues.put(LOCATION_CHAPTER_NUMBER, chapterNumber);
    	contentValues.put(LOCATION_PARAGRAPH_NUMBER, paragraphNumber);
    	contentValues.put(LOCATION_FIRST_WORD_NUMBER, firstWordNumber);
    	contentValues.put(LOCATION_LENGTH_OF_SELECTION, lengthOfSelection);
    	return mDb.insert(LOCATION_TABLE_TITLE, null, contentValues);
    }
    
    public Cursor getLocation(String location_id) throws SQLException {
    	Cursor mCursor = mDb.query(LOCATION_TABLE_TITLE, 
    			new String[] {LOCATION_ROWID,
    			LOCATION_CHAPTER_NUMBER,
    			LOCATION_PARAGRAPH_NUMBER},
    			NOTE_ROWID + "=" + location_id, null, null, null, null);
    	if (mCursor != null) {
    		mCursor.moveToFirst();
    	}
    	return mCursor;
    }
    
    
    
    /**************************************************************************
     * HANDLERS OF EVILBOOK TABLE
     *************************************************************************/
    /**
     * Stores EvilBook entry in database. Is the book exists then replace the 
     * old row in the table (DANGER).
     * 
     * @param title
     * @param pAuthor
     * @param pFilename
     * @param pAbsolutePath
     * @return rowid if success or -1 if failed to store
     */
    public long storeEvilBook(String pTitle, String pAuthor, String pYear, 
    		String pChapters, String pFilename, String pAbsolutePath) {
    	long rowid;
    	ContentValues values = new ContentValues();
    	String anEvilBookId = 
    			constructEvilBookId(pTitle, pAuthor, pYear, pAbsolutePath);
    	values.put(EVILBOOK_ID, anEvilBookId);
    	values.put(EVILBOOK_TITLE, pTitle);
    	values.put(EVILBOOK_AUTHOR, pAuthor);
    	values.put(EVILBOOK_YEAR, pYear);
    	values.put(EVILBOOK_CHAPTERS, pChapters);
    	values.put(EVILBOOK_IS_PRESENT, "true");
    	values.put(EVILBOOK_FILENAME, pFilename);
    	values.put(EVILBOOK_ABSOLUTE_PATH, pAbsolutePath);
    	rowid = mDb.insertWithOnConflict(EVILBOOK_TABLE_TITLE, null, values,
    			SQLiteDatabase.CONFLICT_REPLACE);
    	return rowid;
    }
    
    /**
     * Fetches a cursor to all EvilBooks
     * 
     * @return Cursor
     */
    public Cursor fetchAllEvilBooks() {
    	Cursor aCursorToAllEvilBooks;
    	String[] columns = {EVILBOOK_ABSOLUTE_PATH, EVILBOOK_ROWID};
    	String[] aSelectionArgs = {"true"};
    	aCursorToAllEvilBooks = this.mDb.query(
    			EVILBOOK_TABLE_TITLE,
    			columns, 
    			EVILBOOK_IS_PRESENT + " =  ? ", 
    			aSelectionArgs,
    			null, 
    			null, 
    			null);
    	return aCursorToAllEvilBooks;
    }
    
    /**
     * Get all the column files filenames of ebooks
     * 
     * @return cursor to the results
     */
    public Cursor getFilenamesOfEvilBooks() {
    	Cursor cursorToFilenamesOfEvilBooks;
    	String[] columns = {EVILBOOK_FILENAME};
    	String[] aSelectionArgs = {"true"};
    	cursorToFilenamesOfEvilBooks = this.mDb.query(
    			EVILBOOK_TABLE_TITLE,
    			columns, 
    			EVILBOOK_IS_PRESENT + " =  ? ", 
    			aSelectionArgs,
    			null, 
    			null, 
    			null);
    	return cursorToFilenamesOfEvilBooks;
    }
    
    /**
     * Gets cursor to all titles of evil books in the evilbook table
     * 
     * @return cursor
     */
    public Cursor getTitlesOfEvilBooks() {
    	Cursor cursorToTitlesOfEvilBooks;
    	String[] columns = {EVILBOOK_TITLE};
    	String[] selectionArgs = {"true"};
    	cursorToTitlesOfEvilBooks = this.mDb.query(
    			EVILBOOK_TABLE_TITLE,
    			columns, 
    			EVILBOOK_IS_PRESENT + " =  ? ", 
    			selectionArgs,
    			null, 
    			null, 
    			null);
    	return cursorToTitlesOfEvilBooks;
    }
    
    /**
     * Collects all titles and absolute paths from evil books that are present
     * in the DB.
     * 
     * @return Cursor to query result
     */
    public Cursor getTitlesAndPathsOfEvilBooks() {
    	Cursor aCursorToTitlesAndPaths;
    	String[] columns = {EVILBOOK_TITLE, EVILBOOK_ABSOLUTE_PATH};
    	String[] selectionArgs = {"true"};
    	aCursorToTitlesAndPaths = this.mDb.query(
    			EVILBOOK_TABLE_TITLE, 
    			columns, 
    			EVILBOOK_IS_PRESENT + " = ?", 
    			selectionArgs, 
    			null, 
    			null, 
    			null);
    	return aCursorToTitlesAndPaths;
    }
    
    /**
     * Constructs an Id for an EvilBook in that way: takes first word of title,
     * concatenates it with publication year, first word of author and absolute
     * path. Similar to Google Scholar BibTeX format.
     * 
     * @return String anEvilBookId
     */
    public String constructEvilBookId(String pTitle, String pAuthor,
    		String pYear, String pAbsolutePath) {
    	String anEvilBookId = "";
  
    	String aRegexForTitle = " ";
    	String aTitle = pTitle.split(aRegexForTitle)[0];
    	
    	String aRegexForAuthor = ",";
    	String anAuthor = pAuthor.split(aRegexForAuthor)[0];
    	
    	anEvilBookId = aTitle + pYear + anAuthor + pAbsolutePath;
    	return anEvilBookId;
    }
    
    /**
     * Set column EVILBOOK_IS_PRESENT value specified row id.
     * 
     * @param long pEvilBookRowId
     * @param String pTrueOrFalse
     * @return boolean if true - OK, false - strange because it should be true.
     */
    public boolean markEvilBookStatus(long pEvilBookRowId, 
    		String pTrueOrFalse) {
    	boolean isUpdated = false;
    	ContentValues values = new ContentValues();
    	values.put(EVILBOOK_IS_PRESENT, pTrueOrFalse);
    	isUpdated = this.mDb.update(
    			EVILBOOK_TABLE_TITLE, 
    			values, 
    			EVILBOOK_ROWID + "=" + pEvilBookRowId, 
    			null) > 0;
    	return isUpdated;
    }

	public Cursor getAuthorsTitlesPathsIdsOfEvilBooks() {
		Cursor aCursorToTitlesPathsAndIds;
    	String[] columns = {EVILBOOK_AUTHOR, EVILBOOK_TITLE,
    			EVILBOOK_ABSOLUTE_PATH, EVILBOOK_ID};
    	String[] selectionArgs = {"true"};
    	aCursorToTitlesPathsAndIds = this.mDb.query(
    			EVILBOOK_TABLE_TITLE, 
    			columns, 
    			EVILBOOK_IS_PRESENT + " = ?", 
    			selectionArgs, 
    			null, 
    			null, 
    			null);
    	return aCursorToTitlesPathsAndIds;
	}
	
	public String getEvilBookAuthor(String pBookId) {
		this.open();
		String anAuthor = "";
		Cursor aCursorToAuthor;
		String[] column = {EVILBOOK_AUTHOR};
		String[] selectionArgs = {pBookId};
		aCursorToAuthor = this.mDb.query(
    			EVILBOOK_TABLE_TITLE, 
    			column, 
    			EVILBOOK_ID + " = ?", 
    			selectionArgs, 
    			null, 
    			null, 
    			null);
		if (!aCursorToAuthor.moveToFirst()) {
			anAuthor = "Unknown";
			aCursorToAuthor.close();
			this.close();
			return anAuthor;
		}
		anAuthor = aCursorToAuthor.getString(0);
		aCursorToAuthor.close();
		this.close();
		return anAuthor;
	}
	
	public String getEvilBookTitle(String pBookId) {
		this.open();
		String aTitle = "";
		Cursor aCursorToTitle;
		String[] column = {EVILBOOK_TITLE};
		String[] selectionArgs = {pBookId};
		aCursorToTitle = this.mDb.query(
    			EVILBOOK_TABLE_TITLE, 
    			column, 
    			EVILBOOK_ID + " = ?", 
    			selectionArgs, 
    			null, 
    			null, 
    			null);
		if (!aCursorToTitle.moveToFirst()) {
			aTitle = "Unknown";
			aCursorToTitle.close();
			this.close();
			return aTitle;
		}
		aTitle = aCursorToTitle.getString(0);
		aCursorToTitle.close();
		this.close();
		return aTitle;
	}
		
	public String getEvilBookYear(String pBookId) {
			this.open();
			String aYear = "";
			Cursor aCursorToYear;
			String[] column = {EVILBOOK_YEAR};
			String[] selectionArgs = {pBookId};
			aCursorToYear = this.mDb.query(
	    			EVILBOOK_TABLE_TITLE, 
	    			column, 
	    			EVILBOOK_ID + " = ?", 
	    			selectionArgs, 
	    			null, 
	    			null, 
	    			null);
			if (!aCursorToYear.moveToFirst()) {
				aYear = "Unknown";
				aCursorToYear.close();
				this.close();
				return aYear;
			}
			aYear = aCursorToYear.getString(0);
			aCursorToYear.close();
			this.close();
			return aYear;
	}
	
	public int getNumberOfEvilBookChapters(String pBookId) {
		this.open();
		String aNumberOfChapters = "";
		Cursor aCursorToNumberOfChapter;
		String[] column = {EVILBOOK_CHAPTERS};
		String[] selectionArgs = {pBookId};
		aCursorToNumberOfChapter = this.mDb.query(
    			EVILBOOK_TABLE_TITLE, 
    			column, 
    			EVILBOOK_ID + " = ?", 
    			selectionArgs, 
    			null, 
    			null, 
    			null);
		if (!aCursorToNumberOfChapter.moveToFirst()) {
			aCursorToNumberOfChapter.close();
			this.close();
			return 0;
		}
		aNumberOfChapters = aCursorToNumberOfChapter.getString(0);
		aCursorToNumberOfChapter.close();
		this.close();
		
		return Integer.parseInt(aNumberOfChapters);
}
    /*************************************************************************/

}
