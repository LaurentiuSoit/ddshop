package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.DTOs.ProductFilterCriteriaDTO;
import dd.projects.ddshop.Entities.Product;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> filterProducts(ProductFilterCriteriaDTO criteria) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.isInStock()) {
                predicates.add(builder.greaterThan(root.get("availableQuantity"), 0));
            }
            if (criteria.getMinPrice() != null) {
                predicates.add(
                    builder.greaterThanOrEqualTo(root.get("price"), criteria.getMinPrice())
                );
            }

            if (criteria.getMaxPrice() != null) {
                predicates.add(
                    builder.lessThanOrEqualTo(root.get("price"), criteria.getMaxPrice())
                );
            }

            if (criteria.getName() != null && !criteria.getName().isEmpty()) {
                predicates.add(
                    builder.like(
                        builder.lower(root.get("name")),
                        "%" + criteria.getName().toLowerCase() + "%"
                    )
                );
            }

            if (criteria.getCategoryId() != null) {
                predicates.add(
                    builder.equal(root.get("category").get("id"), criteria.getCategoryId())
                );
            }

            if (
                criteria.getAttributeValueIds() != null &&
                !criteria.getAttributeValueIds().isEmpty()
            ) {
                for (Integer attributeValueId : criteria.getAttributeValueIds()) {
                    predicates.add(
                        builder.isTrue(
                            builder
                                .literal(attributeValueId)
                                .in(
                                    root.join("validAttributeList").join("attributeValue").get("id")
                                )
                        )
                    );
                }
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
