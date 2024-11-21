package Backoffice.BackOffice.Domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tts_requests")
public class TTSRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long robotId;

    private String source;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String text;

    private String audioFile;

}