package dev.galka.account.domain;

import dev.galka.account.dto.AccountDto;
import dev.galka.account.inout.AccountDbEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Date;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(source = "creationDate", target = "creationDate", qualifiedByName = "createOrGetDate")
    AccountDbEntity map(AccountDto newAccount);

    @Mapping(source = "creationDate", target = "creationDate", qualifiedByName = "createOrGetDate")
    AccountDto map(AccountDbEntity account);

    @Named(value = "createOrGetDate")
    public static Date createOrGet(Date date) {
        return date == null ? new Date() : date;
    }
}