package ua.lb4.restserver;

import ua.lb4.restserver.bo.ClientType;
import ua.lb4.restserver.bo.CourierType;
import ua.lb4.restserver.bo.DeliveryType;
import ua.lb4.restserver.bo.ItemType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Path("/")
public class Service {

    ClientType clientType = new ClientType();
    Client client = ClientBuilder.newClient();
    private static Logger log;

    static {
        log = Logger.getLogger(Service.class.getName());
    }

    @Path("")
    @GET
    public void index(@Context HttpServletRequest request, @Context HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/view/registration.jsp").forward(request, response);
    }

    @Path("main")
    @GET
    public void main(@Context HttpServletRequest request, @Context HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/view/main.jsp").forward(request, response);
    }

    @Path("registration")
    @POST
    public void register(@Context HttpServletRequest request,
                         @Context HttpServletResponse response,
                         @FormParam("clientFirstName") String clientFirstName,
                         @FormParam("clientLastName") String clientLastName,
                         @FormParam("email") String email,
                         @FormParam("clientAge") String age,
                         @FormParam("phone") String phone) throws ServletException, IOException {
        ClientType clientType = new ClientType();
        clientType.setClientId(UUID.randomUUID().toString());
        clientType.setClientFirstName(clientFirstName);
        clientType.setClientLastName(clientLastName);
        clientType.setClientAge(Byte.valueOf(age));
        clientType.setEmail(email);
        clientType.setPhone(phone);

        request.getSession().setAttribute("user", clientType);
        request.getSession().setAttribute("id", clientType.getClientId());

        request.getRequestDispatcher("/view/main.jsp").forward(request, response);
    }

    @Path("orderDelivery")
    @GET
    public void orderDelivery(@Context HttpServletRequest request,
                         @Context HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/view/makeDelivery.jsp").forward(request, response);
    }

    @Path("createDelivery")
    @POST
    public void makeDelivery(@Context HttpServletRequest request,
                              @Context HttpServletResponse response,
                             @FormParam("address") String address,
                             @FormParam("itemName") String itemName,
                             @FormParam("weight") String weight,
                             @FormParam("quantity") String quantity) throws ServletException, IOException {
        ItemType itemType = new ItemType();
        itemType.setItemId(UUID.randomUUID().toString());
        itemType.setItemName(itemName);
        itemType.setQuantity(Long.valueOf(quantity));
        itemType.setPrice(Long.valueOf(quantity) * Float.valueOf(weight));
        itemType.setWeight(Float.valueOf(weight));

        DeliveryType deliveryType = new DeliveryType();
        deliveryType.setDeliveryId(UUID.randomUUID().toString());
        deliveryType.setDeliveryStart(LocalDateTime.now().toString());
        deliveryType.setDeliveryPrice(4 * itemType.getPrice());
        deliveryType.setAddress(address);
        deliveryType.setClient((ClientType) request.getSession().getAttribute("user"));
        deliveryType.setItem(itemType);

        WebTarget target = client.target(Links.CREATE_DELIVERY);
        target.request(MediaType.APPLICATION_XML)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.xml(deliveryType), DeliveryType.class);

        request.getRequestDispatcher("/view/main.jsp").forward(request, response);
    }

    @Path("getAll")
    @GET
    public void getAllDeliveries(@Context HttpServletRequest request,
                             @Context HttpServletResponse response) throws ServletException, IOException {
        request.getSession().getAttribute("id");
        WebTarget target = client.target(String.format(Links.GET_ALL_DELIVERIES, request.getSession().getAttribute("id")));
        List<DeliveryType> deliveryTypes = target.request().get().readEntity(new GenericType<>() {});

        request.setAttribute("deliveries", deliveryTypes);
        request.getRequestDispatcher("/view/deliveries.jsp").forward(request, response);
    }

    @Path("assignCourier/{delivery_id}")
    @GET
    public void getAllCouriers(@Context HttpServletRequest request,
                                 @Context HttpServletResponse response,
                               @PathParam("delivery_id") String deliveryId) throws ServletException, IOException {
        WebTarget target = client.target(Links.ALL_COURIERS);
        List<CourierType> courierTypes = target.request().get().readEntity(new GenericType<>() {});

        request.setAttribute("couriers", courierTypes);
        request.getSession().setAttribute("deliveryId", deliveryId);
        request.getRequestDispatcher("/view/couriers.jsp").forward(request, response);
    }

    @Path("update/{delivery_id}")
    @GET
    public void returnUpdatePage(@Context HttpServletRequest request,
                                 @Context HttpServletResponse response,
                       @PathParam("delivery_id") String deliveryId) throws ServletException, IOException {
        WebTarget target = client.target(String.format(Links.GET_ALL_DELIVERIES, request.getSession().getAttribute("id")));
        List<DeliveryType> deliveryTypes = target.request().get().readEntity(new GenericType<>() {});

        DeliveryType deliveryToUpdate = deliveryTypes.stream()
                .filter(deliveryType -> deliveryType.getDeliveryId().equals(deliveryId))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);

        request.setAttribute("delivery", deliveryToUpdate);
        request.getRequestDispatcher("/view/edit.jsp").forward(request, response);
    }

    @Path("cancel/{delivery_id}")
    @GET
    public void cancelDelivery(@Context HttpServletRequest request,
                                 @Context HttpServletResponse response,
                               @PathParam("delivery_id") String deliveryId) throws ServletException, IOException {
        request.getSession().setAttribute("delivery_id", deliveryId);
        WebTarget target = client.target(String.format(Links.CANCEL_DELIVERY, request.getSession().getAttribute("delivery_id")));
        DeliveryType deliveryType = target.request(MediaType.APPLICATION_XML).delete(DeliveryType.class);

        request.getRequestDispatcher("/view/main.jsp").forward(request, response);
    }

    @Path("updateDelivery")
    @POST
   public void updateDelivery(@Context HttpServletRequest request,
                              @Context HttpServletResponse response,
                              @FormParam("address") String address,
                              @FormParam("itemName") String itemName,
                              @FormParam("weight") String weight,
                              @FormParam("quantity") String quantity,
                              @FormParam("deliveryId") String deliveryId) throws ServletException, IOException {
        WebTarget target = client.target(Links.UPDATE_DELIVERY);
        ItemType itemType = new ItemType();
        itemType.setItemId(UUID.randomUUID().toString());
        itemType.setItemName(itemName);
        itemType.setQuantity(Long.valueOf(quantity));
        itemType.setPrice(Long.valueOf(quantity) * Float.valueOf(weight));
        itemType.setWeight(Float.valueOf(weight));

        DeliveryType deliveryType = new DeliveryType();
        deliveryType.setDeliveryId(deliveryId);
        deliveryType.setDeliveryStart(LocalDateTime.now().toString());
        deliveryType.setDeliveryPrice(4 * itemType.getPrice());
        deliveryType.setAddress(address);
        deliveryType.setClient((ClientType) request.getSession().getAttribute("user"));
        deliveryType.setItem(itemType);

        target.request().put(Entity.xml(deliveryType), DeliveryType.class);

        request.getRequestDispatcher("/view/main.jsp").forward(request, response);
    }

    @Path("getAllUnassigned")
    @GET
    public void getAllUnassignedDeliveries(@Context HttpServletRequest request,
                                 @Context HttpServletResponse response) throws ServletException, IOException {
        WebTarget target = client.target(String.format(Links.GET_ALL_DELIVERIES, request.getSession().getAttribute("id")));
        List<DeliveryType> deliveryTypes = target.request().get().readEntity(new GenericType<>() {});
        deliveryTypes = deliveryTypes.stream()
                .filter(deliveryType -> deliveryType.getCourier() == null)
                .collect(Collectors.toList());


        request.setAttribute("deliveries", deliveryTypes);
        request.getRequestDispatcher("/view/unassignedDeliveries.jsp").forward(request, response);
    }

    @Path("assign/{courier_id}")
    @GET
    public void assignCourier(@Context HttpServletRequest request,
                              @Context HttpServletResponse response,
                              @PathParam("courier_id") String courierId) throws ServletException, IOException {
        WebTarget target = client.target(Links.ALL_COURIERS);
        List<CourierType> courierTypes = target.request().get().readEntity(new GenericType<>() {});
        CourierType courierType = courierTypes.stream()
                .filter(courier -> courier.getCourierId().equals(courierId))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);

        target = client.target(String.format(Links.ASSIGN_COURIER, request.getSession().getAttribute("deliveryId")));
        target.request().put(Entity.xml(courierType), DeliveryType.class);

        request.getRequestDispatcher("/view/main.jsp").forward(request, response);
    }
}
