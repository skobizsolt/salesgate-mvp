package com.upscale.salesgate.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ContentDto {
    String name;
    Object data;
}
