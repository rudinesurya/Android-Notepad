package com.example.doom.mynotepad2.Model;


import com.example.doom.mynotepad2.Parent;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Doom on 9/15/2017.
 */

public class Note extends RealmObject
{
    @PrimaryKey
    private long id;
    String title;
    String detail;
    
    @Override
    public String toString()
    {
        return "Note{" + "title='" + title + '\'' + ", detail='" + detail + '\'' + '}';
    }
    
    public long getId()
    {
        return id;
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getDetail()
    {
        return detail;
    }
    
    public void setDetail(String detail)
    {
        this.detail = detail;
    }
    
    public void Clone(Note note)
    {
        setId(note.getId());
        setTitle(note.getTitle());
        setDetail(note.getDetail());
    }
    
    
    public static void addAsync(Realm realm, final Note toClone)
    {
        addAsync(realm, toClone, -1);
    }
    
    public static void addAsync(Realm realm, final Note toClone, final int position)
    {
        realm.executeTransactionAsync(new Realm.Transaction()
        {
            @Override
            public void execute(Realm bgRealm)
            {
                RealmList<Note> myDataset = bgRealm.where(Parent.class).findFirst().getNoteList();
                Note note = bgRealm.createObject(Note.class, toClone.getId());
                note.setTitle(toClone.getTitle());
                note.setDetail(toClone.getDetail());
                
                if (position == -1)
                    myDataset.add(note);
                else
                    myDataset.add(position, note);
            }
        });
    }
    
    public static void updateAsync(Realm realm, final Note toClone)
    {
        realm.executeTransactionAsync(new Realm.Transaction()
        {
            @Override
            public void execute(Realm bgRealm)
            {
                Note note = bgRealm.where(Note.class).equalTo("id", toClone.getId()).findFirst();
                note.setTitle(toClone.getTitle());
                note.setDetail(toClone.getDetail());
            }
        });
    }
    
    public static void deleteAsync(Realm realm, final long toDelete)
    {
        realm.executeTransactionAsync(new Realm.Transaction()
        {
            @Override
            public void execute(Realm bgRealm)
            {
                Note note = bgRealm.where(Note.class).equalTo("id", toDelete).findFirst();
                note.deleteFromRealm();
            }
        });
    }
    
    public static long generateId()
    {
        return new Random().nextLong();
    }
}
