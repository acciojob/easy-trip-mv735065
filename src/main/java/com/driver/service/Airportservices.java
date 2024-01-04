package com.driver.service;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import com.driver.respo.AirportResporitary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.*;

@Service
public class Airportservices {

    @Autowired
    AirportResporitary airportResporitary=new AirportResporitary();


    public void addAirport(Airport airport) {
        airportResporitary.addAirport(airport);
    }

    public String getLargestAirportName() {
        List<Airport> list=airportResporitary.getLargestAirportName();
        String ans=null;
        int terminals=0;

        for(Airport airport : list){
            if(airport.getNoOfTerminals()>terminals){
                ans=airport.getAirportName();
                terminals=airport.getNoOfTerminals();
            }
            else if(airport.getNoOfTerminals()==terminals){
                int i = ans.compareTo(airport.getAirportName());
                ans=i<0 ? ans: airport.getAirportName();
            }
        }
return ans;
    }

    public String addFlight(Flight flight) {
        return airportResporitary.addFlight(flight);
    }

    public String addPassenger(Passenger passenger) {

        return airportResporitary.addPassenger(passenger);
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {

        List<Flight> list=airportResporitary.getFlightList();
        double ans=Integer.MAX_VALUE;

        for(Flight flight : list){
            if(flight.getFromCity().equals(fromCity) && flight.getToCity().equals(toCity)){
                ans=Math.min(ans,flight.getDuration());
            }
        }
        if(ans==Integer.MAX_VALUE) return -1;
        return ans;
    }


    public String bookATicket(Integer flightId, Integer passengerId) {
        return airportResporitary.bookATicket(flightId, passengerId);
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        return airportResporitary.cancelATicket(flightId, passengerId);
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {

        return airportResporitary.getNumberOfPeopleOn(date, airportName);

    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {

        Map<Integer, List<Integer>> map=airportResporitary.getFlightPassenger();

        int count=0;

        for(Integer i: map.keySet()){
           if( map.get(i).contains(passengerId)) count++;
        }
        return count;
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        List<Flight> list=airportResporitary.getFlightList();

            for(Flight flight: list){
                if(flight.getFlightId()==flightId ){
                    return flight.getFromCity().name();
                }
            }
        return null;
    }

    public int calculateFlightFare(Integer flightId) {

        Map<Integer,List<Integer>> map=airportResporitary.getFlightPassenger();

        List<Integer> list=map.get(flightId);

        int size= list.size();;

        int cost=3000+(size*50);
        return cost;


    }


    public int calculateRevenueOfAFlight(Integer flightId) {

        List<Integer> list = airportResporitary.listOfPassengerOfPartcularFlight(flightId);

        int cost=0;

        int n=list.size()-1;
        cost+=3000*list.size();

        int sumOfNum= (n*(n+1) )/2;

        cost+=(sumOfNum*50);

        return cost;



    }
}
