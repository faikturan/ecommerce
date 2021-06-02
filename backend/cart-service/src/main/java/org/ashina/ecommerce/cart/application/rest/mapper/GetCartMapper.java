package org.ashina.ecommerce.cart.application.rest.mapper;

import org.ashina.ecommerce.cart.application.query.model.GetCartView;
import org.ashina.ecommerce.cart.application.rest.dto.GetCartDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GetCartMapper {

    GetCartMapper INSTANCE = Mappers.getMapper(GetCartMapper.class);

    GetCartDto map(GetCartView view);
}
