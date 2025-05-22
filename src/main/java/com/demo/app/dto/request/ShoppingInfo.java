package com.demo.app.dto.request;

import com.demo.app.enums.Location;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingInfo implements Serializable {
    @NotNull
    private Location location;
    @NotEmpty
    private List<ShoppingItem> itemList;

}
