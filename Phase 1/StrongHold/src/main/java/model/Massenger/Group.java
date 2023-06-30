package model.Massenger;

import java.io.Serializable;

public class Group extends Chat implements Serializable {
    public Group(String admin, String id, String name) {
        super(admin, id, name);
        super.addMember(admin);
        super.setChatType(ChatType.GROUP);
    }
}
