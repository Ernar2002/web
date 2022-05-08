package com.shop.onlineshop.utils.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class TotalSumResponse {
    private boolean error;
    private int totalSum;
}
