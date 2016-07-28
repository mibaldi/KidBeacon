package com.mibaldi.kidbeacon.Features.Groups.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mibaldi.kidbeacon.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*final OwnGroupRealm ownGroup = new OwnGroupRealm();
        ownGroup.setName("Grupo 4");
        ownGroup.setCreation_date(new Date());
        final OwnBeaconRealm ownBeacon = new OwnBeaconRealm();
        ownBeacon.setUuid("B9407F30-F5F8-466E-AFF9-25556B57FE6F");
        ownBeacon.setMajor("0");
        ownBeacon.setMinor("1");
       // List<OwnBeaconRealm> list = new ArrayList<>(Arrays.asList(ownBeacon));
        //ownGroup.setBeaconsList(list);

        DataBaseManager.storeOwnGroup(ownGroup, new DataBaseManager.OnDatabaseTransaction() {
            @Override
            public void onSucces() {
                Timber.d("Grupo guardado");
                DataBaseManager.asignOwnBeacon2OwnGroup(ownGroup.getName(), ownBeacon, new DataBaseManager.OnDatabaseTransaction() {
                    @Override
                    public void onSucces() {
                        Timber.d("Beacon guardado en el grupo "+ownGroup.getName());
                    }

                    @Override
                    public void onError(Throwable error) {
                        Timber.e(error,"error al guardar el beacon en el grupo");
                    }
                });

            }

            @Override
            public void onError(Throwable error) {
                Timber.e(error,"error");
            }
        });*/
    }
}
