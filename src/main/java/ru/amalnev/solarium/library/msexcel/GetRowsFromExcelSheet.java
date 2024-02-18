package ru.amalnev.solarium.library.msexcel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.amalnev.solarium.interpreter.ExecutionContext;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.interpreter.memory.IValue;
import ru.amalnev.solarium.interpreter.memory.Value;
import ru.amalnev.solarium.library.AbstractNativeFunction;
import ru.amalnev.solarium.library.FunctionArguments;
import ru.amalnev.solarium.library.FunctionName;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@FunctionName("getRowsFromExcelSheet")
@FunctionArguments({"excelFilePath", "sheetIndex"})
public class GetRowsFromExcelSheet extends AbstractNativeFunction {

    @Override
    public void call(ExecutionContext context) throws InterpreterException {
        final String excelFilePath = getScalarArgument(context, "excelFilePath");
        final Integer sheetIndex = getScalarArgument(context, "sheetIndex");

        try (FileInputStream file = new FileInputStream(new File(excelFilePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            IValue returnVal = new Value();
            returnVal.convertToArray();

            int rowNum = 0;
            for (Row row : sheet) {
                IValue rowValue = new Value();
                rowValue.convertToArray();

                int cellNum = 0;
                for (Cell cell : row) {
                    rowValue.addArrayElement(new Value(getCellValue(cell)));
                    cellNum++;
                }
                returnVal.addArrayElement(rowValue);
                rowNum++;
            }

            setReturnValue(context, returnVal);
        } catch (IOException e) {
            setReturnValue(context, null);
        }
    }

    private String getCellValue(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        }
        return cell.getStringCellValue();
    }
}
