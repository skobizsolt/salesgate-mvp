package com.upscale.salesgate.service;

import com.upscale.salesgate.model.GetTemplateResponse;
import com.upscale.salesgate.model.PostTemplateRequest;
import com.upscale.salesgate.model.PostTemplateResponse;
import com.upscale.salesgate.model.PutTemplateRequest;

public interface TemplatesService {
    PostTemplateResponse createTemplate(PostTemplateRequest postTemplateRequest);

    void deleteTemplate(String templateId);

    void editTemplate(String templateId, PutTemplateRequest body);

    GetTemplateResponse getTemplate(String templateId);
}
