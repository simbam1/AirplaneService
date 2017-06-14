package com.binaryfountain.airplaneservice.domain;

import lombok.Data;
import lombok.Setter;

/**
 * Created by smarufu15 on 6/7/17.
 */
@Data
public class CargoAircraft implements Airplane{

  private Size size;
  private String name;
  private long id;
  private String type;

  public CargoAircraft(){
    type = "Cargo";
  }
}
