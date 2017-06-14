package com.binaryfountain.airplaneservice.domain;

import lombok.Data;

/**
 * Created by smarufu15 on 6/7/17.
 */
@Data
public class PassengerAircraft implements Airplane {

  private Size size;
  private String name;
  private long id;
  private String type;

  public PassengerAircraft(){
    type = "Passenger";
  }

 }
