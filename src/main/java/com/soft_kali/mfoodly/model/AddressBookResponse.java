package com.soft_kali.mfoodly.model;

import com.soft_kali.mfoodly.dto.user.AddressBookDto;
import lombok.*;

import java.util.List;


@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressBookResponse {
    private List<AddressBookDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
    private int totalPages;
    private boolean lastPage;
}
