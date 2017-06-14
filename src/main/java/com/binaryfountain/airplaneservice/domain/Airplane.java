package com.binaryfountain.airplaneservice.domain;

/**
 * Created by smarufu15 on 6/7/17.
 */
public interface Airplane {

  void setSize(Size size);
  void setName(String name);
  void setId(long id);

  Size getSize();
  String getName();
  long getId();


}
