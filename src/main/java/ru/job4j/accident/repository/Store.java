package ru.job4j.accident.repository;

import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.Set;

public interface Store {

    void add(Accident accident, String[] rIds);

    void update(Accident accident, String[] rIds);

    Accident findById(int id);

    Collection<Accident> findAll();

    void delete(int id);

    void addType(AccidentType type);

    AccidentType findTypeById(int id);

    Collection<AccidentType> findAllType();

    void addRule(Rule rule);

    Rule getRule(int id);

    Set<Rule> getCurrentRule(String[] ids);

    Collection<Rule> getAllRule();

}
