package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem implements Store {

    private final Map<Integer, Accident> accidents = new HashMap<>();
    private final Map<Integer, AccidentType> types = new HashMap<>();
    private final AtomicInteger count = new AtomicInteger(0);
    private final AtomicInteger typeCount = new AtomicInteger(0);

    @Override
    public void add(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(count.getAndIncrement());
        }
        accidents.put(accident.getId(), accident);
    }

    @Override
    public void update(Accident accident) {
        accidents.replace(accident.getId(), accident);
    }

    @Override
    public Accident findById(int id) {
        return accidents.get(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }

    @Override
    public void delete(int id) {
        accidents.remove(id);
    }

    @Override
    public void addType(AccidentType type) {
        if (type.getId() == 0) {
            type.setId(typeCount.getAndIncrement());
        }
        types.put(type.getId(), type);
    }

    @Override
    public AccidentType findTypeById(int id) {
        return types.get(id);
    }

    @Override
    public Collection<AccidentType> findAllType() {
        return types.values();
    }
}
