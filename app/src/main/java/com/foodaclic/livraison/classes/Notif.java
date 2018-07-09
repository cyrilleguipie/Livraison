package com.foodaclic.livraison.classes;

import com.foodaclic.livraison.MainApplication;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by cyrilleguipie on 10/16/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notif extends RealmObject {


  @PrimaryKey
  public int id;


  public String title;

  public String content;

  public static void deleteAll() {
    Realm realm = MainApplication.getRealm();
    RealmResults<Notif> results = realm.where(Notif.class).findAll();
    realm.beginTransaction();
    if(results != null && !results.isEmpty()){
      results.deleteAllFromRealm();}
    realm.commitTransaction();
    realm.close();

  }

  public static ArrayList<Notif> findAllOrderedByTitle() {
    ArrayList<Notif>objects = new ArrayList<>();
    RealmResults<Notif> results = MainApplication.getRealm().where(Notif.class)
        .findAll()
        .sort("id", Sort.DESCENDING);
    if(results != null && results.size() > 0) {
      objects.addAll(results.subList(0, results.size()));
      Collections.copy(objects, results.subList(0, results.size()));
    }
    return objects;
  }

  public static Notif findLast() {

    Notif object = null;
    RealmResults<Notif>
        query = MainApplication.getRealm().where(Notif.class).findAll().sort("created", Sort.DESCENDING);
    if(query != null && query.size() > 0){
      object =  query.first();
    }
    return object;
  }

  public static Notif findById(int id) {
    Notif object = null;
    RealmQuery<Notif>
        query = MainApplication.getRealm().where(Notif.class).equalTo("id", id);
    if(query != null){
      object =  query.findFirst();
    }
    return object;

  }

  public static void save(Notif document) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    realm.copyToRealmOrUpdate(document);
    realm.commitTransaction();
    realm.close();
  }

  public static void saveAll(ArrayList<Notif> objects) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    realm.copyToRealmOrUpdate(objects);
    realm.commitTransaction();
    realm.close();
  }
}
