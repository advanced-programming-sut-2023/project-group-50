package model.Massenger;

import java.io.Serializable;
import java.util.LinkedHashSet;

public class PublicChat implements Serializable {
    public static LinkedHashSet<Message> messages = new LinkedHashSet<>();
}
