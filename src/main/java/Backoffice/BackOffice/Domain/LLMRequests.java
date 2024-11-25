package Backoffice.BackOffice.Domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "llm_requests")
public class LLMRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "robot_id", nullable = false)
    private String robotId;

    @Enumerated(EnumType.STRING)
    private SourceType source = SourceType.GPT4O_mini;

    @Column(columnDefinition = "TEXT")
    private String prompt;

    @Column(columnDefinition = "TEXT")
    private String response;

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Column(name = "updated")
    private LocalDateTime updated;

    @PrePersist
    protected void onCreate(){
        this.created = LocalDateTime.now();
        this.updated = this.created;
    }

    @PreUpdate
    protected void onUpdate(){
        this.updated = LocalDateTime.now();
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public SourceType getSource() {
        return source;
    }

    public void setSource(SourceType source) {
        this.source = source;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
