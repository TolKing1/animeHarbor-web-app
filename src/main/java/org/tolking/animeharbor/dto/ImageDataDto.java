package org.tolking.animeharbor.dto;

import org.springframework.core.io.ByteArrayResource;

public class ImageDataDto {
    private final ByteArrayResource resource;
    private final String mimeType;

    public ImageDataDto(ByteArrayResource resource, String mimeType) {
        this.resource = resource;
        this.mimeType = mimeType;
    }

    public ByteArrayResource getResource() {
        return resource;
    }

    public String getMimeType() {
        return mimeType;
    }
}
