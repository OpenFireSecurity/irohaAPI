package openFire.security.monitoring.model;

import iroha.protocol.BlockOuterClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class VerifierTransaction {
    private LocalDateTime localDateTime;
    private String src_account_id;
    private String dest_account_id;
    private String description;
    private Boolean running;

    @Override
    public String toString() {
        return "localDateTime=" + localDateTime +
                ", src_account_id='" + src_account_id + '\'' +
                ", dest_account_id='" + dest_account_id + '\'' +
                ", description='" + description + '\'' +
                ", running=" + running + "\n";
    }

    public VerifierTransaction(BlockOuterClass.Transaction transaction) {
        this.localDateTime = Instant.ofEpochMilli(transaction.getPayload().getCreatedTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.src_account_id = transaction.getPayload().getCommands(0).getTransferAsset().getSrcAccountId();
        this.dest_account_id = transaction.getPayload().getCommands(0).getTransferAsset().getDestAccountId();
        this.description = transaction.getPayload().getCommands(0).getTransferAsset().getDescription().substring(1);
        this.running = transaction.getPayload().getCommands(0).getTransferAsset().getDescription().charAt(0) == '1';
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getSrc_account_id() {
        return src_account_id;
    }

    public String getDest_account_id() {
        return dest_account_id;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getRunning() {
        return running;
    }
}
