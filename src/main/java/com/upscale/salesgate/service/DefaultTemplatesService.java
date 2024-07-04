package com.upscale.salesgate.service;

import com.upscale.salesgate.model.PostTemplateRequest;
import com.upscale.salesgate.model.PostTemplateResponse;
import com.upscale.salesgate.persistence.TemplatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultTemplatesService implements TemplatesService {

    private final TemplatesRepository templatesRepository;

    @Override
    public PostTemplateResponse createTemplate(PostTemplateRequest postTemplateRequest) {
        return new PostTemplateResponse()
                .templateId(templatesRepository.saveTemplate(postTemplateRequest));
    }
}
