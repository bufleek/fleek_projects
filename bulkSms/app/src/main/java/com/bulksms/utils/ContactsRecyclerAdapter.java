package com.bulksms.utils;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bulksms.R;
import com.bulksms.utils.BulkSmsDbContract.ContactEntry;

public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsRecyclerAdapter.ViewHolder> {
    private static final String TAG = "ContactsRecyclerAdapter";
    private Context mContext;
    private Cursor mCursor;
    private LayoutInflater mLayoutInflater;
    private int idPos;
    private int namePos;
    private int phonePos;

    public ContactsRecyclerAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
        mLayoutInflater = LayoutInflater.from(context);
        populateColumnPositions();
    }

    private void populateColumnPositions() {
        if(mCursor != null){
            idPos = mCursor.getColumnIndex(ContactEntry._ID);
            namePos = mCursor.getColumnIndex(ContactEntry.COLUMN_CONTACT_NAME);
            phonePos = mCursor.getColumnIndex(ContactEntry.COLUMN_CONTACT_PHONE);
        }
    }

    public void changeCursor(Cursor newCursor){
        if(mCursor != null)
            mCursor.close();
        mCursor = newCursor;
        populateColumnPositions();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.recipient_list_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(mCursor != null){
            mCursor.moveToPosition(position);
            String name = mCursor.getString(namePos);
            String phone = mCursor.getString(phonePos);
            holder.tvName.setText(name.isEmpty()?"(No name)": name);
            holder.tvPhone.setText(phone);
            holder.id = mCursor.getInt(idPos);
        }
    }

    @Override
    public int getItemCount() {
        return mCursor == null? 0: mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvPhone;
        private int id;
        private CardView cardViewContact;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            cardViewContact = (CardView) itemView.findViewById(R.id.cardView_contact);
            cardViewContact.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                   // Toast.makeText(mContext,"Long Clicked " + id, Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }

}
