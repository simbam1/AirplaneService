package com.binaryfountain.airplaneservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.binaryfountain.airplaneservice.dao.AirplaneDao;
import com.binaryfountain.airplaneservice.domain.Airplane;
import com.binaryfountain.airplaneservice.domain.CargoAircraft;
import com.binaryfountain.airplaneservice.domain.PassengerAircraft;
import com.binaryfountain.airplaneservice.domain.Size;

import java.util.Optional;

/**
 * Created by smarufu15 on 6/7/17.
 */
@Component
public class AirplaneService {

  @Autowired
  private AirplaneDao airplaneDao;

  public Airplane queue(String name, String aircraftType, String aircraftSize){
    validateSystemState();
    validateCraftSize(aircraftSize);
    validateCraftType(aircraftType);
    validateCraftName(name);
    Airplane plane = mapAirplane(name, aircraftType, aircraftSize);
    return airplaneDao.aqmRequestProcess(plane);
  }

  public Airplane dequeue(){
    validateSystemState();
    validateNoneEmptyDatabase();
    return airplaneDao.aqmRequestProcess();
  }

  public String systemSwitch(){
    return airplaneDao.systemSwitch();
  }

  private void validateNoneEmptyDatabase(){
    long num = airplaneDao.numOfPlanesQueued();
    if(!Optional.ofNullable(num).isPresent() || num < 1) {
      throw new RuntimeException("The queue is empty");
    }
  }

  private void validateSystemState(){
    if(!Optional.ofNullable(airplaneDao.getSystemState()).isPresent() || airplaneDao.getSystemState().equalsIgnoreCase("OFF")){
      throw new RuntimeException("System is off");
    }
  }

  private void validateCraftName(final String name){
    if(!Optional.ofNullable(name).isPresent() || name.length() < 2) {
      throw new RuntimeException("Invalid aircraft name.");
    }
  }

  private void validateCraftType(final String aircraftType){
    if(!Optional.ofNullable(aircraftType).isPresent() || !(aircraftType.equalsIgnoreCase("Cargo") || aircraftType.equalsIgnoreCase("Passenger"))) {
      throw new RuntimeException("Invalid Aircraft Type. Only Cargo and Passenger are allowed");
    }
  }

  private void validateCraftSize(final String aircraftSize){
    if(!Optional.ofNullable(aircraftSize).isPresent() || !(aircraftSize.equalsIgnoreCase("Small") || aircraftSize.equalsIgnoreCase("Large"))) {
      throw new RuntimeException("Invalid Aircraft size. Only Small and Large are permitted");
    }
  }

  private Airplane mapAirplane(final String name, final String aircraftType, final String aircraftSize){
    Airplane plane;
    if(aircraftType.equalsIgnoreCase("Cargo")){
      plane = new CargoAircraft();
    }else{
      plane = new PassengerAircraft();
    }
    plane.setName(name);
    if(aircraftSize.equalsIgnoreCase("Small")){
      plane.setSize(Size.SMALL);
    }else{
      plane.setSize(Size.LARGE);
    }
    return plane;
  }
}
