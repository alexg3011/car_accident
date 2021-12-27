package ru.job4j.accident.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.repository.RuleRepository;
import ru.job4j.accident.repository.TypeRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class AccidentService {

    private final AccidentRepository accidentStore;
    private final TypeRepository typeStore;
    private final RuleRepository ruleStore;

    @Autowired
    public AccidentService(AccidentRepository accidentStore, TypeRepository typeStore, RuleRepository ruleStore) {
        this.accidentStore = accidentStore;
        this.typeStore = typeStore;
        this.ruleStore = ruleStore;
    }

    @Transactional
    public void addAccident(Accident accident, String[] rIds) {
        Set<Rule> rules = new HashSet<>();
        for (String id : rIds) {
            rules.add(getRule(Integer.parseInt(id)));
        }
        accident.setRules(rules);
        accidentStore.save(accident);
    }
    @Transactional
    public void updateAccident(Accident accident, String[] rIds) {
        Set<Rule> rules = new HashSet<>();
        for (String id : rIds) {
            rules.add(getRule(Integer.parseInt(id)));
        }
        accident.setRules(rules);
        accidentStore.save(accident);
    }
    @Transactional
    public Accident getAccident(int id) {
        return accidentStore.findAccidentById(id);
    }
    @Transactional
    public Collection<Accident> findAll() {
        return accidentStore.findAllAccidents();
    }
    @Transactional
    public void deleteAccident(int id) {
        Accident accident = getAccident(id);
        accidentStore.delete(accident);
    }
    @Transactional
    public Collection<AccidentType> getAllTypes() {
        return (Collection<AccidentType>) typeStore.findAll();
    }
    @Transactional
    public AccidentType findTypeById(int id) {
        return typeStore.findById(id).orElse(null);
    }
    @Transactional
    public Rule getRule(int id) {
        return ruleStore.findById(id).orElse(null);
    }
    @Transactional
    public Collection<Rule> findAllRule() {
        return (Collection<Rule>) ruleStore.findAll();
    }
}
