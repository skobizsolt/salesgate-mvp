package com.upscale.salesgate.service;

import com.upscale.salesgate.model.PostTemplateRequest;
import com.upscale.salesgate.model.PostTemplateResponse;

public interface TemplatesService {
    PostTemplateResponse createTemplate(PostTemplateRequest postTemplateRequest);
}
