package com.example.demo.mapper;

import com.example.demo.dto.ImageDTO;
import com.example.demo.models.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    @Mapping(target = "picByte", source = "picByte")
    ImageDTO toImageDTO(Image image, byte[] picByte);
}
