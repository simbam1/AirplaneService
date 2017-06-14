package com.binaryfountain.airplaneservice.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.binaryfountain.airplaneservice.domain.Airplane;
import com.binaryfountain.airplaneservice.domain.CargoAircraft;
import com.binaryfountain.airplaneservice.domain.PassengerAircraft;
import com.binaryfountain.airplaneservice.domain.Size;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by smarufu15 on 6/13/17.
 */
@Component
public class AirplaneDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;


  public Airplane aqmRequestProcess(){
    return dequeue();
  }

  public Airplane aqmRequestProcess(final Airplane plane){
    return insertAirplane(plane);
  }

  public String systemSwitch(){
    String UPDATE_SQL;
    String systemState = getSystemState();

    if (systemState.equals("OFF")){
      UPDATE_SQL = "UPDATE system SET switch = 1 where id = 1";
      updateDB(UPDATE_SQL);
      return "ON";
    }

    UPDATE_SQL = "UPDATE system SET switch = 0 where id = 1";
    updateDB(UPDATE_SQL);
    return "OFF";

  }

  public String getSystemState(){
    String SELECT_SQL = "SELECT switch from system where id = 1";
    long resultSet = jdbcTemplate.query(SELECT_SQL,
        (result, row) -> {
          return result.getLong("switch") ;
        }).stream().findFirst().get();
    if(resultSet == 0L){
      return "OFF";
    }
    return "ON";
  }

  public Airplane dequeue(){
    String SEARCH_SQL_BASE = "SELECT id, plane_name, plane_size, type_id, created_on FROM aircraft ORDER BY type_id asc, plane_size asc, id asc";
    String DELETE_SQL = "DELETE FROM aircraft WHERE id IN (?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    Optional<Map<String,String>> resultSet = jdbcTemplate.query(SEARCH_SQL_BASE,
        (result, row) -> {
          Map<String, String> m = new HashMap<>();
          m.put("name", result.getString("plane_name"));
          m.put("type_id", Long.toString(result.getLong("type_id")));
          m.put("size", result.getString("plane_size"));
          m.put("id", Long.toString(result.getLong("id")));
          return m;
        }).stream().findFirst();

    jdbcTemplate.update(
        connection -> {
          PreparedStatement ps =
              connection.prepareStatement(DELETE_SQL, new String[]{"id"});
          ps.setLong(1,Long.parseLong(resultSet.get().get("id")));
          return ps;
        },
        keyHolder);
    return mapAirplane(Long.parseLong(resultSet.get().get("id")), resultSet.get().get("name"), resultSet.get().get("type_id"), resultSet.get().get("size"));
  }

  private void updateDB(String UPDATE_SQL){
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
        connection -> {
          PreparedStatement ps =
              connection.prepareStatement(UPDATE_SQL);
          return ps;
        },
        keyHolder);
  }


  public Airplane insertAirplane(final Airplane plane){
    String INSERT_SQL = "INSERT INTO aircraft (type_id, plane_name, plane_size) VALUES (?,?,?)";
    String SELECT_SQL = "SELECT id, plane_name, plane_size, type_id, created_on from aircraft";

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
        connection -> {
          int typeId = 1;
          if (plane.getClass().isInstance(new CargoAircraft())) {
            typeId = 2;
          }
          PreparedStatement ps =
              connection.prepareStatement(INSERT_SQL, new String[]{"id"});
          ps.setInt(1, typeId);
          ps.setString(2, plane.getName());
          ps.setString(3, plane.getSize().name());
          return ps;
        },
        keyHolder);


    Map<String,String> resultSet = jdbcTemplate.query(SELECT_SQL + " WHERE id = " + keyHolder.getKey().longValue() ,
        (result, row) -> {
          Map<String,String> m = new HashMap<>();
          m.put("name",result.getString("plane_name"));
          m.put("type_id",Long.toString(result.getLong("type_id")));
          m.put("size",result.getString("plane_size"));
          m.put("id",Long.toString(result.getLong("id")));
          return m;
        }).stream().findFirst().get();
    return mapAirplane(Long.parseLong(resultSet.get("id")), resultSet.get("name"), resultSet.get("type_id"), resultSet.get("size"));
  }

  public long numOfPlanesQueued() {
    long num;
    try {
      num = jdbcTemplate.query("SELECT id FROM aircraft ORDER BY id desc",
          (result, row) -> {
            return result.getLong("id");
          }
      ).stream().findFirst().get();
    }catch(Exception e){
      num = 0;
    }
    return num;
  }

  private Airplane mapAirplane(final long id, final String name, final String aircraftType, final String aircraftSize){
    Airplane plane;
    if(aircraftType.equalsIgnoreCase("2")){
      plane = new CargoAircraft();
    }else{
      plane = new PassengerAircraft();
    }
    plane.setId(id);
    plane.setName(name);
    if(aircraftSize.equalsIgnoreCase("Small")){
      plane.setSize(Size.SMALL);
    }else{
      plane.setSize(Size.LARGE);
    }
    return plane;
  }
}
