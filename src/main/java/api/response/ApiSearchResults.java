package api.response;

import api.response.ApiSearchResultItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


//POJO есть java генераторы, на основании open api из постман ответов генерят pojo джава классы (Spring 2)
@Data //lombok can build builders
@AllArgsConstructor // jackson специфически работает, добавляем AllArgs+NoArgs чтобы он не сломался
@NoArgsConstructor
public class ApiSearchResults {
    private List<ApiSearchResultItem> results;
    private Integer offset;
    private Integer number;
    private Integer totalResults;

}
