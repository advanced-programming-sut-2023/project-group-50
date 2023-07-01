package model.Massenger;

import java.io.Serializable;

public class PrivateChat extends Chat implements Serializable {
    public PrivateChat(String admin, String id, String name) {
        super(admin, id, name);
        super.addMember(admin);
        super.setChatType(ChatType.PRIVATE_CHAT);
    }
}
