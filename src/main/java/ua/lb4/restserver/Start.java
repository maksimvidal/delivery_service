package ua.lb4.restserver;

import ua.lb4.restserver.bo.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Logger;

public class Start {

    private static Logger LOG = Logger.getLogger(Start.class.getName());

    public static void main(String[] args) {

        Client client = ClientBuilder.newClient();
        VehicleType vehicleType = new VehicleType();
        vehicleType.setVehicleId(UUID.randomUUID().toString());
        vehicleType.setVehicleName("Truck");

        CourierType courierType = new CourierType();
        courierType.setCourierId("1");
        courierType.setAge((byte)25);
        courierType.setFirstName("Іван");
        courierType.setLastName("Чорнозем");
        courierType.setVehicle(vehicleType);
        courierType.setPhoneNumber("000000000");

        ItemType itemType = new ItemType();
        itemType.setItemId("1");
        itemType.setPrice((float) 1234.1);
        itemType.setItemName("item");

        ClientType clientType = new ClientType();
        clientType.setClientId("1");
        clientType.setClientAge((byte) 23);
        clientType.setEmail("email");
        clientType.setClientFirstName("Bob");
        clientType.setClientLastName("Bob");


        DeliveryType deliveryType = new DeliveryType();
        deliveryType.setDeliveryId("1");
        deliveryType.setAddress("random address");
        deliveryType.setDeliveryPrice((float) 124.1);
        deliveryType.setFinished(false);
        // deliveryType.setClient(clientType);
        deliveryType.setItem(itemType);


        boolean stop = false;
        while (!stop) {
            System.out.println("Operations");
            System.out.println("1.\tCreate delivery");
            System.out.println("2.\tGet all deliveries");
            System.out.println("3.\tCancel delivery");
            System.out.println("4.\tUpdate delivery");
            System.out.println("5.\tAssign courier");
            int n = new Scanner(System.in).nextInt();
            switch (n) {
                case 1:
                    System.out.println(Operations.createDelivery(deliveryType, client));
                    break;
                case 2:
                    System.out.println("All deliveries:\n" + Operations.getAllDeliveries(clientType.getClientId(), client));
                    break;
                case 3:
                    System.out.println("Canceled delivery\n" + Operations.cancelDelivery(deliveryType.getDeliveryId(), client));
                    break;
                case 4:
                    System.out.println("Updated delivery:\n" + Operations.updateDelivery(deliveryType, client));
                    break;
                case 5:
                    System.out.println("Delivery:\n"+Operations.assignCourier(deliveryType.getDeliveryId(), courierType,client));
                    break;
                default:
                    LOG.warning("");
            }
            System.out.println("\nEnter to continue or something else to exit");
            if (!new Scanner(System.in).nextLine().isBlank())
                stop = true;
        }
    }
}
