package system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    private Integer mid;

    private String menuName;

    private String menuUrl;

    private String menuIcon;

    private Integer level;

    private Integer pid;

    private List<Menu> children = new ArrayList<>();
}
