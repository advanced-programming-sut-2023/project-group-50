import controller.Controller;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
//        new Loader().load();
        Controller app = new Controller();
        app.run();
//        new Saver().save();
    }
}