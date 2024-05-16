package org.tolking.animeharbor.dto;

import org.springframework.core.io.ByteArrayResource;


public record ImageDataDto(ByteArrayResource resource, String mimeType) {

}
