package com.cloneweek.hanghaebnb.service;

import com.cloneweek.hanghaebnb.entity.Room;
import org.springframework.data.jpa.domain.Specification;

public class RoomSpecs {
    public static Specification<Room> whitType(String type) {
        return (Specification<Room>) ((root, query ,builder) ->
                builder.equal(root.get("type"), type)
        );
    }

    public enum SearchKey {
        
    }
}
