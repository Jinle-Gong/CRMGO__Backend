package system.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Authorization {
    private Integer authId;
    private String authName;
    private String roleName;
    private String description;
    private String authStatus;
    private String menuId;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
