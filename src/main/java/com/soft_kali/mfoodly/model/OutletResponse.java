package com.soft_kali.mfoodly.model;

import com.soft_kali.mfoodly.dto.user.OutletDto;
import lombok.*;

import java.util.List;


@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OutletResponse {
    private List<OutletDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
    private int totalPages;
    private boolean lastPage;
}
