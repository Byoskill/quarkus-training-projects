package app;

import adoption.domain.AdoptionRepository;
import adoption.domain.Monster;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.arc.log.LoggerName;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class MonsterFileLoader {

    //private static final Logger LOGGER = Logger.getLogger(MonsterFileLoader.class);

    @LoggerName("monster-file-loader")
    @Inject
    Logger logger;

    public static class MonsterList {
        public List<Monster> monsters;

        public Stream<Monster> stream() {
            return monsters.stream();
        }
    }

    @Transactional
    public void initLoad(@Observes StartupEvent event,
                         AdoptionRepository adoptionRepository,
                         @ConfigProperty(name = "backend.file.monsters") String jsonFileName) throws StreamReadException, DatabindException, IOException {
        if (adoptionRepository.getAllMonsters().isEmpty()) {
            File monsterFileName = new File(jsonFileName);
            logger.info("Loading default monster list from " + monsterFileName.getAbsolutePath());
            MonsterList monsters = new ObjectMapper().readValue(monsterFileName, new TypeReference<MonsterList>() {
            });
            monsters.stream().forEach(monster -> adoptionRepository.addMonsterToAdopt(monster));
            logger.info("Monsters loaded successfully");
        }
    }

}