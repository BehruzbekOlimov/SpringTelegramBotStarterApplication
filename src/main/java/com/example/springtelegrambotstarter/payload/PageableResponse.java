package com.example.springtelegrambotstarter.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageableResponse<K>{

    private List<K> data;
    private long totalElements;
}
