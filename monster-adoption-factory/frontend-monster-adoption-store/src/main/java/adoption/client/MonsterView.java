package adoption.client;

import adoption.dto.MonsterDto;

import java.util.List;

public record MonsterView(List<MonsterDto> monsters) {
}
