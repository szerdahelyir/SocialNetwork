package com.example.demo.mapper;

import com.example.demo.dto.ImageDTO;
import com.example.demo.models.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDTO toImageDTO(Image image);
}
