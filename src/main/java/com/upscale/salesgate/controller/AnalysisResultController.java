package com.upscale.salesgate.controller;

import com.upscale.salesgate.api.AnalysisResultApi;
import com.upscale.salesgate.model.GetAnalysisResultResponse;
import com.upscale.salesgate.service.AnalysisResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnalysisResultController implements AnalysisResultApi {

    private final AnalysisResultService analysisResultService;

    @Override
    public ResponseEntity<GetAnalysisResultResponse> getAnalysisResult(final String templateId) {
        return ResponseEntity.ok(new GetAnalysisResultResponse()
                .content(analysisResultService.getAnalysis(templateId)));
    }
}
