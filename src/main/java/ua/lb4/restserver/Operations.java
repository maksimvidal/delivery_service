package ua.lb4.restserver;


import ua.lb4.restserver.bo.CourierType;
import ua.lb4.restserver.bo.DeliveryType;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public interface Operations {

    static DeliveryType createDelivery(DeliveryType deliveryType, Client client) {
        WebTarget target = client.target(Links.URL + "deliveries");
        return target.request(MediaType.APPLICATION_XML)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.xml(deliveryType), DeliveryType.class);
    }

    static List<DeliveryType> getAllDeliveries(String id, Client client)  {
        WebTarget target = client.target(String.format(Links.GET_ALL_DELIVERIES, id));
        List<DeliveryType> deliveryTypes = target.request().get().readEntity(new GenericType<List<DeliveryType>>() {});
//        WebTarget target = client.target(LINK.URL + "clients/" + id + "/deliveries");
//        List<DeliveryType> deliveryTypes = target.request().get().readEntity(new GenericType<>() {});

        return deliveryTypes;
    }

    static DeliveryType cancelDelivery(String id, Client client) {
        WebTarget target = client.target(Links.URL + "deliveries/cancel/" + id);
        DeliveryType delivery = (target.request().get().readEntity(DeliveryType.class));

        return delivery;
    }

    static DeliveryType updateDelivery(DeliveryType delivery, Client client) {
        WebTarget target = client.target(Links.URL + "deliveries");
        return target.request().put(Entity.xml(delivery), DeliveryType.class);
    }

    static DeliveryType assignCourier(String id, CourierType courier, Client client) {
        WebTarget target = client.target(Links.URL + "deliveries/couriers/" + id);
        return target.request().put(Entity.xml(courier), DeliveryType.class);
    }

}
