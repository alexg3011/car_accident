package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem implements Store {
    private final Map<Integer, Accident> accidents = new HashMap<>();
    private final AtomicInteger count = new AtomicInteger(3);

    public AccidentMem() {
        accidents.put(1, new Accident("Превышение скорости",
                "Превышение на 20-40км/ч", "M8 256km"));
        accidents.put(2, new Accident("Заезд за стоп-линию",
                "Заезд за стоп-линию светофора", "Москва, пр-т Мира"));
    }

    @Override
    public void add(Accident accident) {
        accident.setId(count.getAndIncrement());
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
