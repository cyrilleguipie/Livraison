package com.foodaclic.livraison.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodaclic.livraison.R;
import com.foodaclic.livraison.classes.Notif;
import java.util.ArrayList;

/**
 * Created by cyrilleguipie on 10/16/17.
 */

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.ViewHolder> {

  private ArrayList<Notif> mValues;
  private Context mContext;


  public NotifAdapter(Context context, ArrayList<Notif> items) {
    mContext = context;
    mValues = items;

  }


  public static class ViewHolder extends RecyclerView.ViewHolder {

    public final TextView title, content;
    public final View mView;

    public ViewHolder(View itemView) {
      super(itemView);

      title =  itemView.findViewById(R.id.title);
      content =  itemView.findViewById(R.id.content);

      mView = itemView;
    }
  }

  @Override public NotifAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.notif_item, parent, false);

    return new NotifAdapter.ViewHolder(view);
  }

  @Override public void onBindViewHolder(final NotifAdapter.ViewHolder holder, int position) {

    //get object at position
    final Notif object = mValues.get(position);

    holder.title.setText(object.title);
    holder.content.setText(object.content);

  }

  @Override public int getItemCount() {
    return mValues.size();
  }
}
