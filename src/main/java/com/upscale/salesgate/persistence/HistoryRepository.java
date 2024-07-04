package com.upscale.salesgate.persistence;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.upscale.salesgate.exception.Errors;
import com.upscale.salesgate.exception.ServiceExpection;
import com.upscale.salesgate.service.dto.AnalysisResultDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistoryRepository {

    public static final String COLLECTION_HISTORY = "history";
    private final Firestore dbConnection = FirestoreClient.getFirestore();

    @SneakyThrows
    public DocumentSnapshot getAnalysisHistory(final String templateId) {
        final ApiFuture<DocumentSnapshot> templatesFuture = dbConnection
                .collection(COLLECTION_HISTORY)
                .document(templateId)
                .get();
        validateResult(templatesFuture.get());

        return templatesFuture.get();
    }

    @SneakyThrows
    public void saveReview(final String templateId, final AnalysisResultDto result) {
        final ApiFuture<WriteResult> templatesFuture = dbConnection
                .collection(COLLECTION_HISTORY)
                .document(templateId)
                .set(result);
        validateResult(templatesFuture.get());
    }

    private static void validateResult(final Object result) {
        if (result == null) {
            throw new ServiceExpection(Errors.ENTITY_NOT_FOUND);
        }
    }
}
