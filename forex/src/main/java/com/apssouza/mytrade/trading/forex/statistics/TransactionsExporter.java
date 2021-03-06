package com.apssouza.mytrade.trading.forex.statistics;

import com.apssouza.mytrade.trading.forex.session.CycleHistory;
import com.apssouza.mytrade.trading.forex.session.TransactionDto;
import com.apssouza.mytrade.common.misc.helper.file.WriteFileHelper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TransactionsExporter {

    public void exportCsv(List<CycleHistory> transactions, String filepath) throws IOException {
        Path path = Paths.get(filepath);
        if (!Files.exists(path)){
            File file = new File(filepath);
            file.createNewFile();
        }
        WriteFileHelper.write(filepath, getHeader() + "\n");
        for (CycleHistory item : transactions) {
            for (Map.Entry<String, TransactionDto> trans : item.getTransactions().entrySet()) {
                List<String> line = Arrays.asList(
                        trans.getValue().getIdentifier(),
                        trans.getValue().getPosition() != null ? toString(trans.getValue().getPosition().getInitPrice()) : "",
                        trans.getValue().getPosition() != null ? toString(trans.getValue().getPosition().getCurrentPrice()) : "",
                        trans.getValue().getPosition() != null ? toString(trans.getValue().getPosition().getQuantity()) : "",
                        trans.getValue().getPosition() != null ? toString(trans.getValue().getPosition().getInitPrice().multiply(BigDecimal.valueOf(trans.getValue().getPosition().getQuantity()))) : "",
                        trans.getValue().getPosition() != null ? toString(trans.getValue().getPosition().getCurrentPrice().multiply(BigDecimal.valueOf(trans.getValue().getPosition().getQuantity()))) : "",
                        trans.getValue().getPosition() != null ? toString(trans.getValue().getPosition().getCurrentPrice().subtract(trans.getValue().getPosition().getInitPrice())) : "",
                        trans.getValue().getFilledOrder() != null ? toString(trans.getValue().getFilledOrder().getAction()) : "",
                        trans.getValue().getPosition() != null ? toString(trans.getValue().getPosition().getTimestamp()) : "",
                        //trans.getValue().getPosition() != null ? trans.getValue().getPosition().getPlacedStopLoss().getPrice().toString(): "",
                        //trans.getValue().getPosition() != null ? trans.getValue().getPosition().getTakeProfitOrder().getPrice().toString(): "",
                        trans.getValue().getPosition() != null ? toString(trans.getValue().getPosition().getExitReason()) : "",
                        trans.getValue().getState() != null ? toString(trans.getValue().getState()) : "",
                        ""
                );
                WriteFileHelper.append(filepath, String.join(",", line) + "\n");
            }
        }
    }

    private String toString(Object ob) {
        if (ob != null) {
            return ob.toString();
        }
        return "";
    }

    private List<String> getHeader() {
        List<String> line = Arrays.asList(
                "Identifier",
                "Init price",
                "End price",
                "Quantidade",
                "Init amount",
                "End amount",
                "Result",
                "Action",
                "Timestamp",
                "Exit reason",
                "State",
                ""
        );
        return line;
    }
}

