package api.response.shoppinglist;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
            "aisle",
            "items"
    })
public class Aisle {

    @JsonProperty("aisle")
    public String aisle;
    @JsonProperty("items")
    public List<Item> items = new ArrayList<Item>();

}
