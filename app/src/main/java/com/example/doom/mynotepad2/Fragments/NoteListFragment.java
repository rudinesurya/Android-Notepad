package com.example.doom.mynotepad2.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doom.mynotepad2.DetailActivity;
import com.example.doom.mynotepad2.Model.Note;
import com.example.doom.mynotepad2.MyRecyclerViewAdapter;
import com.example.doom.mynotepad2.Parent;
import com.example.doom.mynotepad2.R;

import io.realm.Realm;
import io.realm.RealmList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteListFragment extends Fragment
{
    private Realm realm;
    private MyRecyclerViewAdapter mAdapter;
    private CoordinatorLayout mCoordinatorLayout;
    
    public NoteListFragment()
    {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View viewGroup = inflater.inflate(R.layout.fragment_note_list, container, false);
        
        realm = Realm.getDefaultInstance();
        RecyclerView mRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.recyclerView);
        mRecyclerView.hasFixedSize();
        
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        
        RealmList<Note> myDataset = realm.where(Parent.class).findFirst().getNoteList();
        // specify an adapter (see also next example)
        mAdapter = new MyRecyclerViewAdapter(getActivity(), realm, myDataset);
        mAdapter.setOnTitleClickListener(new MyRecyclerViewAdapter.OnTitleClickListener()
        {
            @Override
            public void onTitleClick(long id)
            {
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra(DetailFragment.ID, id);
                
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.nothing, R.anim.nothing);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback();
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
        
        // Inflate the layout for this fragment
        return viewGroup;
    }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        realm.close();
    }
    
    public void onFABClicked()
    {
        Intent i = new Intent(getActivity(), DetailActivity.class);
        long id = 0;
        i.putExtra(DetailFragment.ID, id);
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.nothing, R.anim.nothing);
    }
    
    public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback
    {
        @Override
        public boolean isLongPressDragEnabled()
        {
            return true;
        }
        
        @Override
        public boolean isItemViewSwipeEnabled()
        {
            return true;
        }
        
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
        {
            int dragFlags = 0;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
        
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
        {
            return false;
        }
        
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
        {
            int i = viewHolder.getAdapterPosition();
            final Note recentlyDeletedNote = new Note();
            recentlyDeletedNote.Clone(mAdapter.getItem(i));
            final int recentlyDeletedPosition = i;
            Note.deleteAsync(realm, mAdapter.getItemId(i));
            
            mCoordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.root);
            Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "Note is deleted", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Note.addAsync(realm, recentlyDeletedNote, recentlyDeletedPosition);
                    
                    Snackbar snackbar1 = Snackbar.make(mCoordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                }
            });
            
            snackbar.show();
        }
    }
}
