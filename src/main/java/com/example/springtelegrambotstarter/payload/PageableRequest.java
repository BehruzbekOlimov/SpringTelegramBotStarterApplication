package com.example.springtelegrambotstarter.payload;

import com.example.springtelegrambotstarter.entity.template.RootEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class PageableRequest {
    private String orderBy = "id";
    private String search = null;
    private String searchBy = null;

    private Object equalTo = null;

    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    private LocalDate from;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    private LocalDate to;
    private boolean desc = false;
    private int size = 25;
    private int page = 0;

    public String getSearch() {
        if (search == null) {
            return "";
        }
        return search;
    }

    public String getSearchBy() {
        if (searchBy == null) {
            return "";
        }
        return searchBy.trim();
    }

    public void setFrom(LocalDate from) {
        System.out.println("from: " + from);
        this.from = from;
    }

    public void setTo(LocalDate to) {
        System.out.println("to: " + to);
        this.to = to;
    }

    public Integer getSize() {
        // Default page size
        return size <= 0 ? 1 : size;
    }

    public Object getEqualTo() {
        if (equalTo == null) {
            return "none-filter";
        } else if (equalTo instanceof String) {
            return equalTo.toString().trim().equals("") ? "none-filter" : equalTo.toString().trim().equals("null") ? null : equalTo.toString().trim();
        }
        try {
            return Integer.parseInt(equalTo.toString());
        } catch (Exception ignored) {
        }
        try {
            return Long.parseLong(equalTo.toString());
        } catch (Exception ignored) {
        }
        try {
            return Double.parseDouble(equalTo.toString());
        } catch (Exception ignored) {
        }
        try {
            return Float.parseFloat(equalTo.toString());
        } catch (Exception ignored) {
        }
        try {
            return Boolean.parseBoolean(equalTo.toString());
        } catch (Exception ignored) {
        }
        try {
            return LocalDate.parse(equalTo.toString());
        } catch (Exception ignored) {
        }
        try {
            return LocalDateTime.parse(equalTo.toString());
        } catch (Exception ignored) {
        }
        return equalTo;
    }

    public Integer getPage() {
        return Math.max(page, 0);
    }

    public String getOrderBy() {
        return orderBy;
    }

    public LocalDate getFrom() {
        return from != null ? from : LocalDate.of(1970, 1, 1);
    }

    public LocalDate getTo() {
        return to != null ? to : LocalDate.now();
    }

    public boolean isDesc() {
        return desc;
    }

    public Pageable toPageable() {
        return org.springframework.data.domain.PageRequest.of(getPage(), getSize(), isDesc() ? org.springframework.data.domain.Sort.Direction.DESC : org.springframework.data.domain.Sort.Direction.ASC, getOrderBy());
    }

    public <E extends RootEntity> Specification<E> getSpecificationBetweenDates() {
        if (getSearchBy().isEmpty() || getSearch().isEmpty()) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.between(root.get("createdAt"), Date.from(getFrom().atStartOfDay().toInstant(ZoneOffset.UTC)), Date.from(getTo().atTime(23, 59, 59).toInstant(ZoneOffset.UTC)));
        }
        if (getEqualTo() instanceof String && getEqualTo().toString().equals("none-filter")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.and(
                            criteriaBuilder.between(root.get("createdAt"), Date.from(getFrom().atStartOfDay().toInstant(ZoneOffset.UTC)), Date.from(getTo().atTime(23, 59, 59).toInstant(ZoneOffset.UTC))),
                            criteriaBuilder.like(criteriaBuilder.upper(root.get(getSearchBy())), "%" + getSearch().toUpperCase() + "%")
                    );
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.between(root.get("createdAt"), Date.from(getFrom().atStartOfDay().toInstant(ZoneOffset.UTC)), Date.from(getTo().atTime(23, 59, 59).toInstant(ZoneOffset.UTC))),
                        criteriaBuilder.equal(root.get(getSearchBy()), getEqualTo())
                );
    }
    public <E extends RootEntity> Specification<E> getSpecificationBetweenDatesAnd(Specification<E> specification) {
        if (getSearchBy().isEmpty() || getSearch().isEmpty()) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.and(
                            criteriaBuilder.between(root.get("createdAt"), Date.from(getFrom().atStartOfDay().toInstant(ZoneOffset.UTC)), Date.from(getTo().atTime(23, 59, 59).toInstant(ZoneOffset.UTC))),
                            specification.toPredicate(root, query, criteriaBuilder)
                    );
        }
        if (getEqualTo() instanceof String && getEqualTo().toString().equals("none-filter")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.and(
                            criteriaBuilder.between(root.get("createdAt"), Date.from(getFrom().atStartOfDay().toInstant(ZoneOffset.UTC)), Date.from(getTo().atTime(23, 59, 59).toInstant(ZoneOffset.UTC))),
                            criteriaBuilder.like(criteriaBuilder.upper(root.get(getSearchBy())), "%" + getSearch().toUpperCase() + "%"),
                            specification.toPredicate(root, query, criteriaBuilder)
                    );
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.between(root.get("createdAt"), Date.from(getFrom().atStartOfDay().toInstant(ZoneOffset.UTC)), Date.from(getTo().atTime(23, 59, 59).toInstant(ZoneOffset.UTC))),
                        criteriaBuilder.equal(root.get(getSearchBy()), getEqualTo())
                );
    }
}
