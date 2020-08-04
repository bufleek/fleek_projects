package com.bulksms;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bulksms.utils.ContactsRecyclerAdapter;
import com.bulksms.utils.DataManager;
import com.bulksms.utils.DbOpenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class SendListFragment extends Fragment {
    private static final String TAG = "SendSMSFragment";
    private RecyclerView rvRecipients;
    private Context mContext;
    private ContactsRecyclerAdapter recyclerAdapter;
    private DbOpenHelper mDbOpenHelper;
    private DataManager mDm;
    private FloatingActionButton mFab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFab = getActivity().findViewById(R.id.fab);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Started.");
        View view = inflater.inflate(R.layout.fragment_send_list, container, false);
        mContext = view.getContext();

        rvRecipients = (RecyclerView) view.findViewById(R.id.recyclerView_recipients);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvRecipients.setLayoutManager(layoutManager);
        mDbOpenHelper = new DbOpenHelper(mContext);
        mDm = new DataManager(mDbOpenHelper);
        recyclerAdapter = new ContactsRecyclerAdapter(mContext, null);
        rvRecipients.setAdapter(recyclerAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Cursor cursor = mDm.getContacts();
        recyclerAdapter.changeCursor(cursor);
        mFab.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recyclerAdapter.changeCursor(null);
    }
}