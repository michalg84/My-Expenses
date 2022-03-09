package dev.galka.account.domain;

import dev.galka.account.dto.AccountDto;
import dev.galka.account.inout.AccountDbEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDbEntity map(AccountDto newAccount);

    AccountDto map(AccountDbEntity account);
}