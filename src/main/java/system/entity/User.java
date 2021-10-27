package system.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private Integer uid;

    private String username;

    private String password;

    private String deptName;

    private String accountStatus;

    private String role;

    private String position;

    private String gender;

    private String uname;

    private String uphone;

    private String email;

}
