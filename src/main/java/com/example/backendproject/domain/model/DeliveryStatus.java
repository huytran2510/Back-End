package com.example.backendproject.domain.model;

import com.example.backendproject.domain.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeliveryStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @OneToMany(mappedBy = "deliveryStatus")
    private Set<Order> orders;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
