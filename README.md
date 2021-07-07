# Couchbase Lite Android 2.8.5 network config reproduction issue process

## Setup
- App uses network_config.xml file with custom domain exception added for __example.com__
- App uses kotlin version of code that was provided as a tutorial in CouchbaseLite webpage
 [here](https://docs.couchbase.com/couchbase-lite/current/android/gs-build.html)


## Reproduction steps
- install application by ./gradlew installDebug
- launch application check logcat error log
- Following error will be reported 

E/CouchbaseLite/NETWORK: {N8litecore4repl12C4SocketImplE#1} WebSocket failed to connect! (reason=Network error 8)
E/CouchbaseLite/REPLICATOR: {Repl#2} Got LiteCore error: Network error 8 "server TLS certificate untrusted"

## Workaround
- Removal/commenting out of **domain-config** section from **src/res/xml/network_config.xml** makes TLS connection working.
- It will fail on **401 Unauthorized** which is expected as we don't want to disclose passwords. But thats pass TLS connection establishment.
- With proper passwords replication works correctly
- In our case workaround is acceptable for production but it limits testability of new features as they often require security exceptions in network config.