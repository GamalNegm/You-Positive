package com.example.prog.bloodshare.utils;

import android.app.Activity;
import android.content.Intent;

import com.example.prog.bloodshare.Activities.LoginActivity;
import com.example.prog.bloodshare.models.User;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by hendiware on 2016/12 .
 */

public class Session {
    private static Session instance;
    private Realm realm;

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }
    public static Session newInstance() {
        return new Session();
    }

    private Session() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfig);
    }

    public void loginUser(final User user) {

        if (realm.where(User.class).findFirst() == null) {

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(user);
                }
            });

        } else {
            logout();
            loginUser(user);
        }


    }

    public void logout() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(User.class);
            }
        });
    }

    public boolean isUserLoggedIn() {
        return realm.where(User.class).findFirst() != null;
    }

    public User getUser() {
        return realm.where(User.class).findFirst();
    }

    public void logoutAndGoToLogin(Activity activity) {
        logout();
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }


}
