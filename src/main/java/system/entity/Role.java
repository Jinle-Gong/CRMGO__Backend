package system.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Role {
    private Integer rid;

    private String roleCode;

    private String roleName;

    private String description;

    private String roleStatus;

    private String deptName;

    private String auth;

}
