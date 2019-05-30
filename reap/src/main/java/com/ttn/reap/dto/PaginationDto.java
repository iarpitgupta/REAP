package com.ttn.reap.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
@Setter
public class PaginationDto<T> {

    Page<T> page;

    List<Integer> pageNumbers;

    @Override
    public String toString() {
        return "PaginationDto{" +
                "page=" + page +
                ", pageNumbers=" + pageNumbers +
                '}';
    }
}
