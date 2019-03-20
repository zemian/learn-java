package com.zemian.hellojava.cipher;

/*

== Keystore

https://docs.oracle.com/javase/8/docs/technotes/tools/unix/keytool.html

The JDK contains a root keystore file under "$JAVA_HOME/jre/lib/security/cacerts" and
the password is "changeit".

User may create their own keystore file. A Keystore may contains 3 type of entries

1. PrivateKeyEntry ->  a cryptographic PrivateKey that associate with a certificate of public key
2. SecretKeyEntry -> a cryptographic SecretKey
3. TrustedCertificateEntry -> a single public key Certificate belonging to another party. (trusted cert)

=== How to create a Keystore file

1. Create a new keystore file with a privateKey entry

  keytool -keystore mykeystore.jks -storetype JKS -genkey -keyalg RSA -alias mydomain

A keystore file will be created with a self signed cert (PrivateKeyEntry). Both the keystore file and the
privateKey cert can have password protection.

You may verify keystore file like this:

  keytool -keystore mykeystore.jks -list

=== How to create and import a cert

1. Create a CSR from an existing privateKey.

  keytool -keystore mykeystore.jks -certreq -alias mydomain -file mydomain.csr

2. Send CSR to a Cert Authority Provider and purchase a CERT.

3. Download cert CA root cert if there is any

  keytool -keystore mykeystore.jks -import -trustcacerts -alias root -file root.crt

4. When you receive your new purchased CERT, import it

  keytool -keystore mykeystore.jks -import -trustcacerts -alias mydomain -file mydomain.crt

=== How to create a Self-Signed Cert

1. Create a CSR from an existing privateKey.

  keytool -keystore mykeystore.jks -certreq -alias mydomain -file mydomain.csr

2. Generate a self-signed cert

  keytool -keystore mykeystore.jks -gencert -alias mydomain -infile mydomain.csr -outfile mydomain.crt

3. Import the cert

  keytool -keystore mykeystore.jks -importcert -alias mydomain -file mydomain.crt

NOTE: Alternatively you may use "-genkeypair" to create the privateKey and cert as one step!

  keytool -keystore mykeystore.jks -storetype JKS -genkeypair -keyalg RSA -alias mydomain

=== How to create a SecretKey entry

NOTE: The JKS keystore type only supports asymmetric (public/private) keys. You would have to create a new
keystore of type JCEKS to support secret keys! If not, you will get this error:
"keytool error: java.security.KeyStoreException: Cannot store non-PrivateKeys"

  keytool -keystore mykeystore2.jceks -storetype JCEKS -genkeypair -keyalg RSA -alias mydomain
  keytool -keystore mykeystore2.jceks -storetype JCEKS -genseckey -keyalg AES -keysize 256 -alias myaes256key

=== How to export a cert from a server

  keytool -printcert -rfc -sslserver mydomain.com:12345 > mydomain.pem

Once you created a .pem file, you then can import it into one of keytore like this:

  keytool -keystore mykeystore.jks -importcert -alias mydomain -file mydomain.pem

=== How to change keystore file password

  keytool -keystore mykeystore.jks -storepasswd

=== How to change key password

  keytool -keystore mykeystore.jks -keypasswd -alias mydomain

=== How to use Trusted Keystore file

  java -Djavax.net.ssl.trustStore=mykeystore.jks

NOTE: By default it will use same system password as the "cacerts" file from JDK.

== Crypto vs PasswordHasher

The Crypto is an cipher encryption service and it can used to encrypt and decrypt data. Given the correct
algorithm, key, and IV params, you can retrieve the original data.

The PasswordHasher is a one-way hashing function for safe password storage. Original value can not be recovered,
but can only verify. When hashed with salt, each call to same value, the result should be different, while
verification should remain true.

The Hasher is wraper to JDK's built-in MessageDigest to create fast hashing. It's up to user how to use it
securely. If you just want to hash password, you probably should use PasswordHasher above.
 */