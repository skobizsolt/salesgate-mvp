package com.upscale.salesgate.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upscale.salesgate.config.ExternalProperties;
import com.upscale.salesgate.exception.Errors;
import com.upscale.salesgate.exception.ServiceExpection;
import com.upscale.salesgate.model.GetContentResponse;
import com.upscale.salesgate.persistence.HistoryRepository;
import com.upscale.salesgate.service.dto.AnalysisResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(ExternalProperties.class)
public class DefaultAnalysisResultService implements AnalysisResultService {

    private final ContentsService contentsService;
    private final RestTemplate restTemplate;
    private final ExternalProperties externalProperties;
    private final HistoryRepository historyRepository;

    @Override
    public String getAnalysis(final String templateId) {
        final var history = historyRepository.getAnalysisHistory(templateId);
        if (history.exists()) {
            return Objects.requireNonNull(history.toObject(AnalysisResultDto.class)).getReview();
        }

        final var content = contentsService.getContentData(templateId);

        if (content == null) {
            throw new ServiceExpection(Errors.CONTENT_NOT_FOUND, templateId);
        }

        final var result = getAnalysisResult(content);
        historyRepository.saveReview(templateId, result);

        return result.getReview();
    }

    private AnalysisResultDto getAnalysisResult(final GetContentResponse request) {

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.ALL));
        headers.setAcceptCharset(List.of(StandardCharsets.UTF_8));
        final var httpEntity = new HttpEntity<>(request, headers);

        final ResponseEntity<Object> result = restTemplate.exchange(
                externalProperties.getSeaShellUrl(),
                HttpMethod.POST,
                httpEntity,
                Object.class);

        final ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.convertValue(result.getBody(), AnalysisResultDto.class);
    }
}
