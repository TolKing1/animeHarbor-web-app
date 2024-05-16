package org.tolking.animeharbor.dto;

import lombok.Getter;
import org.springframework.core.io.ByteArrayResource;

@Getter
public record ImageDataDto(ByteArrayResource resource, String mimeType) {

}
