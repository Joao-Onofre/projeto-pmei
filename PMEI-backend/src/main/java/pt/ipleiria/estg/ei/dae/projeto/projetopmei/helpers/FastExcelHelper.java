package pt.ipleiria.estg.ei.dae.projeto.projetopmei.helpers;

import org.dhatim.fastexcel.reader.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class FastExcelHelper {

    public Map<Integer, List<String>> readExcel(InputStream fileInputStream) throws IOException {
        Map<Integer, List<String>> data = new HashMap<>();

        try (ReadableWorkbook workbook = new ReadableWorkbook(fileInputStream)) {
            Sheet sheet = workbook.getFirstSheet();
            if (sheet == null) {
                throw new IllegalArgumentException("The Excel file is empty or invalid.");
            }

            try (Stream<Row> rows = sheet.openStream()) {
                rows.forEach(row -> {
                    List<String> rowData = new ArrayList<>();
                    for (Cell cell : row) {
                        rowData.add(cell.getRawValue());
                    }
                    data.put(row.getRowNum(), rowData);
                });
            }
        }
        return data;
    }
}
