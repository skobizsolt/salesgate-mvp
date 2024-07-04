package com.upscale.salesgate.service;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.upscale.salesgate.exception.Errors;
import com.upscale.salesgate.exception.ServiceExpection;
import com.upscale.salesgate.model.*;
import com.upscale.salesgate.persistence.ContentsRepository;
import com.upscale.salesgate.service.dto.ContentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultContentsService implements ContentsService {

    private final TemplatesService templatesService;
    private final ContentsRepository contentsRepository;

    @Override
    public void saveContent(final String templateId, final PostContentRequest request) {
        final var template = templatesService.getTemplate(templateId);
        request.getContentData()
                .forEach(dataRecord -> saveRecord(template, templateId, dataRecord));
    }

    @Override
    public GetContentResponse getContentData(final String templateId) {
        final var results = contentsRepository.getContent(templateId);
        return new GetContentResponse()
                .contentData(results.stream()
                        .map(QueryDocumentSnapshot::getData)
                        .toList()
                );
    }

    private void saveRecord(final GetTemplateResponse template, final String templateId, final DataRecord dataRecord) {
        final TemplateField field = template.getFields()
                .stream()
                .filter(templateField -> templateField.getName().equals(dataRecord.getName()))
                .findFirst()
                .orElse(null);
        if (field == null) {
            log.info("Field '{}' is not found in this template, skipping...", dataRecord.getName());
            return;
        }
        final ContentDto contentDto = ContentDto.builder()
                .name(dataRecord.getName())
                .data(convert(dataRecord.getData(), field.getType()))
                .build();
        contentsRepository.saveContent(templateId, contentDto);
    }

    @SuppressWarnings("java:S1905")
    private static Object convert(final String data, final TemplateField.TypeEnum type) {
        return switch (type) {
            case STRING -> (String) data;
            case INTEGER -> Integer.parseInt((String) data);
            case BOOLEAN -> Boolean.getBoolean((String) data);
            default -> new ServiceExpection(Errors.UNEXPECTED_ERROR);
        };
    }
}
