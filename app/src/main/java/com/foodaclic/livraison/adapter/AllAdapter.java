package com.foodaclic.livraison.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.foodaclic.livraison.R;
import com.foodaclic.livraison.classes.Commande;
import com.foodaclic.livraison.service.PayService;
import com.foodaclic.livraison.service.TakeService;
import com.foodaclic.livraison.utils.CustomDialog;
import java.util.List;

/**
 * Created by cyrilleguipie on 1/28/17.
 */

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.ViewHolder> {

private List<Commande> mValues;
private Context mContext;
  private CustomDialog dialog;

public AllAdapter(Context context, List<Commande> items) {
    mContext = context;
    mValues = items;

    }

public static class ViewHolder extends RecyclerView.ViewHolder {


  public final TextView label_client, label_start, label_phone, text_code, text_status, text_restau,label_end, label_date, label_plat, text_price;

  public final View mView;
  public final Button btn_take;

  public ViewHolder(View itemView) {
    super(itemView);

    label_client = itemView.findViewById(R.id.label_client);
    label_start = itemView.findViewById(R.id.label_start);
    label_phone = itemView.findViewById(R.id.label_phone);
    text_code = itemView.findViewById(R.id.text_code);
    text_status = itemView.findViewById(R.id.text_status);
    text_restau = itemView.findViewById(R.id.text_restau);
    label_end = itemView.findViewById(R.id.label_end);
    label_date = itemView.findViewById(R.id.label_date);
    label_plat = itemView.findViewById(R.id.label_plat);
    text_price = itemView.findViewById(R.id.text_price);
    btn_take = itemView.findViewById(R.id.btn_take);

    label_start.setPaintFlags(label_start.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    label_end.setPaintFlags(label_end.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);




    mView = itemView;
  }
}

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.all_item, parent, false);

    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(final ViewHolder holder, int position) {

    //get object at position
    final Commande object = mValues.get(position);
    holder.label_client.setText(object.title);
    holder.label_start.setText(object.address);
    holder.label_phone.setText(object.phone);
    holder.text_code.setText(object.code);
    holder.text_status.setText(object.statut);
    holder.text_restau.setText(object.restaurant);
    holder.label_end.setText(object.restaurantAddress);
    holder.label_date.setText(object.date);
    holder.label_plat.setText(object.meal);
    holder.text_price.setText(object.price);


    holder.label_start.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Uri gmmIntentUri = Uri.parse("geo:"+object.destLat+","+object.destLng+"?q=" + Uri.encode(object.address));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(mContext.getPackageManager()) != null) {
          mContext.startActivity(mapIntent);
        }
      }
    });

    holder.label_end.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Uri gmmIntentUri = Uri.parse("geo:"+object.restauLat+","+object.restauLng+"?q=" + Uri.encode(object.restaurantAddress));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(mContext.getPackageManager()) != null) {
          mContext.startActivity(mapIntent);
        }
      }
    });


    if(object.statut4.equals("Commande prête")){
      holder.text_status.setText("(Commande prête)");
      holder.text_status.setTextColor(mContext.getResources().getColor(R.color.md_green_A700));
    }else{
      holder.text_status.setText("(Pas encore prête)");
      holder.text_status.setTextColor(mContext.getResources().getColor(R.color.md_red_A700));
    }


    if(object.statut.equals("Commande livrée")){
            holder.btn_take.setText("Commande livrée");
            holder.btn_take.setEnabled(false);

        }else if(object.statut2.equals("Paiement non confirmé")){
            holder.btn_take.setText("Valider paiement / livraison");
            holder.btn_take.setEnabled(true);

        }else{
          holder.btn_take.setText("Prendre en charge");
          holder.btn_take.setEnabled(true);

        }
        //onClick to select object
    holder.btn_take.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if(object.statut2.equals("Paiement non confirmé")){
          takePay((int) object.id);
        }else{
          takeDialog(object);
        }


      }
    });

  }

  @Override public int getItemCount() {
    return mValues.size();
  }


  public void takeDialog(final Commande commande){

    final View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_take, null);
    dialog = new CustomDialog(mContext, view);

    ImageView imgClose = (ImageView) dialog.findViewById(R.id.img_close);
    final AppCompatSpinner spinReception = dialog.findViewById(R.id.spin_reception);
    Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);


    btn_ok.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        mContext.startService(new Intent(mContext, TakeService.class)
            .putExtra("id", commande.id)
            .putExtra("code", commande.code)
            .putExtra("pourboire", commande.price)
        .putExtra("reception", spinReception.getSelectedItem().toString()));

        dialog.dismiss();
       dialog.dismiss();
      }
    });

    imgClose.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        dialog.dismiss();
      }
    });

    dialog.setCanceledOnTouchOutside(true);
    dialog.show();

  }

  public void takePay(final int id){

    final View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_pay, null);
    dialog = new CustomDialog(mContext, view);

    ImageView imgClose =  dialog.findViewById(R.id.img_close);

    Button btn_ok =  dialog.findViewById(R.id.btn_ok);


    btn_ok.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        mContext.startService(new Intent(mContext, PayService.class)
        .putExtra("id", id));
        dialog.dismiss();
      }
    });

    imgClose.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        dialog.dismiss();
      }
    });

    dialog.setCanceledOnTouchOutside(true);
    dialog.show();

  }
}
