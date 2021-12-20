package ru.job4j.accident.repository;

import ru.job4j.accident.model.Accident;

import java.util.Collection;

public interface Store {

    void add(Accident accident);

    void update(Accident accident);

    Accident findById(int id);

    Collection<Accident> findAll();

    void delete(int id);
}
