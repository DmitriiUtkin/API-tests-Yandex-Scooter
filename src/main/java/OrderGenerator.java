public class OrderGenerator {
    public static Order base() {
        Order order = new Order();
        order.setFirstName("Ivan15");
        order.setLastName("Ivanv17");
        order.setAddress("Ivanoskaya1, 6");
        order.setMetroStation(3);
        order.setPhone("89181234565");
        order.setRentTime(2);
        order.setDeliveryDate("07.04.2024");
        order.setComment("call");
        order.setColor(new String[]{"black"});
        return order;
    }
}
