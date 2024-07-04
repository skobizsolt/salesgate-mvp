package com.upscale.salesgate.persistence;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.upscale.salesgate.exception.Errors;
import com.upscale.salesgate.exception.ServiceExpection;
import com.upscale.salesgate.service.dto.ContentDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContentsRepository {

    public static final String COLLECTION_CONTENTS = "contents";
    public static final String COLLECTION_FIELDS = "fields";
    private final Firestore dbConnection = FirestoreClient.getFirestore();

    @SneakyThrows
    public void saveContent(final String templateId, final ContentDto contentDto) {

        final ApiFuture<WriteResult> templatesFuture = dbConnection
                .collection(COLLECTION_CONTENTS)
                .document(templateId)
                .collection(COLLECTION_FIELDS)
                .document()
                .set(contentDto);

        validateResult(templatesFuture.get());
    }

    @SneakyThrows
    public DocumentSnapshot getContent(final String templateId) {
        final ApiFuture<DocumentSnapshot> templatesFuture = dbConnection
                .collection(COLLECTION_CONTENTS)
                .document(templateId)
                .get();
        validateResult(templatesFuture.get());

        return templatesFuture.get();
    }

    private static void validateResult(final Object result) {
        if (result == null) {
            throw new ServiceExpection(Errors.ENTITY_NOT_FOUND);
        }
    }
}
