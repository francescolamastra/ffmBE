package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.domain.dto.TokenCredenzialiDto;
import it.fantacalcio.ffm.domain.dto.TokenCredenzialiProjectionDto;
import it.fantacalcio.ffm.domain.entity.TokenCredenziali;
import it.fantacalcio.ffm.domain.entity.TokenCredenzialiInfo;
import it.fantacalcio.ffm.mapper.TokenCredenzialiMapper;
import it.fantacalcio.ffm.mapper.TokenCredenzialiProjectionMapper;
import it.fantacalcio.ffm.repository.TokenCredenzialiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenCredenzialiService {
    private final TokenCredenzialiRepository tokenCredenzialiRepository;

    public List<TokenCredenzialiProjectionDto> getAllTokenCredenzialiInfo() {
        List<TokenCredenzialiInfo> tokenCredenzialiInfos = tokenCredenzialiRepository.findAllTokenCredenzialiInfo();
        return tokenCredenzialiInfos.stream()
                .map(TokenCredenzialiProjectionMapper.INSTANCE::toDto)
                .toList();
    }

    public TokenCredenzialiProjectionDto save(TokenCredenzialiDto tokenCredenzialiDto){
        TokenCredenziali tokenCredenziali = tokenCredenzialiRepository.save(TokenCredenzialiMapper.INSTANCE.toEntity(tokenCredenzialiDto));
        return TokenCredenzialiProjectionMapper.INSTANCE.toDto(tokenCredenziali);
    }
}
