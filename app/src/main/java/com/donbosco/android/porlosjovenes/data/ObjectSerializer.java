package com.donbosco.android.porlosjovenes.data;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public class ObjectSerializer
{
    protected void save(Context context, String fileName, Object object)
    {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try
        {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(fos != null) fos.close();
                if(oos != null) oos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    protected Object load(Context context, String fileName)
    {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object object = null;

        try
        {
            fis = context.openFileInput(fileName);
            ois = new ObjectInputStream(fis);
            object = ois.readObject();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (StreamCorruptedException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(fis != null) fis.close();
                if(ois != null) ois.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return object;
    }
}
