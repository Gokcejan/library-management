package cz.demo.librarymanagement.application.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaginationUtil {


    public static Pageable resolvePageable(Map<String, String> queryParams) {
        int page = Integer.parseInt(queryParams.getOrDefault("page", "0"));
        int size = Integer.parseInt(queryParams.getOrDefault("size", "10"));

        List<Sort.Order> orders = new ArrayList<>();

        queryParams.entrySet().stream()
                .filter(e -> e.getKey().equals("sort"))
                .map(Map.Entry::getValue)
                .forEach(sortStr -> {
                    String[] parts = sortStr.split(",");
                    if (parts.length > 0) {
                        String property = parts[0];
                        Sort.Direction direction = (parts.length > 1 && parts[1].equalsIgnoreCase("desc"))
                                ? Sort.Direction.DESC : Sort.Direction.ASC;
                        orders.add(new Sort.Order(direction, property));
                    }
                });

        Sort sort = orders.isEmpty() ? Sort.by("id").ascending() : Sort.by(orders);

        return PageRequest.of(page, size, sort);
    }
}
