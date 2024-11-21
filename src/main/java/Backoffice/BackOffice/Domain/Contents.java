package Backoffice.BackOffice.Domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "contents")
public class Contents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long robot_id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String text;

    private String bg_image;

    private String url;

    @Column(nullable = false)
    private int type;

    @Column(name = "create_at", updatable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    @Column(name = "update_at")
    private LocalDateTime updateAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate(){
        this.updateAt = LocalDateTime.now();
    }


}
