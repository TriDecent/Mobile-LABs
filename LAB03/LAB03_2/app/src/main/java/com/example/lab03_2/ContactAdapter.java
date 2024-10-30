package com.example.lab03_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
    private final Context context;
    private final ContactsViewModel viewModel;

    public ContactAdapter(Context context, ContactsViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ContactAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var inflater = LayoutInflater.from(context);
        var view = inflater.inflate(R.layout.items, parent, false);

        return new ContactAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.MyViewHolder holder, int position) {
        var contacts = viewModel.getContacts().getValue();

        if (contacts == null) return;

        var currentContact = contacts.get(position);

        holder.tvId.setText(String.valueOf(currentContact.Id()));
        holder.tvName.setText(currentContact.Name());
        holder.tvPhone.setText(currentContact.PhoneNumber());

        holder.cvContact.setOnLongClickListener(v -> {
            viewModel.deleteContact(currentContact);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return viewModel.getAllContacts().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvPhone;
        CardView cvContact;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone_number);
            cvContact = itemView.findViewById(R.id.cv_contact);
        }
    }
}
