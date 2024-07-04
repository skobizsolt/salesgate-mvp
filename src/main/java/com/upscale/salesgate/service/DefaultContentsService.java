package com.upscale.salesgate.service;

import com.upscale.salesgate.exception.Errors;
import com.upscale.salesgate.exception.ServiceExpection;
import com.upscale.salesgate.model.PostContentRequest;
import com.upscale.salesgate.model.TemplateField;
import com.upscale.salesgate.persistence.ContentsRepository;
import com.upscale.salesgate.service.dto.ContentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultContentsService implements ContentsService {

    private final TemplatesService templatesService;
    private final ContentsRepository contentsRepository;

    @Override
    public void saveContent(final String templateId, final PostContentRequest request) {
        final var template = templatesService.getTemplate(templateId);

        final TemplateField field = template.getFields()
                .stream()
                .filter(templateField -> templateField.getName().equals(request.getName()))
                .findFirst()
                .orElseThrow(() -> new ServiceExpection(Errors.TEMPLATE_ERROR));
        final ContentDto contentDto = ContentDto.builder()
                .name(request.getName())
                .data(convert(request.getData(), field.getType()))
                .build();
        contentsRepository.saveContent(templateId, contentDto);
    }

    private Object convert(final String data, final TemplateField.TypeEnum type) {
        return switch (type) {
            case STRING -> (String) data;
            case INTEGER -> Integer.parseInt((String) data);
            case BOOLEAN -> Boolean.getBoolean((String) data);
            default -> new ServiceExpection(Errors.UNEXPECTED_ERROR);
        };
    }
}
