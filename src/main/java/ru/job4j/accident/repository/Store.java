package ru.job4j.accident.repository;

import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;

public interface Store {

    void add(Accident accident, String[] rIds);

    void update(Accident accident, String[] rIds);

    Accident findById(int id);

    Collection<Accident> getAll();

    void delete(int id);

    Collection<AccidentType> findAllType();

    AccidentType findTypeById(int id);

    Rule getRule(int id);

    Collection<Rule> getAllRule();

}
