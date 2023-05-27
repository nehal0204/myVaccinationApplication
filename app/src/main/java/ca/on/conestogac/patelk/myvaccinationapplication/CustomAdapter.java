package ca.on.conestogac.patelk.myvaccinationapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
//    Activity activity;
    private ArrayList user_id, user_name, user_email, user_phone, vaccine_type, vaccine_date;


    int position;

    CustomAdapter(Context context,
                  ArrayList user_id,
                  ArrayList user_name,
                  ArrayList user_email,
                  ArrayList user_phone,
                  ArrayList vaccine_type,
                  ArrayList vaccine_date){
//        this.activity = activity;
        this.context = context;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.vaccine_type = vaccine_type;
        this.vaccine_date = vaccine_date;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_data, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            holder.user_id_tv.setText(String.valueOf(user_id.get(position)));
            holder.user_name_tv.setText(String.valueOf(user_name.get(position)));
            holder.user_email_tv.setText(String.valueOf(user_email.get(position)));
            holder.user_phone_tv.setText(String.valueOf(user_phone.get(position)));
            holder.vaccine_type_tv.setText(String.valueOf(vaccine_type.get(position)));
            holder.vaccine_date_tv.setText(String.valueOf(vaccine_date.get(position)));
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("id1", String.valueOf(user_id.get(position)));
                    intent.putExtra("name1", String.valueOf(user_name.get(position)));
                    intent.putExtra("email1", String.valueOf(user_email.get(position)));
                    intent.putExtra("phone1", String.valueOf(user_phone.get(position)));
                    intent.putExtra("type1", String.valueOf(vaccine_type.get(position)));
                    intent.putExtra("date1", String.valueOf(vaccine_date.get(position)));
                    context.startActivity(intent);
                }
            });

    }

    @Override
    public int getItemCount() {
        return user_id.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {

        TextView user_id_tv, user_name_tv, user_email_tv, user_phone_tv, vaccine_type_tv, vaccine_date_tv;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            user_id_tv = itemView.findViewById(R.id.user_id_tv);
            user_name_tv = itemView.findViewById(R.id.user_name_tv);
            user_email_tv = itemView.findViewById(R.id.user_email_tv);
            user_phone_tv = itemView.findViewById(R.id.user_phone_tv);
            vaccine_type_tv = itemView.findViewById(R.id.vaccine_type_tv);
            vaccine_date_tv = itemView.findViewById(R.id.vaccine_date_tv);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation application_anim = AnimationUtils.loadAnimation(context, R.anim.application_anim);
            mainLayout.setAnimation(application_anim);
        }
    }
}
