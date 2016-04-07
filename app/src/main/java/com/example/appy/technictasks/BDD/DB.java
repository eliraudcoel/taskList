package com.example.appy.technictasks.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appy.technictasks.Models.Contact;
import com.example.appy.technictasks.Models.Customer;
import com.example.appy.technictasks.Models.Task;
import com.example.appy.technictasks.Models.User;

import java.util.ArrayList;

/**
 * Created by Emmanuelle on 25/03/2016.
 */
public class DB {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "tasks.db";
    private Context context;

    private SQLiteDatabase bdd;

    private SQLite maBaseSQLite;

    public DB(Context context){
        maBaseSQLite = new SQLite(context, NOM_BDD, null, VERSION_BDD);
        this.context = context;
    }

    public void open(){
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public User getUserByLogin(String login, String mdp){
        Cursor c = bdd.query("USER", new String[] {"ID_USER", "LOGIN", "MDP", "EMAIL"}, " LOGIN ='"+login+"' AND MDP = '"+mdp+"'", null, null, null, null);
        if(c.getCount() > 0){
            return cursorToUser(c, 0);
        }else{
            return null;
        }
    }

    public User getUserById(Integer id){
        Cursor c = bdd.query("USER", new String[] {"ID_USER", "LOGIN", "MDP", "EMAIL"}, " ID_USER =" + id, null, null, null, null);
        if(c.getCount() > 0){
            return cursorToUser(c, 0);
        }else{
            return null;
        }
    }

    public User getUserByToken(String token){
        Cursor c = bdd.query("USER", new String[] {"ID_USER", "LOGIN", "MDP", "EMAIL"}, " TOKEN =" + token, null, null, null, null);
        if(c.getCount() > 0){
            return cursorToUser(c, 0);
        }else{
            return null;
        }
    }

    public Customer getConstumerById(Integer id){
        Cursor c = bdd.query("CUSTOMER", new String[] {"ID_CUSTOMER", "CONTACT_ID" }, " ID_CUSTOMER =" + id, null, null, null, null);
        if(c.getCount() > 0){
            return cursorToCustomer(c, 0);
        }else{
            return null;
        }
    }

    public Contact getContactById(Integer id){
        Cursor c = bdd.query("CONTACT", new String[] {"ID_CONTACT", "FIRST_NAME", "LAST_NAME", "ADRESS", "POSTCODE", "CITY", "PHONE_NUMBER"}, "ID_CONTACT =" + id, null, null, null, null);
        if(c.getCount() > 0){
            return cursorToContact(c, 0);
        }else{
            return null;
        }
    }

    public Task getTaskById(Integer id){
        Cursor c = bdd.query("TASK", new String[] {"ID_TASK", "USER_ID", "CUSTOMER_ID", "MACHINE_TYPE", "MACHINE_BRAND",
                "PROBLEM", "DATE", "TERMINATED", "TIME_SPEND", "COMMENT" }, "ID_TASK =" + id, null, null, null, null);
        if(c.getCount() > 0){
            return cursorToTask(c, 0);
        }else{
            return null;
        }
    }

    public ArrayList<Task> getUserTasks(Integer user_id){
        ArrayList<Task> tasks = new ArrayList<Task>();
        Task task = null;
        Cursor c = bdd.query("TASK", new String[] {"ID_TASK", "USER_ID", "CUSTOMER_ID", "MACHINE_TYPE", "MACHINE_BRAND",
                "PROBLEM", "DATE", "TERMINATED", "TIME_SPEND", "COMMENT" }, " USER_ID ="+user_id+"", null, null, null, "DATE ASC");
        if(c.getCount() > 0){
            for(int i=0;i<c.getCount();i++){
                task = cursorToTask(c, i);
                tasks.add(task);
            }
        }
        return tasks;
    }

    public int updateTask(int id, Task task){
        ContentValues values = new ContentValues();
        values.put("TIME_SPEND", task.getTime_spend());
        values.put("TERMINATED", task.getTerminated());
        values.put("COMMENT", task.getComment());

        // TODO : Faire si besoin nouveau rdv

        return bdd.update("TASK", values, "ID_TASK = " + id, null);
    }

    public int updateUser(int id, User user){
        ContentValues values = new ContentValues();
        values.put("TOKEN", user.getToken());
        return bdd.update("USER", values, "ID_USER = " + id, null);
    }

    private User cursorToUser(Cursor c,int position){
        if (c.getCount() == 0)
            return null;

        c.moveToPosition(position);
        User user = new User();
        user.setId(c.getInt(0));
        user.setLogin(c.getString(1));
        user.setPassword(c.getString(2));
        user.setEmail(c.getString(3));

        if(position==(c.getCount()-1)){
            c.close();
        }
        return user;
    }

    private Customer cursorToCustomer(Cursor c,int position){
        if (c.getCount() == 0)
            return null;

        c.moveToPosition(position);
        Customer customer = new Customer();
        customer.setId(c.getInt(0));
        customer.setContact_id(c.getInt(1));

        if(position==(c.getCount()-1)){
            c.close();
        }
        return customer;
    }

    private Task cursorToTask(Cursor c,int position){
        if (c.getCount() == 0)
            return null;

        c.moveToPosition(position);
        Task task = new Task();
        task.setId(c.getInt(0));
        task.setUser(this.getUserById(c.getInt(1)));
        task.setCustomer(this.getConstumerById(c.getInt(2)));
        task.setMachine_type(c.getString(3));
        task.setMachine_brand(c.getString(4));
        task.setProblem(c.getString(5));
        task.setDate(c.getString(6));
        task.setTerminated(c.getString(7));
        task.setTime_spend(c.getString(8));

        if(position==(c.getCount()-1)){
            c.close();
        }
        return task;
    }

    private Contact cursorToContact(Cursor c,int position){
        if (c.getCount() == 0)
            return null;

        c.moveToPosition(position);
        Contact contact = new Contact();
        contact.setId(c.getInt(0));
        contact.setFirst_name(c.getString(1));
        contact.setLast_name(c.getString(2));
        contact.setAddress(c.getString(3));
        contact.setPostcode(c.getString(4));
        contact.setCity(c.getString(5));
        contact.setPhone_number(c.getString(6));

        if(position==(c.getCount()-1)){
            c.close();
        }
        return contact;
    }

    /*
    public long insertUser (User user){
        ContentValues values = new ContentValues();
        values.put("LOGIN", user.getLogin());
        values.put("EMAIL", user.getEmail());
        values.put("MDP", user.getPassword());
        return bdd.insert("USER", null, values);
    }
    */

    /***** UPDATES
    public int updatePlat(int id, Plat plat){
        ContentValues values = new ContentValues();
        values.put("NOM_PLAT", plat.getNom_plat());
        return bdd.update("PLAT", values, "ID_PLAT = " + id, null);
    }
    *****/

    /***** REMOVE BY ID
    public int removePlatWithID(int id){
        return bdd.delete("PLAT", "ID_PLAT = " +id, null);
    }
    *****/
}