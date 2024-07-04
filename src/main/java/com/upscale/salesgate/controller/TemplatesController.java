package com.upscale.salesgate.controller;

import com.upscale.salesgate.api.TemplatesApi;
import com.upscale.salesgate.model.GetTemplateResponse;
import com.upscale.salesgate.model.PostTemplateRequest;
import com.upscale.salesgate.model.PostTemplateResponse;
import com.upscale.salesgate.model.PutTemplateRequest;
import com.upscale.salesgate.service.TemplatesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TemplatesController implements TemplatesApi {

    private final TemplatesService templatesService;

    /**
     * POST /templates/new : Create new template
     *
     * @param postTemplateRequest (optional)
     * @return OK (status code 200)
     */
    @Override
    public ResponseEntity<PostTemplateResponse> createTemplate(PostTemplateRequest postTemplateRequest) {
        return ResponseEntity.ok(templatesService.createTemplate(postTemplateRequest));
    }

    /**
     * DELETE /templates/{templateId} : Delete template
     *
     * @param templateId (required)
     * @return OK (status code 200)
     */
    @Override
    public ResponseEntity<Void> deleteTemplate(String templateId) {
        return TemplatesApi.super.deleteTemplate(templateId);
    }

    /**
     * PUT /templates/{templateId} : Edit an existing template
     *
     * @param templateId (required)
     * @param body       (required)
     * @return OK (status code 200)
     */
    @Override
    public ResponseEntity<Void> editTemplate(String templateId, PutTemplateRequest body) {
        return TemplatesApi.super.editTemplate(templateId, body);
    }

    /**
     * GET /templates/{templateId} : Get template information
     *
     * @param templateId (required)
     * @return OK (status code 200)
     */
    @Override
    public ResponseEntity<GetTemplateResponse> getTemplate(String templateId) {
        return TemplatesApi.super.getTemplate(templateId);
    }
}
