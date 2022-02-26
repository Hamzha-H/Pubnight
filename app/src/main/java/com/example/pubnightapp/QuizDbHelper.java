package com.example.pubnightapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.pubnightapp.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pubnightapp";
    private static final int DATABASE_VERSION = 1;
    private static QuizDbHelper instance;
    private SQLiteDatabase db;
    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";
        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
    private void fillCategoriesTable() {
        Category c1 = new Category("Countries");
        insertCategory(c1);
        Category c2 = new Category("City");
        insertCategory(c2);
        Category c3 = new Category("Football");
        insertCategory(c3);
        Category c4 = new Category("Celebrities");
        insertCategory(c4);
    }
    public void addCategory(Category category) {
        db = getWritableDatabase();
        insertCategory(category);
    }
    public void addCategories(List<Category> categories) {
        db = getWritableDatabase();
        for (Category category : categories) {
            insertCategory(category);
        }
    }
    private void insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }
    private void fillQuestionsTable() {
        PubnightQuestions q1 = new PubnightQuestions("Floppy hats called berets are associated with what culture?",
                "British", "French", "American", 2,
                PubnightQuestions.DIFFICULTY_EASY, Category.Countries);
        insertQuestion(q1);
        PubnightQuestions q2 = new PubnightQuestions("What are people of Sweden referred to as?",
                "Germans", "British", "Swedes", 2,
                PubnightQuestions.DIFFICULTY_EASY, Category.Countries);
        insertQuestion(q2);
        PubnightQuestions q3 = new PubnightQuestions("In which country would you find Buckingham Palace?",
                "Scotland", "Ireland", "England", 3,
                PubnightQuestions.DIFFICULTY_EASY, Category.Countries);
        insertQuestion(q3);

        PubnightQuestions q4 = new PubnightQuestions("The Japanese flag shows a circle of what color on a white background?",
                "Green", "White", "Red", 1,
                PubnightQuestions.DIFFICULTY_MEDIUM, Category.Countries);
        insertQuestion(q4);
        PubnightQuestions q5 = new PubnightQuestions("Enchiladas, burritos, and tacos are examples of what nation's food?",
                "Brazil", "Mexico", "Australia", 2,
                PubnightQuestions.DIFFICULTY_MEDIUM, Category.Countries);
        insertQuestion(q5);
        PubnightQuestions q6 = new PubnightQuestions("In 2007, who was the monarch of England?",
                "Queen Elizabeth II", "Donald Trump", "Angela Merkel", 1,
                PubnightQuestions.DIFFICULTY_MEDIUM, Category.Countries);
        insertQuestion(q6);

        PubnightQuestions q7 = new PubnightQuestions("Residents of Peru are referred to as what?",
                "Taiwanese", "Peruvian", "Vietnamese", 2,
                PubnightQuestions.DIFFICULTY_HARD, Category.Countries);
        insertQuestion(q7);
        PubnightQuestions q8 = new PubnightQuestions("What season is it in Australia during December?",
                "Spring", "Winter", "Summer", 3,
                PubnightQuestions.DIFFICULTY_HARD, Category.Countries);
        insertQuestion(q8);
        PubnightQuestions q9 = new PubnightQuestions("India's national bird is what colorfully-plumed creature?",
                "Peacock", "Elephant", "Tiger", 1,
                PubnightQuestions.DIFFICULTY_HARD, Category.Countries);
        insertQuestion(q9);

        PubnightQuestions q10 = new PubnightQuestions("What is the Capital City of Spain?",
                "New York", "London", "Madrid", 3,
                PubnightQuestions.DIFFICULTY_EASY, Category.City);
        insertQuestion(q10);
        PubnightQuestions q11 = new PubnightQuestions("Which Capital City is home to Buckingham Palace?",
                "Glasgow", "Belfast", "London", 3,
                PubnightQuestions.DIFFICULTY_EASY, Category.City);
        insertQuestion(q11);
        PubnightQuestions q12 = new PubnightQuestions("The Capital City of which Country is known to be the coldest in the World??",
                "Mongolia", "Canada", "Sweden", 1,
                PubnightQuestions.DIFFICULTY_EASY, Category.City);
        insertQuestion(q12);

        PubnightQuestions q13 = new PubnightQuestions("The Worlds 16th biggest City is South Korea’s Capital, what is its name?",
                "Kabul", "Seoul", "Beijing", 2,
                PubnightQuestions.DIFFICULTY_MEDIUM, Category.City);
        insertQuestion(q13);
        PubnightQuestions q14 = new PubnightQuestions("Where is the Eiffel tower",
                "Washington DC", "Moscow", "Paris", 3,
                PubnightQuestions.DIFFICULTY_MEDIUM, Category.City);
        insertQuestion(q14);
        PubnightQuestions q15 = new PubnightQuestions("Name the Capital City of Croatia",
                "Berlin", "Paris", "Zagreb", 3,
                PubnightQuestions.DIFFICULTY_MEDIUM, Category.City);
        insertQuestion(q15);

        PubnightQuestions q16 = new PubnightQuestions("What is the capital city of Switzerland?",
                "Rome", "Warsaw", "Bern", 2,
                PubnightQuestions.DIFFICULTY_HARD, Category.City);
        insertQuestion(q16);
        PubnightQuestions q17 = new PubnightQuestions("What capital city would you be in if you were on a boat on the Seine river?",
                "Paris", "Brussels", "Bern", 1,
                PubnightQuestions.DIFFICULTY_HARD, Category.City);
        insertQuestion(q17);
        PubnightQuestions q18 = new PubnightQuestions("In which country would you find the capital city of Sofia? ",
                "Bulgaria", "Syria", "Pakistan", 1,
                PubnightQuestions.DIFFICULTY_HARD, Category.City);
        insertQuestion(q18);

    }
    public void addQuestion(PubnightQuestions question) {
        db = getWritableDatabase();
        insertQuestion(question);
    }
    public void addQuestions(List<PubnightQuestions> questions) {
        db = getWritableDatabase();
        for (PubnightQuestions question : questions) {
            insertQuestion(question);
        }
    }
    private void insertQuestion(PubnightQuestions question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }
    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }
        c.close();
        return categoryList;
    }
    public ArrayList<PubnightQuestions> getAllQuestions() {
        ArrayList<PubnightQuestions> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                PubnightQuestions question = new PubnightQuestions();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
    public ArrayList<PubnightQuestions> getQuestions(int categoryID, String difficulty) {
        ArrayList<PubnightQuestions> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String selection = QuizContract.QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuizContract.QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};
        Cursor c = db.query(
                QuizContract.QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            do {
                PubnightQuestions question = new PubnightQuestions();
                question.setId(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}