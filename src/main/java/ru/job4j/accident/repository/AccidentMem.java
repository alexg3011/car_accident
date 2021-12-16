package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.*;

@Repository
public class AccidentMem implements Store {
    private final Map<Integer, Accident> accidents = new HashMap<>();

    public AccidentMem() {
        add(new Accident(1, "Превышение скорости",
                "Превышение на 20-40км/ч", "M8 256km"));
        add(new Accident(2, "Заезд за стоп-линию",
                "Заезд за стоп-линию светофора", "Москва, пр-т Мира"));
    }

    @Override
    public void add(Accident accident) {
        accidents.put(accident.getId(), accident);
    }

    @Override
    public Accident get(int id) {
        return accidents.get(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();

    }
}
