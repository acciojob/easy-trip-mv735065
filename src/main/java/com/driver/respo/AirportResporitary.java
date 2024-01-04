package com.driver.respo;

import com.driver.model.Airport;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.*;
@Repository
public class AirportResporitary {

      List<Airport> airportList;
      List<Flight> flightList;

      Map<Integer,List<Integer>> flightPassenger;

      List<Passenger> passengerList;

      public  AirportResporitary(){
          this.airportList=new ArrayList<>();
          this.flightList=new ArrayList<>();
          this.passengerList=new ArrayList<>();
          this.flightPassenger=new HashMap<>();
      }

    public List<Airport> getAirportList() {
        return airportList;
    }

    public void addAirport(Airport airport) {
        airportList.add(airport);
    }


    public List<Airport> getLargestAirportName() {
          return airportList;
    }

    public String addFlight(Flight flight) {
          flightList.add(flight);
          return "SUCCESS";

    }


    public String addPassenger(Passenger passenger) {

          passengerList.add(passenger);
          return "SUCCESS";
    }


    public List<Flight> getFlightList() {
          return flightList;
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
          if(!flightPassenger.containsKey(flightId)){
              List<Integer> list=new ArrayList<>();
              list.add(passengerId);
              flightPassenger.put(flightId,list);

          }
          else {


              List<Integer> list=flightPassenger.get(flightId);

              if(list.contains(passengerId)) return "FAILURE";

              int capacity=checkFilghtCapacity(flightId);
              boolean full=capacity<=list.size();

              if(full){
                  list.add(passengerId);
              }
              else {
                  return "FAILURE";
              }


          }
        return "SUCCESS";
    }

    private int checkFilghtCapacity(Integer flightId) {

          for(Flight flight : flightList){
              if(flight.getFlightId()==flightId){
                  return flight.getMaxCapacity();
              }
          }
          return 0;
    }


    public String cancelATicket(Integer flightId, Integer passengerId) {

          if(!flightPassenger.containsKey(flightId)) return "FAILURE";

          else {
              List<Integer> list=flightPassenger.get(flightId);
             if(! list.contains(passengerId)) {
                 return "FAILURE";
             }
             else {
                 list.remove(list.indexOf(passengerId)) ;
                 return "SUCCESS";
             }
          }




    }

    public int getNumberOfPeopleOn(Date date, String airportName) {

          int ans=0;

          for(Flight flight: flightList){
              if((flight.getFromCity().equals(airportName) || flight.getToCity().equals(airportName))){
                  if(flight.getFlightDate().compareTo (date)==0 ){
//                      ans+=flightPassenger.getOrDefault(flight,0).size();
                      if(flightPassenger.containsKey(flight))   ans+=flightPassenger.get(flight).size();
                  }
              }
          }

          return ans;
    }

    public Map<Integer, List<Integer>> getFlightPassenger() {
        return flightPassenger;
    }

    public List<Integer> listOfPassengerOfPartcularFlight(Integer flightId) {
          return flightPassenger.get(flightId);
    }
}

