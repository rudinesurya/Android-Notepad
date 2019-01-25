package com.example.doom.mynotepad2;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Doom on 9/15/2017.
 */

public class BaseApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").initialData(new Realm.Transaction()
        {
            @Override
            public void execute(Realm realm)
            {
                realm.createObject(Parent.class);
            }
        }).build();
        Realm.setDefaultConfiguration(config);
    }
}
