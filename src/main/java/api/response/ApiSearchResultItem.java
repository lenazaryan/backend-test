package api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //generates constructor, getters & setters, hash
@AllArgsConstructor
@NoArgsConstructor
public class ApiSearchResultItem {
    private Long id;
    private String title;
    private String image;
    private String imageType;
}
