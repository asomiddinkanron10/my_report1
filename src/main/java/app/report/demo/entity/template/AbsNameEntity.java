package app.report.demo.entity.template;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
public abstract class AbsNameEntity extends AbsEntity {
    @Column(nullable = false, unique = true)
    private String name;
}
