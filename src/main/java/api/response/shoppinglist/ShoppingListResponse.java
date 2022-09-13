package api.response.shoppinglist;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingListResponse {

        @JsonProperty("aisles")
        public List<Aisle> aisles = new ArrayList<>();
        @JsonProperty("cost")
        public Double cost;
        @JsonProperty("startDate")
        public Integer startDate;
        @JsonProperty("endDate")
        public Integer endDate;

}
