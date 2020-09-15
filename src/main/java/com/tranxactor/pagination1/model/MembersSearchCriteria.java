package com.tranxactor.pagination1.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Builder
@Getter
public class MembersSearchCriteria {
    private Integer page;

    private Integer perPage;

    private Sort.Direction direction;

    private String sort;

    private Long id;

    private String firstName;

    private String lastName;

    private String nikename;

    private String designation;
}