package org.com.session06.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.com.session06.entity.enumerate.Status;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "`orders`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(100)", name = "serial_number")
    private String serialNumber;
    @Column(name = "total_price", columnDefinition = "decimal", precision = 10, scale = 2)
    private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(columnDefinition = "varchar(100)")
    private String note;
    @Column(name = "receive_name", columnDefinition = "varchar(100)")
    private String receiveName;
    @Column(name = "receive_address", columnDefinition = "varchar(255)")
    private String receiveAddress;
    @Column(name = "receive_phone", columnDefinition = "varchar(15)")
    private String receivePhone;
    @Column(name = "created_at", columnDefinition = "date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate createdAt;
    @Column(name = "received_at", columnDefinition = "date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate receivedAt;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;
    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;
}
