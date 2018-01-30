package fr.isen.galiay.androidtoolbox.webService;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fr.isen.galiay.androidtoolbox.R;
import fr.isen.galiay.androidtoolbox.WebServiceActivity;
import fr.isen.galiay.androidtoolbox.picasso.CircleTransform;

import static fr.isen.galiay.androidtoolbox.WebServiceActivity.listUsers;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private int nb = 25;
    private List<Pair<String, results>> characters = new ArrayList<>();

    @Override
    public int getItemCount() {
        return nb;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_user, parent, false);

        for (int i = 0; i < nb; i++)
            characters.add(Pair.create(listUsers.get(i).getName().getFirst() + " " + listUsers.get(i).getName().getLast(), listUsers.get(i)));

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Pair<String, results> pair = characters.get(position);
        holder.display(pair);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView photo;
        private TextView nom;
        private TextView adresse;
        private TextView email;

        public MyViewHolder(final View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            nom = itemView.findViewById(R.id.nom);
            adresse = itemView.findViewById(R.id.adresse);
            email = itemView.findViewById(R.id.email);
        }

        public void display(Pair<String, results> pair) {
            nom.setText(pair.first);
            Picasso.with(WebServiceActivity.context)
                    .load(pair.second.getPicture().getLarge())
                    .transform(new CircleTransform())
                    .into(photo);
            String adresseBuilder = pair.second.getLocation().getStreet() + " " + pair.second.getLocation().getCity();
            adresse.setText(adresseBuilder);
            email.setText(pair.second.getEmail());
        }
    }
}
