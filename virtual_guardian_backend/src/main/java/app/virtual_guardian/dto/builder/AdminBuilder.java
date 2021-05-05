package app.virtual_guardian.dto.builder;

import app.virtual_guardian.entity.Admin;
import app.virtual_guardian.entity.User;

public class AdminBuilder {
    public static Admin toEntity(User user){
        return new Admin(user);
    }
}
