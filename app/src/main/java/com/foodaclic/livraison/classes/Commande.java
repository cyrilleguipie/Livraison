package com.foodaclic.livraison.classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foodaclic.livraison.MainApplication;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Commande extends RealmObject {

  @PrimaryKey
  public int id;

  public String title;
  public String address;
  public String phone;
  public String code;
  public String statut;
  public String statut2;
  public String statut3;
  public String statut4;
  public String restaurant;
  public String restaurantAddress;
  public String date;
  public String meal;
  public String mealDetails;
  public String price;
  public double restauLng;
  public double restauLat;
  public double destLng;
  public double destLat;

  public static void deleteAll() {
    Realm realm = MainApplication.getRealm();
    RealmResults<Commande> results = realm.where(Commande.class).findAll();
    realm.beginTransaction();
    if(results != null && !results.isEmpty()){
      results.deleteAllFromRealm();}
    realm.commitTransaction();
    realm.close();

  }

  public static ArrayList<Commande> findAllOrderedByTitle() {
    ArrayList<Commande>objects = new ArrayList<>();
    RealmResults<Commande> results = MainApplication.getRealm().where(Commande.class)
        .findAll()
        .sort("id", Sort.DESCENDING);
    if(results != null && results.size() > 0) {
      objects.addAll(results.subList(0, results.size()));
      Collections.copy(objects, results.subList(0, results.size()));
    }
    return objects;
  }

  public static Commande findLast() {

    Commande object = null;
    RealmResults<Commande>
        query = MainApplication.getRealm().where(Commande.class).findAll().sort("created", Sort.DESCENDING);
    if(query != null && query.size() > 0){
      object =  query.first();
    }
    return object;
  }

  public static Commande findById(int id) {
    Commande object = null;
    RealmQuery<Commande>
        query = MainApplication.getRealm().where(Commande.class).equalTo("id", id);
    if(query != null){
      object =  query.findFirst();
    }
    return object;

  }

  public static void save(Commande document) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    realm.copyToRealmOrUpdate(document);
    realm.commitTransaction();
    realm.close();
  }

  public static void saveAll(List<Commande> objects) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    realm.copyToRealmOrUpdate(objects);
    realm.commitTransaction();
    realm.close();
  }

}
