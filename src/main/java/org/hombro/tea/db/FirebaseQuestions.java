package org.hombro.tea.db;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.*;
import com.google.firebase.tasks.Task;
import com.google.firebase.tasks.TaskCompletionSource;
import com.google.firebase.tasks.Tasks;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.hombro.tea.question.code.CodingQuestion;
import org.hombro.tea.question.code.Language;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by nicolas on 9/12/2017.
 */
public class FirebaseQuestions implements QuestionStore {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseQuestions.class);
    private static final String FIREBASE_KEY_ID = "FIREBASE_PRIVATE_KEY_ID";
    private static final String FIREBASE_KEY = "FIREBASE_PRIVATE_KEY";
    private static final String FIREBASE_NAME = "https://tea-service.firebaseio.com/";
    private final InputStream secretFile;

    private InputStream getSecretFile() throws FileNotFoundException {
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
                .put("private_key", System.getenv(FIREBASE_KEY).replace("\\n", "\n"));
        return new ByteArrayInputStream(secret.toString().getBytes());
    }

    public FirebaseQuestions() {
        try {
            secretFile = getSecretFile();
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(secretFile))
                    .setDatabaseUrl(FIREBASE_NAME)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getQuestions() {
        final TaskCompletionSource<List<String>> taskCompletionSource = new TaskCompletionSource<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("questions");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> questions = new ArrayList<>();
                for (DataSnapshot l : dataSnapshot.getChildren()) {
                    for (DataSnapshot q : l.getChildren()) {
                        questions.add(l.getKey() + "/" + q.getKey());
                    }
                }
                taskCompletionSource.setResult(questions);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                logger.info("Database read error: " + databaseError.getMessage());
                taskCompletionSource.setException(new RuntimeException(databaseError.getMessage()));
            }
        });
        Task<List<String>> t = taskCompletionSource.getTask();
        try {
            Tasks.await(t);
            if (t.isSuccessful())
                return t.getResult();
            else {
                throw new RuntimeException("Did not read from firebase");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CodingQuestion getQuestionInfo(Language language, String name) {
        final TaskCompletionSource<CodingQuestion> taskCompletionSource = new TaskCompletionSource<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("questions")
                .child(language.toString().toUpperCase())
                .child(name.toLowerCase());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taskCompletionSource.setResult(dataSnapshot.getValue(CodingQuestion.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                logger.info("Database read error: " + databaseError.getMessage());
                taskCompletionSource.setException(new RuntimeException(databaseError.getMessage()));
            }
        });
        Task<CodingQuestion> t = taskCompletionSource.getTask();
        try {
            Tasks.await(t);
            if (t.isSuccessful())
                return t.getResult();
            else
                throw new RuntimeException("Did not read from firebase");
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
