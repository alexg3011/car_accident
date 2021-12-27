package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Repository
public class AccidentHibernate implements Store {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public void add(Accident accident, String[] rIds) {
        Set<Rule> rules = new HashSet<>();
        for (String id : rIds) {
            rules.add(getRule(Integer.parseInt(id)));
        }
        accident.setRules(rules);
        tx(session -> session.save(accident));
    }

    @Override
    public void update(Accident accident, String[] rIds) {
        Set<Rule> rules = new HashSet<>();
        for (String id : rIds) {
            rules.add(getRule(Integer.parseInt(id)));
        }
        accident.setRules(rules);
        tx(session -> session.merge(accident));
    }

    @Override
    public Accident findById(int id) {
        return (Accident) tx(session -> session.createQuery("from Accident where id=:id")
                .setParameter("id", id).uniqueResult());
    }

    public List<Accident> getAll() {
        return tx(session -> session.createQuery(
                "select distinct a from Accident a left join fetch a.type left join fetch a.rules").list());
    }

    @Override
    public void delete(int id) {
        Accident accident = findById(id);
        tx(session -> {
            session.remove(accident);
            return null;
        });
    }

    @Override
    public Collection<AccidentType> findAllType() {
        return tx(session -> session.createQuery("from AccidentType ").list());
    }

    @Override
    public AccidentType findTypeById(int id) {
        return (AccidentType) tx(session -> session.createQuery("from AccidentType where id=:id")
                .setParameter("id", id).uniqueResult());
    }

    @Override
    public Rule getRule(int id) {
        return (Rule) tx(session -> session.createQuery("from Rule where id=:id")
                .setParameter("id", id).uniqueResult());
    }

    @Override
    public Collection<Rule> getAllRule() {
        return tx(session -> session.createQuery("from Rule").list());
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            transaction.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}