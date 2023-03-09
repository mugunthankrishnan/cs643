package org.carrecognition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*  UCID:   mk2246
    Desc:   The App class is the entry point to the car image recognition application.
*/

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String... args) throws InterruptedException{
        logger.info("Car Recognition Application starts");

        GetImages getimages = new GetImages();
        getimages.getImageIndex();

        logger.info("Car Recognition Application ends");
    }
}