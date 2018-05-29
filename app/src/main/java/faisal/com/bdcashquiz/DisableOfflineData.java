package faisal.com.bdcashquiz;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class DisableOfflineData {

        public  void disableOfflineData(FirebaseFirestore db)
        {
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(false)
                    .build();
            try {
                db.setFirestoreSettings(settings);
            }catch(Exception e)
            {

            }
        }

}
