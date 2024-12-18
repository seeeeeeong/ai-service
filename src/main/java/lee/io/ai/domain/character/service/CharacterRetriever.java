package lee.io.ai.domain.character.service;

import lee.io.ai.domain.character.dto.GetCharactersListResDto;
import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.character.repository.CharacterRepository;
import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.global.exception.ErrorCode;
import lee.io.ai.global.exception.notfound.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CharacterRetriever {

    private final CharacterRepository characterRepository;

    public Character getCharacterByCharacterId(Long characterId) {
        return characterRepository.findByCharacterId(characterId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHARACTER_NOT_FOUND));
    }

    public List<GetCharactersListResDto> getAllCharacters() {
        return characterRepository.findAllCharacters();
    }

    public List<GetCharactersListResDto> getCharactersByMember(Member member) {
        return characterRepository.findCharactersByMember(member);
    }

}
