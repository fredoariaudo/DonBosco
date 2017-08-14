package com.donbosco.android.porlosjovenes.data;

import android.content.Context;

import com.donbosco.android.porlosjovenes.model.User;

public class UserSerializer extends ObjectSerializer
{
    //User file name
    private final static String FILE_NAME = "User.dat";

    //Singleton instance
    private static UserSerializer userSerializer;

    private UserSerializer()
    {
        //Prevent initialization from the reflection api.
        if(userSerializer != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
    }

    public static synchronized UserSerializer getInstance()
    {
        if(userSerializer == null)
            userSerializer = new UserSerializer();

        return userSerializer;
    }

    public void save(Context context, User user)
    {
        save(context, FILE_NAME, user);
    }

    public User load(Context context)
    {
        return (User) load(context, FILE_NAME);
    }

    public void clear(Context context)
    {
        save(context, null);
    }
}
