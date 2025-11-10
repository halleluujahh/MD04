package org.com.session06.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VietQrResponseDTO {
    String vietQr;
    String content;
    String amount;
}
