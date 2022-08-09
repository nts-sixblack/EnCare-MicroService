package nts.sixblack.findservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private long categoryId;
    private String name;
    private String description;

    public CategoryResponse(long categoryId) {
        this.categoryId = categoryId;
    }
}
