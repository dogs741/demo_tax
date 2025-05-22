package com.demo.app.dto.request;

import com.demo.app.enums.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingInfo implements Serializable {
    private Location location;
    private List<ShoppingItem> itemList;

}
