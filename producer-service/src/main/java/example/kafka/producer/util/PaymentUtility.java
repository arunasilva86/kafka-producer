package example.kafka.producer.util;

import java.util.concurrent.ThreadLocalRandom;

public class PaymentUtility {

    public static String generateRandomTransaction(){
        String vendors[]={"Amazon","Paypal","Visa","mastercard"};
        String vendor= vendors[ThreadLocalRandom.current().nextInt(vendors.length)];
        double amount = ThreadLocalRandom.current().nextDouble(0.10,1000.0);
        return "Vendor: "+vendor+"Amount $"+amount;
    }
}
