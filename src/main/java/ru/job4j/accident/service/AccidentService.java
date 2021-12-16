package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.Store;

import java.util.Collection;

@Service
public class AccidentService {

    private final Store store;

    public AccidentService(Store store) {
        this.store = store;
    }

    public void addAccident(Accident accident) {
        store.add(accident);
    }

    public Accident getAccident(int id) {
        return store.get(id);
    }

    public Collection<Accident> findAll() {
        return store.findAll();
    }
}
