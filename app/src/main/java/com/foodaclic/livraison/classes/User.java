package com.foodaclic.livraison.classes;

import com.foodaclic.livraison.MainApplication;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import java.util.Date;

/**
 * Created by billyaymeric on 19/01/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends RealmObject {



    @PrimaryKey
    public int id;

    public String userliv;
    public String username;
    public String livcouverture;
    public String email;
    public String tel;

    public static void deleteAll() {
        Realm realm = MainApplication.getRealm();
        RealmResults<User> results = realm.where(User.class).findAll();
        realm.beginTransaction();
        if(results != null && !results.isEmpty()){
            results.deleteAllFromRealm();
        }
        realm.commitTransaction();
        realm.close();

    }
    public static User findById(int id) {
        User object = null;
        RealmQuery<User>
            query = MainApplication.getRealm().where(User.class).equalTo("id", id);
        if(query != null){
            object =  query.findFirst();
        }
        return object;
    }

    public static void save(User document) {
        Realm realm = MainApplication.getRealm();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(document);
        realm.commitTransaction();
        realm.close();
    }


}
