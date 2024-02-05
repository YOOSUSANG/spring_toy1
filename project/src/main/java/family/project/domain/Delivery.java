package family.project.domain;

import family.project.domain.enums.DeliveryStatus;
import family.project.domain.mapped.BasicEntity;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class Delivery extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;


    protected Delivery() {

    }

    public Delivery(DeliveryStatus status, Address address) {
        this.status = status;
        this.address = address;
    }

    public Delivery(Order order, DeliveryStatus status, Address address) {
        this.order = order;
        this.status = status;
        this.address = address;
    }
}
