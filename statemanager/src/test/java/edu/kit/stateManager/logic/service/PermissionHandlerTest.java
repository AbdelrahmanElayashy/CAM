package edu.kit.stateManager.logic.service;

import edu.kit.stateManager.infrastructure.converter.StateNameConverter;
import edu.kit.stateManager.logic.model.state.StateNames;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class PermissionHandlerTest {

    @Test
    void readFromExcelFile() throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(new File("C:\\Users\\felix\\Documents\\Intellij\\Testfiles\\Permission.xlsx"));
        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, StateNames> rowMapping = new HashMap<>();
        Map<Integer, StateNames> columnMapping = new HashMap<>();

        DataFormatter formatter = new DataFormatter();
        StateNameConverter stateNameConverter = new StateNameConverter();

        String type = formatter.formatCellValue(sheet.getRow(0).getCell(0));
        //TODO check type in staff manager

        Row row0 = sheet.getRow(0);
        for (int i = 2; i < row0.getLastCellNum(); i++) {
            Cell cell = row0.getCell(i);
            String value = formatter.formatCellValue(cell);
            StateNames state;
            try {
                state = stateNameConverter.convertToEntityAttribute(value);
                columnMapping.put(i, state);
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal State name in row 1 column " + i + ": " + value);
            }
        }

        Row column0 = sheet.getRow(0);
        for (int i = 2; i < column0.getLastCellNum(); i++) {
            Cell cell = column0.getCell(i);
            String value = formatter.formatCellValue(cell);
            StateNames state;
            try {
                state = stateNameConverter.convertToEntityAttribute(value);
                rowMapping.put(i, state);
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal State name in row 1 column " + i + ": " + value);
            }
        }

        for (int i = 2; i < column0.getLastCellNum(); i++) {    //Iterates over rows
            for (int j = 2; j < row0.getLastCellNum(); j++) {   //Iterates over columns
                Cell cell = sheet.getRow(i).getCell(j);
                String minRank = formatter.formatCellValue(cell);
                if (!minRank.equals("")) {
                    //TODO Check rank in staff manager
                    System.out.println("From : " + rowMapping.get(i) + ", To: " + columnMapping.get(j) + ", Type: " + type + ", Min Rank: " + minRank);
                }
            }
        }
    }

}