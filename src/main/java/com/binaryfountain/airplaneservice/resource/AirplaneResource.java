package com.binaryfountain.airplaneservice.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

import com.binaryfountain.airplaneservice.domain.Airplane;
import com.binaryfountain.airplaneservice.service.AirplaneService;

/**
 * Created by smarufu15 on 6/6/17.
 */
@RestController
@Component
@Api(basePath = "/airplane-service/seats", value = "airplane-service",
    description = "Operations with Airplane Service")
@RequestMapping(value = "/airplane-service/v1/")
public class AirplaneResource {

  @Autowired
  private AirplaneService airplaneService;

  @RequestMapping(value = "/queue", method = RequestMethod.POST)
  @ApiOperation(value = "places an airplane in the queue, if validations are passed")
  @ApiResponses(value = {
      @ApiResponse(code = SC_OK, message = "The application is up"),
      @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = "The application is down")
  })
  public ResponseEntity<Airplane> queue(
      @ApiParam(value = "Name of the plane", required = true)
      @RequestParam("aircraftName") String aircraftName,
      @ApiParam(value = "Cargo vs Passenger", required = true)
      @RequestParam("aircraftType") String aircraftType,
      @ApiParam(value = "Small vs Large", required = true)
      @RequestParam("aircraftSize") String aircraftSize) {
    return ResponseEntity.ok(airplaneService.queue(aircraftName,aircraftType,aircraftSize));
  }

  @RequestMapping(value = "/dequeue", method = RequestMethod.DELETE)
  @ApiOperation(value = "Removes airplane in the queue, if validations are passed")
  @ApiResponses(value = {
      @ApiResponse(code = SC_OK, message = "The application is up"),
      @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = "The application is down")
  })
  public ResponseEntity<Airplane> queue() {
    return ResponseEntity.ok(airplaneService.dequeue());
  }

  @RequestMapping(value = "/switch", method = RequestMethod.PUT)
  @ApiOperation(value = "Removes airplane in the queue, if validations are passed")
  @ApiResponses(value = {
      @ApiResponse(code = SC_OK, message = "The application is up"),
      @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = "The application is down")
  })
  public ResponseEntity<String> systemSwitch() {
    return ResponseEntity.ok(airplaneService.systemSwitch());
  }


}
