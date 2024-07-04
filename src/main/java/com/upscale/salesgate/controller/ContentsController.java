package com.upscale.salesgate.controller;

import com.upscale.salesgate.api.ContentsApi;
import com.upscale.salesgate.model.GetContentResponse;
import com.upscale.salesgate.model.PostContentRequest;
import com.upscale.salesgate.service.ContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContentsController implements ContentsApi {

    private final ContentsService contentsService;

    @Override
    public ResponseEntity<GetContentResponse> getContentData(final String templateId) {
        return ContentsApi.super.getContentData(templateId);
    }

    @Override
    public ResponseEntity<Void> saveContent(final String templateId, final PostContentRequest postContentRequest) {
        contentsService.saveContent(templateId, postContentRequest);
        return ResponseEntity.ok().build();
    }
}
