package it.fantacalcio.ffm.domain.model.fantaleghe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FantalegheLoginRequest {
    String username;
    String password;
}
