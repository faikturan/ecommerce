package org.ashina.ecommerce.catalog.application.rest.mapper;

import org.ashina.ecommerce.catalog.application.query.model.SearchProductsView;
import org.ashina.ecommerce.catalog.application.rest.dto.SearchProductsDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SearchProductsMapper {

    SearchProductsMapper INSTANCE = Mappers.getMapper(SearchProductsMapper.class);

    SearchProductsDto map(SearchProductsView view);
}
