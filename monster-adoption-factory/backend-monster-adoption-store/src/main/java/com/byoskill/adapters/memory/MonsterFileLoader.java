package com.byoskill.adapters.memory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.byoskill.adoption.model.Monster;
import com.byoskill.adoption.repository.MonsterRepository;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class MonsterFileLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonsterFileLoader.class);
    
    public static class MonsterList {
        public List<Monster> monsters;

        public Stream<Monster> stream() {
            return monsters.stream();
        }    
    }

    public void initLoad(@Observes StartupEvent event, 
                        MonsterRepository monsterRepository, 
                        @ConfigProperty(name = "backend.file.monsters") String jsonFileName ) throws StreamReadException, DatabindException, IOException {
        File monsterFileName = new File(jsonFileName);
        LOGGER.info("Loading default monster list from {}", monsterFileName.getAbsolutePath());
        MonsterList monsters = new ObjectMapper().readValue(monsterFileName, new TypeReference<MonsterList>() {});
        monsters.stream().forEach(monster -> monsterRepository.addMonsterToAdopt(monster));        
        LOGGER.info("Monsters loaded successfully");
    }
    
}
