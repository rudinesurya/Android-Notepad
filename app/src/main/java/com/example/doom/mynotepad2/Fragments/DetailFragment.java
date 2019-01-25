package com.example.doom.mynotepad2.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.doom.mynotepad2.Interfaces.OnBackPressedFragment;
import com.example.doom.mynotepad2.Model.Note;
import com.example.doom.mynotepad2.R;
import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements OnBackPressedFragment
{
    public static final String ID = "id";
    private long itemId;
    private Realm realm;
    
    String titleText;
    String detailText;
    TextView titleTV;
    TextView detailTV;
    
    public DetailFragment()
    {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View viewGroup = inflater.inflate(R.layout.fragment_detail, container, false);
        
        return viewGroup;
    }
    
    @Override
    public void onStart()
    {
        super.onStart();
        View view = getView();
        titleTV = (TextView) view.findViewById(R.id.textTitle);
        detailTV = (TextView) view.findViewById(R.id.textDetail);
        realm = Realm.getDefaultInstance();
    
        if (itemId != 0)
        {
            Note note = realm.where(Note.class).equalTo("id", itemId).findFirst();
            if (note != null)
            {
                titleText = note.getTitle();
                detailText = note.getDetail();
                titleTV.setText(titleText);
                detailTV.setText(detailText);
            }
        }
        else
        {
            titleText = "";
            detailText = "";
        }
    }
    
    public void setNoteId(long id)
    {
        this.itemId = id;
    }
    
    public void Save()
    {
        final String title_ = titleTV.getText().toString().trim();
        final String detail_ = detailTV.getText().toString().trim();
        
        if (itemId != 0)
        {
            Note note = new Note();
            note.setId(itemId);
            note.setTitle(title_);
            note.setDetail(detail_);
            Note.updateAsync(realm, note);
        }
        else
        {
            Note note = new Note();
            note.setId(Note.generateId());
            note.setTitle(title_);
            note.setDetail(detail_);
            Note.addAsync(realm, note);
        }
    }
    
    public void Delete()
    {
        if (itemId != 0)
            Note.deleteAsync(realm, itemId);
    }
    
    @Override
    public void onBackPressedFragment()
    {
        if (checkIfSaveIsNeeded() == true)
        {
            Save();
        }
        
        getActivity().overridePendingTransition(R.anim.nothing, R.anim.nothing);
    }
    
    private boolean checkIfSaveIsNeeded()
    {
        return !titleText.equals(titleTV.getText().toString()) || !detailText.equals(detailTV.getText().toString());
    }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        realm.close();
    }
}
