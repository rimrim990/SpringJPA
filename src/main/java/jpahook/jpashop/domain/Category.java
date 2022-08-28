package jpahook.jpashop.domain;

import jpahook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            // 중간 테이블
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    // many to many 를 일대 다 관계로 풀어내는 중간 테이블 필요
    // 실무에서는 쓰지 말자 - 중간 테이블 수정이 불가능하고 운영하기 어렵다고 함
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    // == 연관관계 메서드 == //
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
