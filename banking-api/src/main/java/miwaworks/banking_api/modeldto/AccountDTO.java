package miwaworks.banking_api.modeldto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private String holderName;
    private Integer balance;

}
