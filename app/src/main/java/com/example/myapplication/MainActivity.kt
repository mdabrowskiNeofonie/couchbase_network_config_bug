package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.couchbase.lite.*
import java.net.URI


class MainActivity : AppCompatActivity() {
    private val TAG = "COUCH"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CouchbaseLite.init(this)
        val database =
            Database("test", DatabaseConfiguration().setEncryptionKey(EncryptionKey("key")))
        val targetEndpoint: Endpoint = URLEndpoint(URI("wss://cbsgwt.adac.de/pannenhilfe"))
        val replConfig = ReplicatorConfiguration(database, targetEndpoint)
        replConfig.replicatorType = AbstractReplicatorConfiguration.ReplicatorType.PULL;
        //WE CANNOT DISCLOSE CREDENTIALS BUT ERROR HAPPENS DURING TLS HANDSHAKE - BEFORE CREDENTIALS ARE PASSED
        replConfig.setAuthenticator(BasicAuthenticator("pannenhilfe", "pannenhilfe"))
        val replicator = Replicator(replConfig);
        replConfig.isContinuous = true
        replicator.addChangeListener {
            Log.i(TAG, it.toString())
        }
        replicator.start()
    }
}