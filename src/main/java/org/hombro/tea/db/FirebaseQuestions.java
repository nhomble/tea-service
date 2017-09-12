package org.hombro.tea.db;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import io.vertx.core.json.JsonObject;
import org.hombro.tea.util.IOHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by nicolas on 9/12/2017.
 */
public class FirebaseQuestions {
    private static final String FIREBASE_KEY_ID = "FIREBASE_PRIVATE_KEY_ID";
    private static final String FIREBASE_KEY = "FIREBASE_PRIVATE_KEY_ID";
    private static final String FIREBASE_NAME = "https://tea-service.firebaseio.com/";
    private final File secretFile;

    private File writeSecretFile() {
        JsonObject secret = new JsonObject()
                .put("type", "service_account")
                .put("project_id", "tea-service")
                .put("client_email", "firebase-adminsdk-29nud@tea-service.iam.gserviceaccount.com")
                .put("client_id", "100022818526909960804")
                .put("auth_uri", "https://accounts.google.com/o/oauth2/auth")
                .put("token_uri", "https://accounts.google.com/o/oauth2/token")
                .put("auth_provider_x509_cert_url", "https://www.googleapis.com/oauth2/v1/certs")
                .put("client_x509_cert_url", "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-29nud%40tea-service.iam.gserviceaccount.com")
                .put("private_key_id", System.getenv(FIREBASE_KEY_ID))
                .put("private_key", System.getenv(FIREBASE_KEY));
        return IOHelper.writeTempFile(secret.encodePrettily());
    }

    public FirebaseQuestions() {
        secretFile = writeSecretFile();
        try {
            FileInputStream stream = new FileInputStream(secretFile);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(stream))
                    .setDatabaseUrl(FIREBASE_NAME)
                    .build();
            FirebaseApp.initializeApp(options);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
