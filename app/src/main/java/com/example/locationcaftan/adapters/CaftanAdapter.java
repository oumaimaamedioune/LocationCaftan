package com.example.locationcaftan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.locationcaftan.R;
import com.example.locationcaftan.model.Caftan;
import com.example.locationcaftan.DetailsActivity;

import java.util.List;
import android.graphics.Color;

public class CaftanAdapter extends RecyclerView.Adapter<CaftanAdapter.CaftanViewHolder> {

    private List<Caftan> caftanList;
    private android.content.Context context;

    // Constructor 1
    public CaftanAdapter(List<Caftan> caftanList) {
        this.caftanList = caftanList;
    }

    // Constructor 2 (avec context)
    public CaftanAdapter(android.content.Context context, List<Caftan> caftanList) {
        this.context = context;
        this.caftanList = caftanList;
    }

    //
    @NonNull
    @Override
    public CaftanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_caftan, parent, false);
        return new CaftanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaftanViewHolder holder, int position) {

        Caftan c = caftanList.get(position);

        holder.nom.setText("Nom : " + c.getNom());
        holder.prix.setText("Prix : " + c.getPrix());

        // Gestion statut
        String statut = c.getStatut();

        if (statut == null || statut.trim().isEmpty()) {
            holder.statut.setText("Statut : Indisponible");
            holder.statut.setTextColor(Color.parseColor("#FF0000"));
        }
        else if (statut.trim().equalsIgnoreCase("disponible")) {
            holder.statut.setText("Statut : Disponible");
            holder.statut.setTextColor(Color.parseColor("#00A000"));
        }
        else {
            holder.statut.setText("Statut : " + statut);
            holder.statut.setTextColor(Color.parseColor("#FF0000"));
        }

        // Charger image depuis Laravel Storage
        String fullUrl = "http://10.0.2.2:8000/storage/" + c.getImageUrl().trim();

        Glide.with(context != null ? context : holder.itemView.getContext())
                .load(fullUrl)
                .placeholder(R.drawable.placeholder)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return caftanList.size();
    }

    class CaftanViewHolder extends RecyclerView.ViewHolder {

        TextView nom, prix, statut;
        ImageView image;

        public CaftanViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView);
            nom = itemView.findViewById(R.id.txtNom);
            prix = itemView.findViewById(R.id.txtPrix);
            statut = itemView.findViewById(R.id.txtStatut);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                Caftan c = caftanList.get(pos);

                android.content.Context ctx = itemView.getContext();
                android.content.Intent i = new android.content.Intent(ctx, DetailsActivity.class);

                i.putExtra("caftan_id", c.getId());
                i.putExtra("nom", c.getNom());
                i.putExtra("prix", c.getPrix());
                i.putExtra("taille", c.getTaille());
                i.putExtra("couleur", c.getCouleur());
                i.putExtra("statut", c.getStatut());
                i.putExtra("image", c.getImageUrl());

                ctx.startActivity(i);
            });
        }
    }
}