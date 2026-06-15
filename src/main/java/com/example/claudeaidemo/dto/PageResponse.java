package com.example.claudeaidemo.dto;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Lightweight, serialization-friendly representation of a {@link Page} that exposes
 * only the fields the frontend needs (avoids leaking Spring's {@code PageImpl} internals).
 */
public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {
    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}
