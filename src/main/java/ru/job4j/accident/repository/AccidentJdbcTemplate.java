package ru.job4j.accident.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class AccidentJdbcTemplate implements Store {
    private final JdbcTemplate jdbc;

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Transactional
    @Override
    public void add(Accident accident, String[] rIds) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "insert into accident (name, text, address, type_id) "
                            + "values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, accident.getName());
            statement.setString(2, accident.getText());
            statement.setString(3, accident.getAddress());
            statement.setInt(4, accident.getType().getId());
            return statement;
        }, holder);
        int aId = (Integer) Objects.requireNonNull(holder.getKeys()).get("id");
        accident.setId(aId);
        for (String id : rIds) {
            jdbc.update("insert into accident_rule (accident_id, rules_id) "
                            + "values (?,?)",
                    aId,
                    Integer.parseInt(id));
        }
    }

    @Transactional
    @Override
    public void update(Accident accident, String[] rIds) {
        jdbc.update("update accident set name=?, text=?, address=?, type_id=? where id=?",
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId(), accident.getId());
        jdbc.update("delete from accident_rule where accident_id=?", accident.getId());
        for (String rId : rIds) {
            jdbc.update("insert into accident_rule (accident_id, rules_id) values (?, ?)",
                    accident.getId(), Integer.parseInt(rId));
        }
    }

    @Override
    public Collection<Accident> getAll() {
        return jdbc.query("select a.id, a.name, a.text, a.address, "
                        + "t.id as t_id, t.name as t_name, ar.accident_id as accident_id,"
                        + " r.id as r_id, r.name as r_name "
                        + "from accident a "
                        + "left join type t on a.type_id = t.id "
                        + "left join accident_rule ar on a.id = ar.accident_id "
                        + "join rule r on ar.rules_id = r.id",
                new AccidentResultSet());

    }

    @Override
    public void delete(int id) {
        jdbc.update("delete from accident where id=?", id);
    }

    @Override
    public Accident findById(int id) {
        return jdbc.query(
                        "select a.id, a.name, a.text, a.address, "
                                + "t.id as t_id, t.name as t_name, r.id as r_id, r.name as r_name "
                                + "from accident a "
                                + "left join type t on a.id = t.id "
                                + "left join accident_rule ar on a.id = ar.accident_id "
                                + "join rule r on ar.rules_id = r.id "
                                + "where a.id = ?", new Object[]{id}, new AccidentResultSet())
                .stream().findAny().orElse(null);
    }

    @Override
    public Rule getRule(int id) {
        return jdbc.query("select id, name from rule where id = ?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Rule.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public AccidentType findTypeById(int id) {
        return jdbc.query("select * from type where id=? ", new Object[]{id},
                        new BeanPropertyRowMapper<>(AccidentType.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public Collection<AccidentType> findAllType() {
        return jdbc.query("select * from type",
                new BeanPropertyRowMapper<>(AccidentType.class));
    }

    @Override
    public Set<Rule> getAllRule() {
        return new HashSet<>(jdbc.query("select * from rule",
                new BeanPropertyRowMapper<>(Rule.class)));
    }

    class AccidentResultSet implements ResultSetExtractor<Collection<Accident>> {
        @Override
        public Collection<Accident> extractData(ResultSet resultSet)
                throws SQLException, DataAccessException {
            Map<Integer, Accident> accidents = new HashMap<>();
            Set<Rule> rules;
            while (resultSet.next()) {

                int aId = resultSet.getInt("id");
                Accident accident = accidents.get(aId);
                if (accident == null) {

                    AccidentType accidentType = new AccidentType();
                    Accident newAccident = new Accident();
                    newAccident.setId(aId);
                    newAccident.setName(resultSet.getString("name"));
                    newAccident.setText(resultSet.getString("text"));
                    newAccident.setAddress(resultSet.getString("address"));

                    accidentType.setId(resultSet.getInt("t_id"));
                    accidentType.setName(resultSet.getString("t_name"));
                    newAccident.setType(accidentType);

                    rules = new HashSet<>();
                    rules.add(Rule.of(resultSet.getInt("r_id"), resultSet.getString("r_name")));
                    newAccident.setRules(rules);
                    accidents.put(aId, newAccident);
                } else {
                    accidents.get(aId).addRule(Rule.of(
                            resultSet.getInt("r_id"), resultSet.getString("r_name")));
                }
            }
            return accidents.values();
        }
    }
}
