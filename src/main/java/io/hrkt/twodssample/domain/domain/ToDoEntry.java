package io.hrkt.twodssample.domain.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoEntry {
    private String todo;
}
