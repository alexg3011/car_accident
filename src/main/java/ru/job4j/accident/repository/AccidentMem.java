package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem implements Store {

    private final Map<Integer, Accident> accidents = new HashMap<>();
    private final Map<Integer, AccidentType> types = new HashMap<>();
    private final Map<Integer, Rule> rules = new HashMap<>();
    private final AtomicInteger count = new AtomicInteger(0);
    private final AtomicInteger typeCount = new AtomicInteger(0);

    public AccidentMem() {
        addType(AccidentType.of(1, "Две машины"));
        addType(AccidentType.of(2, "Машина и человек"));
        addType(AccidentType.of(3, "Машина и велосипед"));
        addRule(Rule.of(1, "Статья. 1"));
        addRule(Rule.of(2, "Статья. 2"));
        addRule(Rule.of(3, "Статья. 3"));
    }

    @Override
    public void add(Accident accident, String[] rIds) {
        if (accident.getId() == 0) {
            accident.setId(count.getAndIncrement());
        }
        accident.setRules(getCurrentRule(rIds));
        accident.setType(findTypeById(accident.getType().getId()));
        accidents.put(accident.getId(), accident);
    }

    @Override
    public void update(Accident accident, String[] rIds) {
        accident.setType(findTypeById(accident.getType().getId()));
        accident.setRules(getCurrentRule(rIds));
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

    @Override
    public void addRule(Rule rule) {
        rules.put(rule.getId(), rule);
    }
    @Override
    public Rule getRule(int id) {
        return rules.get(id);
    }

    @Override
    public Set<Rule> getCurrentRule(String[] ids) {
        Set<Rule> currentRule = new HashSet<>();
        for (String rId : ids) {
            currentRule.add(getRule(Integer.parseInt(rId)));
        }
        return currentRule;
    }

    @Override
    public Collection<Rule> getAllRule() {
        return rules.values();
    }
}
