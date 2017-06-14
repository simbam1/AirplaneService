package com.binaryfountain.airplaneservice.domain;

import lombok.Getter;

/**
 * Created by smarufu15 on 6/7/17.
 */
@Getter
public enum Size {
  SMALL(0),
  LARGE(1);
  private final int priority;
  Size(int priority) {
    this.priority= priority;
  }
}
