package ua.lb4.restserver;

public interface Links {
    String CREATE_DELIVERY = "http://localhost:8081/service/deliveries";
    String GET_ALL_DELIVERIES = "http://localhost:8081/service/clients/%s/deliveries";
    String CANCEL_DELIVERY = "http://localhost:8081/service/deliveries/cancel/%s";
    String UPDATE_DELIVERY = "http://localhost:8081/service/deliveries";
    String ALL_COURIERS = "http://localhost:8081/service/couriers";
    String ASSIGN_COURIER = "http://localhost:8081/service/deliveries/couriers/%s";
    String URL = "http://localhost:8081/service/";
}
