package com.upscale.salesgate.persistence;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.upscale.salesgate.model.PostTemplateRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class TemplatesRepository {

    private final Firestore dbConnection = FirestoreClient.getFirestore();

    @SneakyThrows
    public String saveTemplate(PostTemplateRequest request) {
        final var templateId = UUID.randomUUID().toString();

        final ApiFuture<WriteResult> templatesFuture = dbConnection
                .collection("templates")
                .document(templateId)
                .set(request);

        validateResult(templatesFuture);
        return templateId;
    }

    private static void validateResult(ApiFuture<WriteResult> templatesFuture) throws InterruptedException, ExecutionException {
        final WriteResult result = templatesFuture.get();

        if (result == null) {
            throw new RuntimeException("Saving new template failed!");
        }
    }
}
