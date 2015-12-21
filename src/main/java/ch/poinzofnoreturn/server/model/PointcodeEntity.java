package ch.poinzofnoreturn.server.model;

import javax.persistence.*;

/**
 * Repräsentiert den Code für einen Anbieter
 */
@Entity
@Table(name="pointcode")
public class PointcodeEntity extends PublicIdEntity {
    @ManyToOne
    @JoinColumn(name = "provider_fk")
    private ProviderEntity provider;

    @Column(name = "content", nullable = false)
    private String content;

    public ProviderEntity getProvider() {
        return provider;
    }

    public void setProvider(ProviderEntity provider) {
        this.provider = provider;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
