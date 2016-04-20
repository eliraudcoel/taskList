package com.example.appy.technictasks.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLite extends SQLiteOpenHelper {

    /* INSERT CONTACT */
    private static final String CREATE_BDD_CONTACT = "CREATE TABLE CONTACT (ID_CONTACT INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, LAST_NAME TEXT, ADRESS TEXT, POSTCODE TEXT, CITY TEXT, PHONE_NUMBER TEXT);";
    private static final String INSERT_BDD_CONTACT_1 = "INSERT INTO CONTACT (FIRST_NAME, LAST_NAME, ADRESS, POSTCODE, CITY, PHONE_NUMBER) VALUES ('Paul', 'Rolland', '23 rue la fayette', '75010', 'Paris','0664798596');";
    private static final String INSERT_BDD_CONTACT_2 = "INSERT INTO CONTACT (FIRST_NAME, LAST_NAME, ADRESS, POSTCODE, CITY, PHONE_NUMBER) VALUES ('Sandrine', 'Douroux', '96 rue la fayette', '75010', 'Paris','0662351984');";
    private static final String INSERT_BDD_CONTACT_3 = "INSERT INTO CONTACT (FIRST_NAME, LAST_NAME, ADRESS, POSTCODE, CITY, PHONE_NUMBER) VALUES ('Patrice', 'Martin', '3 boulevard diderot', '75012', 'Paris','0662351984');";
    private static final String INSERT_BDD_CONTACT_4 = "INSERT INTO CONTACT (FIRST_NAME, LAST_NAME, ADRESS, POSTCODE, CITY, PHONE_NUMBER) VALUES ('Marc', 'Martin', '12 avenue de Laumière', '75019', 'Paris','0664798596');";

    /* INSERT USER */
    private static final String CREATE_BDD_USER = "CREATE TABLE USER (ID_USER INTEGER PRIMARY KEY AUTOINCREMENT, LOGIN TEXT, MDP TEXT, EMAIL TEXT, CONTACT_ID INTEGER, TOKEN TEXT);";
    private static final String INSERT_BDD_USER_1 = "INSERT INTO USER (LOGIN, MDP, EMAIL, CONTACT_ID) VALUES ('paul', 'test', 'paul@example.com', 1);";
    private static final String INSERT_BDD_USER_2 = "INSERT INTO USER (LOGIN, MDP, EMAIL, CONTACT_ID) VALUES ('marc', 'test', 'marc@example.com', 2);";

    /* INSERT CUSTOMER */
    private static final String CREATE_BDD_CUSTOMER = "CREATE TABLE CUSTOMER (ID_CUSTOMER INTEGER PRIMARY KEY AUTOINCREMENT, CONTACT_ID INTEGER);";
    private static final String INSERT_BDD_CUSTOMER_1 = "INSERT INTO CUSTOMER (CONTACT_ID) VALUES (2);";
    private static final String INSERT_BDD_CUSTOMER_2 = "INSERT INTO CUSTOMER (CONTACT_ID) VALUES (3);";

    /* INSERT TASK */
    private static final String CREATE_BDD_TASK = "CREATE TABLE TASK (ID_TASK INTEGER PRIMARY KEY AUTOINCREMENT, CUSTOMER_ID INTEGER, USER_ID INTEGER, MACHINE_TYPE TEXT, MACHINE_BRAND, PROBLEM TEXT, DATE DATETIME, TERMINATED TEXT, TIME_SPEND TEXT, COMMENT TEXT);";
    private static final String INSERT_BDD_TASK_1 = "INSERT INTO TASK (CUSTOMER_ID, USER_ID, MACHINE_TYPE, MACHINE_BRAND, PROBLEM, DATE, TERMINATED, TIME_SPEND) VALUES (1, 1, 'télévision', 'SAMSUNG', \"Problème antenne\", '2016-04-20 10:26', 'false', 0 );";
    private static final String INSERT_BDD_TASK_2 = "INSERT INTO TASK (CUSTOMER_ID, USER_ID, MACHINE_TYPE, MACHINE_BRAND, PROBLEM, DATE, TERMINATED, TIME_SPEND) VALUES (1, 2, 'machine à laver', 'BOSH', \"Problème d'entrée d'eau\", '2016-05-10 20:26', 'false', 0 );";
    private static final String INSERT_BDD_TASK_3 = "INSERT INTO TASK (CUSTOMER_ID, USER_ID, MACHINE_TYPE, MACHINE_BRAND, PROBLEM, DATE, TERMINATED, TIME_SPEND) VALUES (1, 2, 'séchoir', 'BOSH', \"Problème chaleur\", '2016-05-10 15:26', 'false', 0 );";
    private static final String INSERT_BDD_TASK_4 = "INSERT INTO TASK (CUSTOMER_ID, USER_ID, MACHINE_TYPE, MACHINE_BRAND, PROBLEM, DATE, TERMINATED, TIME_SPEND) VALUES (1, 1, 'ordinateur', 'ACER', \"Problème d'alimentation\", '2016-05-15 15:26', 'false', 0 );";
    private static final String INSERT_BDD_TASK_5 = "INSERT INTO TASK (CUSTOMER_ID, USER_ID, MACHINE_TYPE, MACHINE_BRAND, PROBLEM, DATE, TERMINATED, TIME_SPEND) VALUES (1, 2, 'ordinateur', 'MSI', \"Problème d'écran\", '2016-04-19 11:00', 'false', 0 );";
    private static final String INSERT_BDD_TASK_6 = "INSERT INTO TASK (CUSTOMER_ID, USER_ID, MACHINE_TYPE, MACHINE_BRAND, PROBLEM, DATE, TERMINATED, TIME_SPEND) VALUES (1, 1, 'téléphone', 'ACER', \"Problème de lumière\", '2016-05-12 18:15', 'false', 0 );";

    public SQLite(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /* INSERT CONTACT */
        db.execSQL(CREATE_BDD_CONTACT);
        db.execSQL(INSERT_BDD_CONTACT_1);
        db.execSQL(INSERT_BDD_CONTACT_2);
        db.execSQL(INSERT_BDD_CONTACT_3);
        db.execSQL(INSERT_BDD_CONTACT_4);

        /* INSERT USER */
        db.execSQL(CREATE_BDD_USER);
        db.execSQL(INSERT_BDD_USER_1);
        db.execSQL(INSERT_BDD_USER_2);

        /* INSERT CUSTOMER */
        db.execSQL(CREATE_BDD_CUSTOMER);
        db.execSQL(INSERT_BDD_CUSTOMER_1);
        db.execSQL(INSERT_BDD_CUSTOMER_2);

        /* INSERT TASK */
        db.execSQL(CREATE_BDD_TASK);
        db.execSQL(INSERT_BDD_TASK_1);
        db.execSQL(INSERT_BDD_TASK_2);
        db.execSQL(INSERT_BDD_TASK_3);
        db.execSQL(INSERT_BDD_TASK_4);
        db.execSQL(INSERT_BDD_TASK_5);
        db.execSQL(INSERT_BDD_TASK_6);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE TASK");
        db.execSQL("DROP TABLE CUSTOMER");
        db.execSQL("DROP TABLE USER");
        db.execSQL("DROP TABLE CONTACT");
        onCreate(db);
    }

}
