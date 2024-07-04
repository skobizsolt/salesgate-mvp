package com.upscale.salesgate.service;

import com.upscale.salesgate.model.PostContentRequest;

public interface ContentsService {
    void saveContent(String templateId, PostContentRequest request);
}
