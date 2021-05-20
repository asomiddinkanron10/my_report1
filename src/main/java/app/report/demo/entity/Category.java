package app.report.demo.entity;

import app.report.demo.entity.template.AbsEntity;
import app.report.demo.entity.template.AbsNameEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends AbsNameEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String description;
}
