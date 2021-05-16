package org.maxkey.domain.result;

import lombok.Data;

import java.util.List;

@Data
public class PageData<T> {
    private List<T> items;
    private long total;
}
