package ru.job4j.accident.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.Store;

import java.util.Collection;

@Service
public class AccidentService {

    private final Store store;

    @Autowired
    public AccidentService(Store store) {
        this.store = store;
    }

    public void addAccident(Accident accident, String[] rIds) {
        store.add(accident, rIds);
    }

    public void updateAccident(Accident accident, String[] rIds) {
        store.update(accident, rIds);
    }

    public Accident getAccident(int id) {
        return store.findById(id);
    }

    public Collection<Accident> findAll() {
        return store.findAll();
    }

    public void deleteAccident(int id) {
        store.delete(id);
    }

    public Collection<AccidentType> getAllTypes() {
        return store.findAllType();
    }

    public Collection<Rule> findAllRule() {
        return store.getAllRule();
    }
}
