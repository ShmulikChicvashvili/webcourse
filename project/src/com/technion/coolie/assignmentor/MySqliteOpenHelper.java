package com.technion.coolie.assignmentor;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.util.Log;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

	private Context context;
	
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "AssignMentor";
 
    // Table Names
    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_DONE_TASKS = "doneTasks";
 
    // Common column names
    private static final String KEY_ID = "_id";
    private static final String KEY_TASK_NAME = "task_name";
    private static final String KEY_COURSE_NAME = "course_name";
    private static final String KEY_COURSE_ID = "course_id";
    private static final String KEY_DUE_DATE = "due_date";
    private static final String KEY_DONE = "done";
    private static final String KEY_DIFFICULTY = "difficulty";
    private static final String KEY_IMPORTANCE = "importance";
    private static final String KEY_PROGRESS = "progress";
    private static final String KEY_URL = "url";
    private static final String KEY_EVENT_ID = "event_id";
 
 
    // Tables Create Statements
    private static final String CREATE_TABLE_TASKS = "CREATE TABLE "
            + TABLE_TASKS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TASK_NAME
            + " TEXT," + KEY_COURSE_NAME + " TEXT," + KEY_COURSE_ID + " TEXT," 
            + KEY_DUE_DATE + " TEXT," + KEY_DONE + " INTEGER," + KEY_DIFFICULTY + " INTEGER,"
            + KEY_IMPORTANCE + " INTEGER," + KEY_PROGRESS + " INTEGER," 
            + KEY_URL + " TEXT," + KEY_EVENT_ID + " INTEGER" + ")";
    
    private static final String CREATE_TABLE_DONE_TASKS = "CREATE TABLE "
            + TABLE_TASKS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TASK_NAME
            + " TEXT," + KEY_COURSE_NAME + " TEXT," + KEY_COURSE_ID + " TEXT," 
            + KEY_DUE_DATE + " TEXT," + KEY_DONE + " INTEGER," + KEY_DIFFICULTY + " INTEGER,"
            + KEY_IMPORTANCE + " INTEGER," + KEY_PROGRESS + " INTEGER," 
            + KEY_URL + " TEXT," + KEY_EVENT_ID + " INTEGER" + ")";
 
    public MySqliteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_TABLE_TASKS);
//        db.execSQL(CREATE_TABLE_DONE_TASKS);
//        db.execSQL(CREATE_TABLE_TODO_TAG);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DONE_TASKS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_TAG);
 
        // create new tables
        onCreate(db);
    }
 
    // ------------------------ "tasks" table methods ----------------//
 
     /**
     * Creating a task
     */
    public long createTask(TasksInfo task) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_NAME, task.taskName.toString());
        values.put(KEY_COURSE_NAME, task.courseName);
        values.put(KEY_COURSE_ID, task.courseId);
        values.put(KEY_DUE_DATE, formatDueDate(task.dueDate));
        values.put(KEY_DONE, task.isDone);
        values.put(KEY_DIFFICULTY, task.difficulty);
        values.put(KEY_IMPORTANCE, task.importance);
        values.put(KEY_PROGRESS, task.progress);
        values.put(KEY_URL, task.url);
        values.put(KEY_EVENT_ID, task.eventID);
 
        // insert row
        long task_id = db.insert(TABLE_TASKS, null, values);
 
        return task_id;
    }
    
    private TasksInfo getTasksInfo(Cursor c) {
    	int id = c.getInt(c.getColumnIndex(KEY_ID));
        String taskName = c.getString(c.getColumnIndex(KEY_TASK_NAME));
        String courseName = c.getString(c.getColumnIndex(KEY_COURSE_NAME));
        String courseId = c.getString(c.getColumnIndex(KEY_COURSE_ID));
        String DbDueDate = c.getString(c.getColumnIndex(KEY_DUE_DATE));
        String dueDate = formatDbDueDate(DbDueDate);
        int done = c.getInt(c.getColumnIndex(KEY_DONE));
        boolean isDone = (done == 1);
        int difficulty = c.getInt(c.getColumnIndex(KEY_DIFFICULTY));
        int importance = c.getInt(c.getColumnIndex(KEY_IMPORTANCE));
        int progress = c.getInt(c.getColumnIndex(KEY_PROGRESS));
        String url = c.getString(c.getColumnIndex(KEY_URL));
        int eventID = c.getInt(c.getColumnIndex(KEY_EVENT_ID));
        TasksInfo task = new TasksInfo(new SpannableString(taskName), courseName, courseId, 
        		dueDate, isDone, difficulty, importance, progress, url, eventID);
        task.id = id;
        
        return task;
    }
    
    private String formatDueDate(String dueDate) {
		if (dueDate == null || dueDate.isEmpty()) return "";
//		Log.i(MainActivity.AM_TAG, "Due date is: " + dueDate);
    	String[] dateArr = dueDate.split("/");
    	String day = dateArr[0];
    	String month = dateArr[1];
    	String year = dateArr[2];
    	if (day.length() != 2) day = "0" + day;
    	if (month.length() != 2) month = "0" + month;
    	if (year.length() == 2) year = "20" + year;
    	String DbDueDate = year + month + day;
    	return DbDueDate;
    }
    
    private String formatDbDueDate(String DbDueDate) {
    	if (DbDueDate.isEmpty()) return "";
    	String year = DbDueDate.substring(0, 4);
    	String month = DbDueDate.substring(4, 6);
    	String day = DbDueDate.substring(6);
    	return day + "/" + month + "/" + year;
    }
    
    private int boolToInt(boolean done) {
    	if(done) return 1;
    	else return 0;
    }
 
    /**
     * get single task
     */
    public TasksInfo getTask(long task_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS + " WHERE "
                + KEY_ID + " = " + task_id;
 
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
 
        return getTasksInfo(c);
    }
 
    /**
     * getting all todos
     * */
    public List<TasksInfo> getAllTasks() {
    	// First get the 'sort by' method, and then pull the data, ordered by that method.
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    	int sort_by = prefs.getInt(MainActivity.KEY_AM_PREFS_SORT_BY, 0);
    	String orderBy = "ORDER BY ";
    	switch(sort_by) {
    	case 0:
    		orderBy = orderBy + KEY_DUE_DATE + " ASC";
    		break;
    		
    	case 1:
    		orderBy = orderBy + KEY_DIFFICULTY + " DESC, " + KEY_DUE_DATE + " ASC";
    		break;
    		
    	case 2:
    		orderBy = orderBy + KEY_IMPORTANCE + " DESC, " + KEY_DUE_DATE + " ASC";
    		break;
    		
    	case 3:
    		orderBy = orderBy + KEY_PROGRESS + " DESC, " + KEY_DUE_DATE + " ASC";
    		break;
    	}
    	
        List<TasksInfo> tasks = new ArrayList<TasksInfo>();
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS + " " + orderBy ;
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                tasks.add(getTasksInfo(c));
            } while (c.moveToNext());
        }
        return tasks;
    }
 
 
    /**
     * getting tasks count
     */
    public int getTaskCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
 
        int count = cursor.getCount();
        cursor.close();
 
        // return count
        return count;
    }
    
    /**
     * Updating a task
     */
    public int updateTask(TasksInfo task) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_NAME, task.taskName.toString());
        values.put(KEY_COURSE_NAME, task.courseName);
        values.put(KEY_COURSE_ID, task.courseId);
        values.put(KEY_DUE_DATE, formatDueDate(task.dueDate));
        values.put(KEY_DONE, boolToInt(task.isDone));
        values.put(KEY_DIFFICULTY, task.difficulty);
        values.put(KEY_IMPORTANCE, task.importance);
        values.put(KEY_PROGRESS, task.progress);
        values.put(KEY_URL, task.url);
        values.put(KEY_EVENT_ID, task.eventID);
 
        // updating row
        return db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(task.id) });
    }
 
    /**
     * Deleting a task
     */
    public void deleteTask(long task_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?",
                new String[] { String.valueOf(task_id) });
    }
}
