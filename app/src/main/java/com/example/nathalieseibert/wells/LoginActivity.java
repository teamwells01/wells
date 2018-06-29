package com.example.nathalieseibert.wells;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class LoginActivity extends AppCompatActivity {

    Button email_sign_in_button;
    EditText email;
    EditText password;
    DatabaseHelper databaseHelper;

    //disable the backbutton after logOut
    public void onBackPressed() {
        //do nothing
    }

    public void onClickSwitchActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_sign_in_button = (Button) findViewById(R.id.email_sign_in_button);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        databaseHelper = new DatabaseHelper(LoginActivity.this);

        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isExist = databaseHelper.checkUserExist(email.getText().toString(), password.getText().toString());

                if (isExist) {
                    Intent intent = new Intent(LoginActivity.this, MainMenueActivity.class);
                    intent.putExtra("username", email.getText().toString());
                    startActivity(intent);
                } else {
                    password.setText(null);
                    Toast.makeText(LoginActivity.this, "Login failed. Invalid username or password.", Toast.LENGTH_SHORT).show();
                }
            }
        });


//        TextView textView;
//        FingerprintManager.CryptoObject cryptoObject;
//        FingerprintManager fingerprintManager;
//        KeyguardManager keyguardManager;
//
//
//        // If you’ve set your app’s minSdkVersion to anything lower than 23, then you’ll need to verify that the device is running Marshmallow
//        // or higher before executing any fingerprint-related code
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//
//        {
//            //Get an instance of KeyguardManager and FingerprintManager//
//            keyguardManager =
//                    (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
//            fingerprintManager =
//                    (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
//
//            textView = findViewById(R.id.fingerprint);
//
//            //Check whether the device has a fingerprint sensor//
//            if (!fingerprintManager.isHardwareDetected()) {
//                // If a fingerprint sensor isn’t available, then inform the user that they’ll be unable to use your app’s fingerprint functionality//
//                textView.setText(R.string.noFingerprintFunktion);
//            }
//            //Check whether the user has granted your app the USE_FINGERPRINT permission//
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
//                // If your app doesn't have this permission, then display the following text//
//                textView.setText(R.string.fingerprintAktivPls);
//            }
//
//            //Check that the user has registered at least one fingerprint//
//            if (!fingerprintManager.hasEnrolledFingerprints()) {
//                // If the user hasn’t configured any fingerprints, then display the following message//
//                textView.setText(R.string.fingerEinstSpeiPls);
//            }
//
//            //Check that the lockscreen is secured//
//            if (!keyguardManager.isKeyguardSecure()) {
//                // If the user hasn’t secured their lockscreen with a PIN password or pattern, then display the following text//
//                textView.setText(R.string.displaysperreAktivPls);
//            } else {
//                try {
//                    generateKey();
//                } catch (FingerprintException e) {
//                    e.printStackTrace();
//                }
//
//                if (initCipher()) {
//                    //If the cipher is initialized successfully, then create a CryptoObject instance//
//                    cryptoObject = new FingerprintManager.CryptoObject(cipher);
//
//                    // Here, I’m referencing the FingerprintHandler class that we’ll create in the next section. This class will be responsible
//                    // for starting the authentication process (via the startAuth method) and processing the authentication process events//
//                    FingerprintHandler helper = new FingerprintHandler(this);
//                    helper.startAuth(fingerprintManager, cryptoObject);
//
//                }
//            }
//        }
//    }
//
////Create the generateKey method that we’ll use to gain access to the Android keystore and generate the encryption key//
//
//    private void generateKey() throws FingerprintException {
//        try {
//            // Obtain a reference to the Keystore using the standard Android keystore container identifier (“AndroidKeystore”)//
//            keyStore = KeyStore.getInstance("AndroidKeyStore");
//
//            //Generate the key//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
//            }
//
//            //Initialize an empty KeyStore//
//            keyStore.load(null);
//
//            //Initialize the KeyGenerator//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                keyGenerator.init(new
//
//                        //Specify the operation(s) this key can be used for//
//                        KeyGenParameterSpec.Builder(KEY_NAME,
//                        KeyProperties.PURPOSE_ENCRYPT |
//                                KeyProperties.PURPOSE_DECRYPT)
//                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
//
//                        //Configure this key so that the user has to confirm their identity with a fingerprint each time they want to use it//
//                        .setUserAuthenticationRequired(true)
//                        .setEncryptionPaddings(
//                                KeyProperties.ENCRYPTION_PADDING_PKCS7)
//                        .build());
//            }
//
//            //Generate the key//
//            keyGenerator.generateKey();
//
//        } catch (KeyStoreException
//                | NoSuchAlgorithmException
//                | NoSuchProviderException
//                | InvalidAlgorithmParameterException
//                | CertificateException
//                | IOException exc) {
//            exc.printStackTrace();
//            throw new FingerprintException(exc);
//        }
//    }
//
//    //Create a new method that we’ll use to initialize our cipher//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private boolean initCipher() {
//        try {
//            //Obtain a cipher instance and configure it with the properties required for fingerprint authentication//
//            cipher = Cipher.getInstance(
//                    KeyProperties.KEY_ALGORITHM_AES + "/"
//                            + KeyProperties.BLOCK_MODE_CBC + "/"
//                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
//        } catch (NoSuchAlgorithmException |
//                NoSuchPaddingException e) {
//            throw new RuntimeException("Failed to get Cipher", e);
//        }
//
//        try {
//            keyStore.load(null);
//            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
//                    null);
//            cipher.init(Cipher.ENCRYPT_MODE, key);
//            //Return true if the cipher has been initialized successfully//
//            return true;
//        } catch (KeyPermanentlyInvalidatedException e) {
//
//            //Return false if cipher initialization failed//
//            return false;
//        } catch (KeyStoreException | CertificateException
//                | UnrecoverableKeyException | IOException
//                | NoSuchAlgorithmException | InvalidKeyException e) {
//            throw new RuntimeException("Failed to init Cipher", e);
//        }
//    }
//
//}
//
//@TargetApi(Build.VERSION_CODES.M)
//@RequiresApi(api = Build.VERSION_CODES.M)
//
//class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
//
//    // You should use the CancellationSignal method whenever your app can no longer process user input, for example when your app goes
//    // into the background. If you don’t use this method, then other apps will be unable to access the touch sensor, including the lockscreen!//
//
//
//    private final Context context;
//
//    FingerprintHandler(Context mContext) {
//        context = mContext;
//    }
//
//    //Implement the startAuth method, which is responsible for starting the fingerprint authentication process//
//
//    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
//        CancellationSignal cancellationSignal;
//        cancellationSignal = new CancellationSignal();
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
//    }
//
//    @Override
//    //onAuthenticationError is called when a fatal error has occurred. It provides the error code and error message as its parameters//
//
//    public void onAuthenticationError(int errMsgId, CharSequence errString) {
//
//        //I’m going to display the results of fingerprint authentication as a series of toasts.
//        //Here, I’m creating the message that’ll be displayed if an error occurs//
//
//        Toast.makeText(context, "Finger nicht erkannt! " + errString, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//
//    //onAuthenticationFailed is called when the fingerprint doesn’t match with any of the fingerprints registered on the device//
//
//    public void onAuthenticationFailed() {
//        Toast.makeText(context, "Falscher Finger!", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//
//    //onAuthenticationHelp is called when a non-fatal error has occurred. This method provides additional information about the error,
//    //so to provide the user with as much feedback as possible I’m incorporating this information into my toast//
//    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
//        Toast.makeText(context, "Authentication help\n" + helpString, Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//
//    //onAuthenticationSucceeded is called when a fingerprint has been successfully matched to one of the fingerprints stored on the user’s device//
//    public void onAuthenticationSucceeded(
//
//            FingerprintManager.AuthenticationResult result) {
//        Intent intent = new Intent(context.getApplicationContext(), MainMenueActivity.class);
//        context.startActivity(intent);
    }


}

