package nts.sixblack.zuulservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationForm {
    private long accountId;
    @NotBlank
    @NotEmpty
    private String name;
    private String description;
    private String birthDay;
}
