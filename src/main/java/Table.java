import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a7.A7_Grids;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Table {
  private AsciiTable table = new AsciiTable();
  private int size;
  private String[] options;

  public Table(String[] args) {
    options =  Arrays.stream(args).distinct().toArray(String[]::new);
    size = options.length;
    if ((size == 1) || (size % 2 == 0)) throw new IllegalArgumentException();
    this.fillTable();
  }

  private void fillTable() {
      fillHeader();
      for (int i = 0; i < size; i++) {
        fillRow(i);
      }
      table.getContext().setGrid(A7_Grids.minusBarPlusEquals());
  }

  private void fillHeader() {
    table.addRule();
    List<String> header = new ArrayList<>();
    header.addAll(Arrays.asList(options));
    header.add(0, "User / PC");
    table.addRow(header);
    table.addRule();
  }

  private void fillRow(int i) {
    List<String> row = new ArrayList<>();
    row.add(options[i]);
    for (int j = 0; j < size; j++) {
      row.add(Rules.getResult(i+1, j+1, size));
    }
    table.addRow(row);
    table.addRule();
  }

  public String getTable() {
    return table.render();
  }

  public void printTable() {
    System.out.println(table.render());
  }

  public String[] getOptions() {
    return options;
  }
}
