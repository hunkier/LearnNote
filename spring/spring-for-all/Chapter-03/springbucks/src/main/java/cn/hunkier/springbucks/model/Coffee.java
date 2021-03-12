package cn.hunkier.springbucks.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import org.joda.money.Money;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "T_COFFEE")
@Builder
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Accessors
public class Coffee extends BaseEntity implements Serializable {
    private String name;

    //import org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount;
//import org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount;
    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode" , value = "CNY")}
    )
    private Money price;

}
