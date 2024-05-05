package com.byoskill.adapters.adoptions.memory;

import com.byoskill.domain.adoption.model.Monster;
import com.byoskill.domain.adoption.repository.AdoptionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class MonsterFileLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonsterFileLoader.class);
    
    public void initLoad(@Observes final StartupEvent event,
                         final AdoptionRepository monsterRepository,
                         @ConfigProperty(name = "backend.file.monsters") final String jsonFileName) throws IOException {
        final File monsterFileName = new File(jsonFileName);
        LOGGER.info("Loading default monster list from {}", monsterFileName.getAbsolutePath());
        final MonsterList monsters = new ObjectMapper().readValue(monsterFileName, new TypeReference<MonsterList>() {
        });
        monsters.stream().forEach(monster -> monsterRepository.addMonsterToAdopt(monster).await().indefinitely());
        LOGGER.info("Monsters loaded successfully");
    }

    public static class MonsterList {
        public List<Monster> monsters;

        public Stream<Monster> stream() {
            return monsters.stream();
        }
    }

}
