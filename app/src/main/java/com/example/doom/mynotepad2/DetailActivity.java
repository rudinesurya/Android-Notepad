package com.example.doom.mynotepad2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.doom.mynotepad2.Fragments.DetailFragment;

public class DetailActivity extends AppCompatActivity
{
    DetailFragment detailFragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    
        long id = getIntent().getExtras().getLong(DetailFragment.ID);
        detailFragment = (DetailFragment)getFragmentManager().findFragmentById(R.id.detail_frag);
        detailFragment.setNoteId(id);
    }
    
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }
    
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        detailFragment.onBackPressedFragment();
    }
    
    @Override
    public boolean onSupportNavigateUp()
    {
        boolean result = super.onSupportNavigateUp();
        detailFragment.onBackPressedFragment();
        return result;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.delete :
                detailFragment.Delete();
                finish();
                return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
