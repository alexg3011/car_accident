package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem implements Store {

    private final Map<Integer, Accident> accidents = new HashMap<>();
    private final AtomicInteger count = new AtomicInteger(0);

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
}
