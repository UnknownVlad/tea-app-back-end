package org.example.teaappbackend.gateway.confirmation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmationDto {
    String emailTo;
    String subjectText;
    String content;
}
