package com.example.doom.mynotepad2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.doom.mynotepad2.Fragments.NoteListFragment;
import com.example.doom.mynotepad2.Settings.AppPreferences;

public class MainActivity extends AppCompatActivity
{
    NoteListFragment notelistFragment;
    private CoordinatorLayout mCoordinatorLayout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        notelistFragment = (NoteListFragment)getFragmentManager().findFragmentById(R.id.notelist_frag);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.root);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                notelistFragment.onFABClicked();
            }
        });
    }
    
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //respond to menu item selection
        switch (item.getItemId())
        {
            case R.id.about:
                return true;
            case R.id.settings:
                startActivity(new Intent(this, AppPreferences.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    
    //Helper func to check fragment class
    private Fragment checkFragment(String tag)
    {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment result = null;
        if (fragmentManager != null)
        {
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            if (fragment != null && fragment.isVisible())
            {
                result = fragment;
            }
        }
        return result;
    }
}
