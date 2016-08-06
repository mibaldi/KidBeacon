package com.mibaldi.kidbeacon.Features.Beacons.NFC;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mibaldi.kidbeacon.Data.FirebaseManager;
import com.mibaldi.kidbeacon.Data.Models.OwnBeacon;
import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class NfcActivity extends AppCompatActivity {
    @BindView(R.id.beaconName)
    TextView beaconName;
    private OwnBeacon ownBeacon;
    private OwnGroup ownGroup;
    private NfcAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        ownGroup =getIntent().getParcelableExtra("ownGroup");
        ButterKnife.bind(this);
        adapter = NfcAdapter.getDefaultAdapter(this);
        checkNFC();

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNFC();
    }

    public String[] parseString(String nfcResponse) {
        String[] nfcResponses = nfcResponse.split(";");
        String uuid = "";
        String major = "";
        String minor = "";
        String name = "";
        if (nfcResponses.length == 4) {
            uuid = nfcResponses[0].split(":")[1];

            major = nfcResponses[1].split(":")[1];
            minor = nfcResponses[2].split(":")[1];
            name = nfcResponses[3].split(":")[1];
        }
        String[] beaconFields = {uuid,major,minor,name};


        return beaconFields;
    }
    public OwnBeacon generateBeacon(String[] beaconFields){
        OwnBeacon ownBeacon = new OwnBeacon();
        ownBeacon.uuid = beaconFields[0];
        ownBeacon.major = beaconFields[1];
        ownBeacon.minor = beaconFields[2];
        ownBeacon.name = beaconFields[3];
        return ownBeacon;
    }

    @OnClick(R.id.scannfc)
    public void scanNFC(Button button) {
        checkNFC();
        button.setText("escaneado");
    }

    @OnClick(R.id.saveBeacon)
    public void saveBeacon() {
        if (ownBeacon != null){
            FirebaseManager.generateBeacon(ownBeacon,ownGroup.id);
            Toast.makeText(this, "Beacon guardado"+ ownBeacon.name, Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void checkNFC() {
        if (adapter != null) {
            if (!adapter.isEnabled()) {
                if (android.os.Build.VERSION.SDK_INT >= 16) {
                    startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
                } else {
                    startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                }
            } else {
                ownBeacon = readTag(getIntent());
                if (ownBeacon != null)
                beaconName.setText(ownBeacon.name);
            }
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {

        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            String content = "";

            Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if (parcelableArrayExtra != null) {
                NdefMessage[] ndefMessages = new NdefMessage[parcelableArrayExtra.length];
                for (int i = 0; i < parcelableArrayExtra.length; i++) {
                    ndefMessages[i] = (NdefMessage) parcelableArrayExtra[i];
                }

                if (ndefMessages[0] != null) {
                    byte[] payload = ndefMessages[0].getRecords()[0].getPayload();
                    for (byte aPayload : payload) {
                        content += (char) aPayload;
                    }
                    String[] beaconFields = parseString(content);
                    ownBeacon=generateBeacon(beaconFields);

                }

            }

        }
    }

    private OwnBeacon readTag(Intent intent) {
        String content = "";

        Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if (parcelableArrayExtra != null) {
            NdefMessage[] ndefMessages = new NdefMessage[parcelableArrayExtra.length];
            for (int i = 0; i < parcelableArrayExtra.length; i++) {
                ndefMessages[i] = (NdefMessage) parcelableArrayExtra[i];
            }

            if (ndefMessages[0] != null) {
                byte[] payload = ndefMessages[0].getRecords()[0].getPayload();
                for (byte aPayload : payload) {
                    content += (char) aPayload;
                }
                String[] beaconFields = parseString(content);
                OwnBeacon ownBeacon=generateBeacon(beaconFields);

                return ownBeacon;
            }

        }

        return null;
    }
}
