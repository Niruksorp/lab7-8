package com.example.lab4.Service;

import com.example.lab4.DTOPackage.KafkaDTO;
import com.example.lab4.Entity.Jewel;
import com.example.lab4.Repository.JewelsRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
public class CRUDJewel implements CRUDJewelInt {

    private final JewelsRepo jewelsRepo;
    private final KafkaTemplate<Long, KafkaDTO> kafkaStarshipTemplate;
    private final ObjectMapper objectMapper;
    private Long counter = 0l;

    CRUDJewel(JewelsRepo jewelsRepo, KafkaTemplate<Long, KafkaDTO> kafkaStarshipTemplate, ObjectMapper objectMapper) {
        this.jewelsRepo = jewelsRepo;
        this.kafkaStarshipTemplate = kafkaStarshipTemplate;
        this.objectMapper = objectMapper;
    }

    public Jewel add(Jewel jewel) {
        KafkaDTO dto = createDto("Пользователь добавил jewel" + jewel.toString());
        produce(dto);
        return jewelsRepo.save(jewel);
    }
    public ArrayList<Jewel> findAll() {
        KafkaDTO dto = createDto("Пользователь запросил вывод всех Jewels");
        produce(dto);
        return new ArrayList<>(jewelsRepo.findAll());
    }
    public void update(Jewel jewel) {
        KafkaDTO dto = createDto("Пользователь запросил обновление. Приходящий обьект {}" + jewel.toString());
        produce(dto);
        jewelsRepo.save(jewel);
    }
    public void deleteById(Long id) {
        KafkaDTO dto = createDto("Удаления по id {}" + id);
        produce(dto);
        jewelsRepo.deleteById(id);
    }
    public Optional<Jewel> findById(Long id) {
        KafkaDTO dto = createDto("Нахождение по id: {}" + id);
        produce(dto);
        return jewelsRepo.findById(id);
    }
    public ArrayList<Jewel> findJewelsByBrandName(String name) {
        KafkaDTO dto = createDto("Нахождение по brand name" + name);
        produce(dto);
        return jewelsRepo.findJewelsByBrandName(name);
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 5000)
    public void produce(KafkaDTO kafkaDTO) {
        log.info("<= sending {}", writeValueAsString(kafkaDTO));
        kafkaStarshipTemplate.send("quickstart-events", kafkaDTO);
    }

    private KafkaDTO createDto(String text) {
        return new KafkaDTO(counter++,text);
    }

    private String writeValueAsString(KafkaDTO dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing value to JSON failed: " + dto.toString());
        }
    }
}
