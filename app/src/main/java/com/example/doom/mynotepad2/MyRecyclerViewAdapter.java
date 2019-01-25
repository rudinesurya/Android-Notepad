package com.example.doom.mynotepad2;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doom.mynotepad2.Model.Note;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by Doom on 9/16/2017.
 */

public class MyRecyclerViewAdapter extends RealmRecyclerViewAdapter<Note, MyRecyclerViewAdapter.MyViewHolder>
{
    private OnTitleClickListener onTitleClickListener;
    private Context mContext;
    private Realm realm;
    
    public MyRecyclerViewAdapter(Context context, Realm realm, OrderedRealmCollection<Note> data)
    {
        super(data, true);
        mContext = context;
        this.realm = realm;
        setHasStableIds(true);
    }
    
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_note, parent, false);
        return new MyViewHolder(itemView);
    }
    
    public static interface OnTitleClickListener
    {
        void onTitleClick(long id);
    }
    
    public void setOnTitleClickListener(OnTitleClickListener onTitleClickListener)
    {
        this.onTitleClickListener = onTitleClickListener;
    }
    
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        final Note obj = getItem(position);
        final long id = obj.getId();
        
        holder.title.setText(obj.getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onTitleClickListener != null)
                {
                    onTitleClickListener.onTitleClick(id);
                }
            }
        });
    }
    
    @Override
    public long getItemId(int index)
    {
        return getItem(index).getId();
    }
    
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView title;
        
        MyViewHolder(View view)
        {
            super(view);
            cardView = (CardView) view;
            title = (TextView) view.findViewById(R.id.textTitle);
        }
    }
}
