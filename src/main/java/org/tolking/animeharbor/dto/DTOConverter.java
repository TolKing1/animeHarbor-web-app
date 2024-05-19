package org.tolking.animeharbor.dto;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


public abstract class DTOConverter<T, D> {
    private final ModelMapper modelMapper;

    public DTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DTOConverter() {
        this.modelMapper = new ModelMapper();
    }

    public T convertToEntity(D dto) {
        return modelMapper.map(dto, getTypeEntity());
    }

    public D convertToDto(T entity) {
        return modelMapper.map(entity, getTypeDTO());
    }

    public List<T> convertToEntityList(List<D> dtoList) {
        return dtoList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

    public List<D> convertToDtoList(List<T> entityList) {
        return entityList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    protected abstract Class<T> getTypeEntity();

    protected abstract Class<D> getTypeDTO();
}
