package com.upscale.salesgate.persistence;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.upscale.salesgate.exception.Errors;
import com.upscale.salesgate.exception.ServiceExpection;
import com.upscale.salesgate.model.PostTemplateRequest;
import com.upscale.salesgate.model.PutTemplateRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TemplatesRepository {

    public static final String COLLECTION_TEMPLATES = "templates";
    private final Firestore dbConnection = FirestoreClient.getFirestore();

    @SneakyThrows
    public String saveTemplate(final PostTemplateRequest request) {
        final var templateId = UUID.randomUUID().toString();

        final ApiFuture<WriteResult> templatesFuture = dbConnection
                .collection(COLLECTION_TEMPLATES)
                .document(templateId)
                .set(request);

        validateResult(templatesFuture.get());
        return templateId;
    }

    @SneakyThrows
    public void deleteTemplate(final String templateId) {
        final ApiFuture<WriteResult> templatesFuture = dbConnection
                .collection(COLLECTION_TEMPLATES)
                .document(templateId)
                .delete();
        validateResult(templatesFuture.get());
    }

    @SneakyThrows
    public void editTemplate(final String templateId, final PutTemplateRequest request) {
        final ApiFuture<WriteResult> templatesFuture = dbConnection
                .collection(COLLECTION_TEMPLATES)
                .document(templateId)
                .set(request, SetOptions.merge());
        validateResult(templatesFuture.get());
    }

    @SneakyThrows
    public DocumentSnapshot getTemplate(final String templateId) {
        final ApiFuture<DocumentSnapshot> templatesFuture = dbConnection
                .collection(COLLECTION_TEMPLATES)
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
