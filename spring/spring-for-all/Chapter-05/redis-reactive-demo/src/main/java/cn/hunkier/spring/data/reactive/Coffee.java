package cn.hunkier.spring.data.reactive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coffee {
    private Long id;
    private String name;
    private Long price;
}
