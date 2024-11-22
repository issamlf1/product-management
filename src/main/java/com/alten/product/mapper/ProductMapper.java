package com.alten.product.mapper;

import com.alten.product.data.Product;
import com.alten.product.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * @author lanfouf
 **/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    ProductDTO toDto(Product product);

    Product toEntity(ProductDTO productDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ProductDTO productDTO, @MappingTarget Product product);
}
