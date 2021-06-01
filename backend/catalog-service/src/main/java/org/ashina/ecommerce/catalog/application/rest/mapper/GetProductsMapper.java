package org.ashina.ecommerce.catalog.application.rest.mapper;

import org.ashina.ecommerce.catalog.application.query.model.GetProductsView;
import org.ashina.ecommerce.catalog.application.rest.dto.GetProductsDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GetProductsMapper {

    GetProductsMapper INSTANCE = Mappers.getMapper(GetProductsMapper.class);

    GetProductsDto map(GetProductsView view);
}
