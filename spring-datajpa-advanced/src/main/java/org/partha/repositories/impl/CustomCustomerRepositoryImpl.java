package org.partha.repositories.impl;

import org.partha.dto.CreateCustomerDto;
import org.partha.repositories.CustomCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.List;

public class CustomCustomerRepositoryImpl implements CustomCustomerRepository {

    @Autowired
    NamedParameterJdbcTemplate nJdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer insertCustomer(CreateCustomerDto dto) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("name", dto.getName());
        paramSource.addValue("email", dto.getEmail());
        paramSource.addValue("age", dto.getAge());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        nJdbcTemplate.update("insert into mydatabase.customer (name, email,age) values(:name,:email, :age)", paramSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

//    @Override
//    public List<Integer> incrementAgeAndRetrieve() {
//        MapSqlParameterSource paramSource = new MapSqlParameterSource();
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        PreparedStatement pstmt = null;
//
//        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
//            String query = "update mydatabase.customer set age= age+1 WHERE id=1";
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                return connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
//            }
//        };
//
//
////        //int updateRecordCount = jdbcTemplate.query(preparedStatementCreator, keyHolder);
////        List<Integer> list = jdbcTemplate.query(preparedStatementCreator, new ResultSetExtractor<List<Integer>>() {
////            @Override
////            public List<Integer> extractData(ResultSet rs=) throws SQLException, DataAccessException {
////                rs.get
////                return List.of();
////            }
////        });
//        jdbcTemplate.query(preparedStatementCreator, new PreparedStatementSetter(), new ResultSetExtractor<>() {
//        })
//
//        //jdbcTemplate.update(preparedStatementCreator, keyHolder);
//
//
//        return List.of(keyHolder.getKey().intValue());
//
//    }
}
