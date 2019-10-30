package ro.siveco.hipster.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * A AppLogs.
 */
@Entity
@Table(name = "app_logs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "log_id")
    private UUID logId;

    @Column(name = "entry_date")
    private ZonedDateTime entryDate;

    @Column(name = "logger")
    private String logger;

    @Column(name = "log_level")
    private String logLevel;

    @Column(name = "message")
    private String message;

    @Column(name = "username")
    private String username;

    @Column(name = "aplicatie")
    private String aplicatie;

    @Column(name = "cod")
    private String cod;

    @Column(name = "tip")
    private String tip;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getLogId() {
        return logId;
    }

    public AppLogs logId(UUID logId) {
        this.logId = logId;
        return this;
    }

    public void setLogId(UUID logId) {
        this.logId = logId;
    }

    public ZonedDateTime getEntryDate() {
        return entryDate;
    }

    public AppLogs entryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public void setEntryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public String getLogger() {
        return logger;
    }

    public AppLogs logger(String logger) {
        this.logger = logger;
        return this;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public AppLogs logLevel(String logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getMessage() {
        return message;
    }

    public AppLogs message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public AppLogs username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAplicatie() {
        return aplicatie;
    }

    public AppLogs aplicatie(String aplicatie) {
        this.aplicatie = aplicatie;
        return this;
    }

    public void setAplicatie(String aplicatie) {
        this.aplicatie = aplicatie;
    }

    public String getCod() {
        return cod;
    }

    public AppLogs cod(String cod) {
        this.cod = cod;
        return this;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getTip() {
        return tip;
    }

    public AppLogs tip(String tip) {
        this.tip = tip;
        return this;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppLogs)) {
            return false;
        }
        return id != null && id.equals(((AppLogs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AppLogs{" +
            "id=" + getId() +
            ", logId='" + getLogId() + "'" +
            ", entryDate='" + getEntryDate() + "'" +
            ", logger='" + getLogger() + "'" +
            ", logLevel='" + getLogLevel() + "'" +
            ", message='" + getMessage() + "'" +
            ", username='" + getUsername() + "'" +
            ", aplicatie='" + getAplicatie() + "'" +
            ", cod='" + getCod() + "'" +
            ", tip='" + getTip() + "'" +
            "}";
    }
}
