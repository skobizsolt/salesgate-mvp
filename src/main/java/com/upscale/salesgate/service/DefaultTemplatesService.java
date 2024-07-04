package com.upscale.salesgate.service;

import com.google.cloud.firestore.DocumentSnapshot;
import com.upscale.salesgate.exception.Errors;
import com.upscale.salesgate.exception.ServiceExpection;
import com.upscale.salesgate.model.GetTemplateResponse;
import com.upscale.salesgate.model.PostTemplateRequest;
import com.upscale.salesgate.model.PostTemplateResponse;
import com.upscale.salesgate.model.PutTemplateRequest;
import com.upscale.salesgate.persistence.TemplatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultTemplatesService implements TemplatesService {

    private final TemplatesRepository templatesRepository;

    @Override
    public PostTemplateResponse createTemplate(final PostTemplateRequest postTemplateRequest) {
        return new PostTemplateResponse()
                .templateId(templatesRepository.saveTemplate(postTemplateRequest));
    }

    @Override
    public void deleteTemplate(final String templateId) {
        templatesRepository.deleteTemplate(templateId);
    }

    @Override
    public void editTemplate(String templateId, PutTemplateRequest body) {
        templatesRepository.editTemplate(templateId, body);
    }

    @Override
    public GetTemplateResponse getTemplate(final String templateId) {
        final DocumentSnapshot result = templatesRepository.getTemplate(templateId);
        if (!result.exists()) {
            throw new ServiceExpection(Errors.ENTITY_NOT_FOUND);
        }
        return result.toObject(GetTemplateResponse.class);
    }
}
